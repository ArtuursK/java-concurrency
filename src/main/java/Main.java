import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

public class Main {

    public static final int THREAD_COUNT = 5;

    public static void main(String[] args) throws Exception {
        System.out.println("______________ Concurrency application starting __________________");

        //CountTimeComparison.parallelProcessing();
        //CountTimeComparison.inOrderProcessing();


        //BruteForceTest.guessKey();
        APIDOSTest.testApi();


        System.out.println("______________ Concurrency application end __________________");
    }

}
