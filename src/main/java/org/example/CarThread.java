package org.example;

import java.io.IOException;
import java.util.concurrent.Semaphore;

public class CarThread extends Thread {
    private static int currentlyParked = 0;

    private static final Object lock = new Object();


    Car car;
    private final SemaphoreCustomed parkingSpots;

    public CarThread(Car car, SemaphoreCustomed parkingSpots) {
        this.car = car;
        this.parkingSpots = parkingSpots;
    }

    @Override
    public void run() {
        try {

            Thread.sleep(car.arriveTime * 1000);


            announceArrival();


            if (parkCar()) {

                Thread.sleep(car.waitTime * 1000);

                leaveParkingLot();
            }

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Thread interrupted for car " + car.carNum);
        } catch (IOException e) {
            System.err.println("Error writing to file for car " + car.carNum);
        }
    }

    private void announceArrival() throws IOException {
        FileOut.Print(String.format("Car %d from Gate %d arrived at time %d",
                car.carNum, car.carGate, car.arriveTime));
    }

    private boolean parkCar() throws IOException {
        long startWaitTime = System.currentTimeMillis();
        boolean isWaiting = false;


        while (true) {

            if (parkingSpots.tryAcquire()) {

                synchronized (lock) {
                    currentlyParked++;

                    long totalWaitTime = (System.currentTimeMillis() - startWaitTime) / 1000;

                    if (isWaiting) {
                        FileOut.Print(String.format(
                                "Car %d from Gate %d parked after waiting for %d units of time. (Parking Status: %d spots occupied)",
                                car.carNum, car.carGate, totalWaitTime, currentlyParked));
                    } else {
                        FileOut.Print(String.format(
                                "Car %d from Gate %d parked. (Parking Status: %d spots occupied)",
                                car.carNum, car.carGate, currentlyParked));
                    }
                }
                return true;
            }


            synchronized (lock) {
                if (currentlyParked == 4 && !isWaiting) {
                    FileOut.Print(String.format("Car %d from Gate %d waiting for a spot.",
                            car.carNum, car.carGate));
                    isWaiting = true;
                }
            }

        }
    }

    private void leaveParkingLot() throws IOException {
        synchronized (lock) {
            currentlyParked--;
            FileOut.Print(String.format(
                    "Car %d from Gate %d left after %d units of time. (Parking Status: %d spots occupied)",
                    car.carNum, car.carGate, car.waitTime, currentlyParked));
            parkingSpots.release();
        }
    }
}