// Eric Jackman
// CSC 460 Program 3

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class driver {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // Part A
        System.out.println("Part A Output\n**********************************************************\n\n");
        ExecutorService ES = Executors.newFixedThreadPool(6);
        List<Future<?>> myFutures = new ArrayList<>();

        for (int i = 0; i < 120; i++) {  // start 120 sample threads and submit them to thread pool
            sampleThread temp = new sampleThread();
            Future<?> f = ES.submit(temp);
            myFutures.add(f);
        }

        for (Future<?> curfuture : myFutures) {  // block until all threads have finished execution
            curfuture.get();
        }

        ES.shutdown();

        //Part B
        System.out.println("\n\n\nPart B Output\n**********************************************************\n\n");
        ExecutorService ES2 = Executors.newCachedThreadPool();
        List<Future<Integer>> intFutures = new ArrayList<>();

        for (int i = 0; i < 750; i++) {  // start 750 callable threads and submit them to thread pool
            sampleCallable temp = new sampleCallable(i);
            Future<Integer> f = ES2.submit(temp);
            intFutures.add(f);
        }

        int i = 0;
        for (Future<Integer> curfuture : intFutures) {  // output result from each thread
            System.out.println("Result from Thread-" + i++ + ": " + curfuture.get());
        }

        ES2.shutdown();
        Thread.sleep(2000);
    }
}
