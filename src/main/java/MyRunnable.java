
public class MyRunnable implements Runnable {

    private final long countUntil;
    private long sum;

    MyRunnable(long countUntil) {
        this.countUntil = countUntil;
    }

    @Override
    public void run() {
        this.sum = 0;
        for (long i = 1; i < countUntil; i++) {
            sum += i;
        }
    }

    public long getSum(){
        return sum;
    }

}
