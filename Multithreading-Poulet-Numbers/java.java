public class Parallelmplementation {
    public static void main(String[] args) throws InterruptedException {
        int nrThreads = 8; // Number of threads
        long time = System.currentTimeMillis();
        Thread threads[] = new Thread[nrThreads];
        for(int i=0; i<nrThreads; i++)
        {
            threads[i] = new Thread(new Worker((i+1), nrThreads), "T"+(i+1));
            threads[i].start();
        }
        for(int i=0; i<nrThreads; i++)
        {
            threads[i].join();
        }

        System.out.println((double)(System.currentTimeMillis() - time)/1000);
    }
}
