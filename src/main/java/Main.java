import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

public class Main {

    public static final int THREAD_COUNT = 5;

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        System.out.println("______________ Concurrency application starting __________________");

        //CountTimeComparison.parallelProcessing();
        //CountTimeComparison.inOrderProcessing();


        BruteForceTest.guessKey();



        System.out.println("______________ Concurrency application ending __________________");
    }

}
