import java.util.concurrent.*;

class Task implements Runnable{
    private final int TaskId;
    
    public Task(int TaskId){
        this.TaskId = TaskId;
    }
    
    @Override
    public void run(){
        System.out.println(Thread.currentThread().getName() + " -Starting  Task  " + TaskId);
        
        try{
            Thread.sleep(2000);
            
            synchronized (this){
                System.out.println(Thread.currentThread().getName() + " -Waiting Task " + TaskId);
                
                this.wait(1000);
            }
            
            System.out.println(Thread.currentThread().getName() + " - Task " + TaskId + " COMPLETED"); 
        } catch(InterruptedException e){
            Thread.currentThread().interrupt();
        }
    }
}

public class Main{
    public static void main (String[] args) {
         ExecutorService executor = Executors.newFixedThreadPool(2);
        System.out.println("Thread Pool created: ");
        
        for(int i=0; i<5; i++){
            executor.execute(new Task(i));
        }
        
        executor.shutdown();
        
        try{
            if(executor.awaitTermination(10, TimeUnit.SECONDS)){
                executor.shutdownNow();
                System.out.println("Forcing shutdown...");
            } 
        } catch (InterruptedException e){
            executor.shutdownNow();
        }
    }
}
