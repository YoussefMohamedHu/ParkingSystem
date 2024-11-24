package org.example;

public class SemaphoreCustomed {
    private int permits;
    private Object lock=new Object();
   public void release(){
       synchronized (lock) {
           permits++;
       }
    }
    public void acquire(){
       synchronized (lock) {
           while (permits == 4) ;
           permits--;
       }
    }
    public boolean tryAcquire(){
       synchronized (lock){
           if(permits>0) {
               permits--;
               return true;
           }
           return false;
       }

    }
    public SemaphoreCustomed(int permits){
       this.permits=permits;
    }

}
