package org.example;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        ArrayList<Car>cars=new ArrayList<>();
        try(BufferedReader reader=new BufferedReader(new FileReader("input.txt"))){
            String info;
            while((info=reader.readLine())!=null){
                String []cutInfo= info.split(",");
                if(cutInfo[0].equals('\n'))break;
                Car temp=new Car();
                for(int i=0;i<cutInfo.length;i++){
                    String [] KeyValue =(cutInfo[i].trim().split(" "));
                    switch(i){
                        case 0:

                           temp.carGate=Integer.parseInt(KeyValue[1]);
                           break;
                        case 1:

                            temp.carNum=Integer.parseInt(KeyValue[1]);
                            break;
                        case 2:

                            temp.arriveTime=Integer.parseInt(KeyValue[1]);
                            break;
                        case 3:

                            temp.waitTime=Integer.parseInt(KeyValue[1]);
                            break;
                    }
                }

                cars.add(temp);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Parking parking=new Parking();
        parking.goToGate(cars);


    }
}