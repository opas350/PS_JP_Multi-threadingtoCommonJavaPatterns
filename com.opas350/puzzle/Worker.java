package puzzle;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Javier on 3/19/2017.
 */
public class Worker extends Thread{

    private Object lock = new Object();
    private volatile boolean quittingTime = false;

    public void run() {
        while(!quittingTime) {
            System.out.println("Still working...");
        }
        System.out.println("Coffee is GOD!");
    }

    private void working() {
        try{
            Thread.sleep(300);
        }catch (InterruptedException ex){
            System.err.print(ex);
        }
    }

    synchronized void quit() throws InterruptedException {
        synchronized (lock) {
            quittingTime = true;
            System.out.println("Calling Join");
            join();
            System.out.println("Back From Join");
        }
    }

    synchronized void keepWorking() {
        synchronized (lock) {
            quittingTime = false;
            System.out.println("Keep Working");
        }
    }

    public static void main(String... args) throws InterruptedException {

        final Worker worker = new Worker();
        worker.start();

        Timer t = new Timer(true); // Daemon Tread
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                worker.keepWorking();
            }
        }, 500);

        Thread.sleep(400);
        worker.quit();
    }

}

