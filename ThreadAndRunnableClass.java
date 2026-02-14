
//the drawback of this way of initializing thread is we cannot extend more than one  class
class MyThread extends Thread{
    @Override
    public void run(){
        for(int i=0; i<5; i++){
            System.out.println("Thread: " + Thread.currentThread().getId() + " :" + i);
            try {
                Thread.sleep(i*1000);
            } catch(InterruptedException e) {
                System.out.println("Thread got interrupted");
            }
        }
    }
}

//here is the solution we should implement the Runnable class
class MyRunnable implements  Runnable{
    @Override
    public void run(){
        for(int i=0; i<=5; i++){
            System.out.println("Thread: " + Thread.currentThread().getId() + " :" + i);
            try {
                Thread.sleep(i*1000);
            } catch(InterruptedException e) {
                System.out.println("Thread got interrupted");
            }
        }
    }
}

public class Main{
    public static void main (String[] args) {
        // MyThread thread1 = new MyThread();
        // MyThread thread2 = new MyThread();
        
        // thread1.start();
        // thread2.start();
        
        MyRunnable  runnable = new MyRunnable();
        
        Thread thread1 = new Thread(runnable);
        Thread thread2 = new Thread(runnable);
        
        thread1.start();
        thread2.start();
    }
}
