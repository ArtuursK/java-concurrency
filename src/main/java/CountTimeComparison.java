import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CountTimeComparison {

    public static final long CYCLES = 1000000000L;
    public static final int THREAD_COUNT = 5;

    public static void parallelProcessing() throws ExecutionException, InterruptedException {
        long timeBeforeProcessing = new Date().getTime();
        ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);
        List<Future<Object>> futures = new ArrayList<>();
        long totalSum = 0;

        for(int i = 0; i < THREAD_COUNT; i++){
            Future<Object> foo = executor.submit(() -> {
                //Thread.sleep(3000);

                //execute this task
                long sum=0;
                for(int j = 0; j < CYCLES; j++){
                    sum += j;
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

    public static void inOrderProcessing(){
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
}
