import java.math.BigDecimal;

public class Worker implements Runnable {

    static Object lock = new Object();
    Integer threadNumber;
    Integer nrThreads;
    volatile Integer threadToRun=1;

    public Worker(Integer threadNumber, Integer nrThreads) {
        this.threadNumber = threadNumber;
        this.nrThreads = nrThreads;
    }

    private static boolean isComposite(int n)
    {
        // Corner cases
        if (n <= 1)  return false;
        if (n <= 3)  return false;

        // This is checked so that we can skip
        // middle five numbers in below loop
        if (n%2 == 0 || n%3 == 0) return true;

        for (int i=5; i*i<=n; i=i+6)
            if (n%i == 0 || n%(i+2) == 0)
                return true;
        return false;
    }

    public void run() {
        BigDecimal two = new BigDecimal("2");
        int p=threadNumber*2+1;
        while (p < 250000) {
                if (isComposite(p) == true && two.pow(p - 1).remainder(BigDecimal.valueOf(p)).equals(BigDecimal.ONE))
                    System.out.println(Thread.currentThread().getName() + " found " + p);
                p += nrThreads*2;
        }
    }
}
