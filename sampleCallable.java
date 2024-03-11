// Eric Jackman
// CSC 460 Program 3

import java.util.Random;
import java.util.concurrent.Callable;

public class sampleCallable implements Callable<Integer> {
    private final int threadID;

    public sampleCallable(int id) {
        this.threadID = id;
    }

    @Override
    public Integer call() throws Exception {
        Random rand = new Random();

        // Sleep for 100 - 200 milliseconds
        long sleepTime = (long) (rand.nextDouble() * 100 + 100);
        Thread.sleep(sleepTime);

        // Get the square of a random int between 1 and 100
        int num = (int) (rand.nextDouble() * 99 + 1);
        System.out.println("Thread-" + threadID + " calculating square of " + num);

        return num * num;
    }
}
