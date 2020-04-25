import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class APIDOSTest {

    private static final int THREAD_COUNT = 10;
    private static final int CONCURRENT_USER_COUNT = 40;
    private static final String API_URL = "http://localhost:3000";

    public static void testApi() throws Exception {
        long timeBeforeProcessing = new Date().getTime();
        ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);

        for (int i = 0; i < CONCURRENT_USER_COUNT; i++) {
            Runnable worker = new MyRunnable(API_URL);
            executor.execute(worker);
        }

        executor.shutdown();

        // Wait until all threads are finish
        while (!executor.isTerminated()) {}

        long timeAfterProcessing = new Date().getTime();
        System.out.println("TOTAL TIME WITH " + THREAD_COUNT + " THREADS AND " + CONCURRENT_USER_COUNT + " CONCURRENT USERS |"
                + " TOTAL PARALLEL EXECUTION TIME WAS : " + (timeAfterProcessing-timeBeforeProcessing) + " ms");
        System.out.println("\nFinished all threads");
    }


    public static class MyRunnable implements Runnable {
        private final String url;

        MyRunnable(String url) {
            this.url = url;
        }

        @Override
        public void run() {

            String result = "";
            int code = 200;
            try {
                URL siteURL = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) siteURL.openConnection();

                String auth = "user" + ":" + "password";
                byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes(StandardCharsets.UTF_8));
                String authHeaderValue = "Basic " + new String(encodedAuth);
                connection.setRequestProperty("Authorization", authHeaderValue);
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(3000);
                connection.connect();

                code = connection.getResponseCode();
                if (code == 200) {
                    result = "-> Green <-\t" + "Code: " + code;
                    ;
                } else {
                    result = "-> Yellow <-\t" + "Code: " + code;
                }
            } catch (Exception e) {
                result = "-> Red <-\t" + "Wrong domain - Exception: " + e.getMessage();

            }
            System.out.println(url + "\t\tStatus:" + result);
        }
    }
}
