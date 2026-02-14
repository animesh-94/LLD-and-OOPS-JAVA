import java.util.concurrent.*;

class MyCallable implements Callable<String> {

    private final String name;

    public MyCallable(String name){
        this.name = name;
    }

    @Override
    public String call() throws Exception {
        StringBuilder result = new StringBuilder();

        for(int i = 0; i <= 5; i++){
            result.append("Callable: ")
                  .append(name)
                  .append(" is running: ")
                  .append(i)
                  .append("\n");
        }

        return result.toString();
    }
}

public class Main {

    public static void main(String[] args) {

        ExecutorService execute = Executors.newFixedThreadPool(2);

        Callable<String> callable1 = new MyCallable("Thread 1");
        Callable<String> callable2 = new MyCallable("Thread 2");

        try {
            Future<String> future1 = execute.submit(callable1);
            Future<String> future2 = execute.submit(callable2);

            System.out.println("Results from task 1:");
            System.out.println(future1.get());

            System.out.println("Results from task 2:");
            System.out.println(future2.get());

        } catch (InterruptedException | ExecutionException e) {
            System.out.println("Execution is interrupted: " + e.getMessage());
        } finally {
            execute.shutdown();
        }
    }
}
