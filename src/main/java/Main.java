import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

public class Main {

    public static final long CYCLES = 1000000000L;
    public static final int THREAD_COUNT = 5;

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        System.out.println("______________ Concurrency application starting __________________");

        parallelProcessing();
        inOrderProcessing();

        System.out.println("______________ Concurrency application ending __________________");
    }


    private static void parallelProcessing() throws ExecutionException, InterruptedException {
        long timeBeforeProcessing = new Date().getTime();
        ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);
        List<Future<Object>> futures = new ArrayList<>();
        long totalSum = 0;

        for(int i = 0; i < THREAD_COUNT; i++){
            Future<Object> foo = executor.submit(() -> {
                //Thread.sleep(3000);
                long sum=0;
                for(int j = 0; j < CYCLES; j++){
                    sum+=j;
                }
                return sum;
            });
            futures.add(foo);
        }

        //We will reach this line before sum is calculated.
        for(int i = 0; i < THREAD_COUNT; i++){
            // Will wait until the value is complete
            totalSum += (long)futures.get(i).get();
        }

        System.out.println("(PARALLEL PROCESSING) TOTAL SUM = " + totalSum);
        long timeAfterProcessing = new Date().getTime();
        System.out.println("TOTAL TIME WITH " + THREAD_COUNT + " PARALLEL COUNTINGS IS : " + (timeAfterProcessing-timeBeforeProcessing) + " ms");
        executor.shutdown();
    }

    private static void inOrderProcessing(){
        long timeBeforeProcessing = new Date().getTime();
        long totalSum = 0;
        for(int i = 0; i < THREAD_COUNT; i++){
            long sum = 0;
            for(int j = 0; j < CYCLES; j++){
                sum += j;
            }
            totalSum += sum;
        }
        System.out.println("(ORDER PROCESSING) TOTAL SUM = " + totalSum);
        long timeAfterProcessing = new Date().getTime();
        System.out.println("TOTAL TIME WITH " + THREAD_COUNT + " IN ORDER COUNTINGS IS : " + (timeAfterProcessing-timeBeforeProcessing) + " ms");
    }



    private static void parallelProcessing2(){
        // We will store the threads so that we can check if they are done
        List<Thread> threads = new ArrayList<>();

        // Creating threads
        for (int i = 0; i < THREAD_COUNT; i++) {
            Runnable task = new MyRunnable(CYCLES);
            Thread worker = new Thread(task);
            //Set the name of the thread
            worker.setName(String.valueOf(i));
            // Start the thread
            worker.start();

            // Add the thread to a list
            threads.add(worker);
        }

        int running = 0;

        do {
            running = 0;
            for (Thread thread : threads) {
                if (thread.isAlive()) {
                    running++;
                }
            }
            //System.out.println("We have " + running + " running threads. ");
        } while (running > 0);
    }


}
