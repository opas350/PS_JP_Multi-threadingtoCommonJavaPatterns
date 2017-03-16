/**
 * Created by javier.reyes.valdez on 3/15/2017.
 */
public class FirstRunnable {

    public static void main(String[] args) {

        Runnable runnable =() -> {
          System.out.println("I'm running in " + Thread.currentThread().getName());
        };

        Thread t = new Thread(runnable);
        t.setName("My Thread");
        //t.start(); // Should not be used if want to start new thread
    }
}
