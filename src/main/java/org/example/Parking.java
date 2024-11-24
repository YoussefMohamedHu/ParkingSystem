package org.example;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;
import java.lang.Thread;
public class Parking {
    int []gates=new int[]{0,0,0};
    SemaphoreCustomed parkingSlots;
    ArrayList<CarThread>carList=new ArrayList<>();
    public Parking(){

        parkingSlots=new SemaphoreCustomed(4);

    }
    public void goToGate(ArrayList<Car> cars) throws InterruptedException, IOException {
        for(var i:cars) {

            CarThread car = new CarThread(i,parkingSlots);
            car.car =i;
            gates[i.carGate-1]++;
            carList.add(car);
            car.start();
        }
        for(var i:carList){
           i.join();
        }
        FileOut.Print(String.format("Total Cars Served: %d\n" +
                "Current Cars in Parking: 0\n" +
                "Details:\n" +
                "- Gate 1 served %d cars.\n" +
                "- Gate 2 served %d cars.\n" +
                "- Gate 3 served %d cars.",carList.size(),gates[0],gates[1],gates[2]));
    }
}
