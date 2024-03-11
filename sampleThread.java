// Eric Jackman
// CSC 460 Program 3

import java.util.Random;

public class sampleThread extends Thread {
    @Override
    public void run() {
        Random rand = new Random();
        long sleepTime = (long) (rand.nextDouble() * 1000);

        // Sleep for random amount of time between 0 and 1 second
        System.out.println(getName() + " is sleeping for " + sleepTime + " milliseconds.");
        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(getName() + " is NOW AWAKE");
    }
}
