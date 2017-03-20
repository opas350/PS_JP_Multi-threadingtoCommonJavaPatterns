package runnable.model;

/**
 * Created by javier.reyes.valdez on 3/15/2017.
 *
 * This new LongWrapper class modifies the first one with some fixes to solve past bugs and performance issues
 * But it's not enough
 */
public class NewLongWrapper {

    //private Object key = new Object();
    private volatile long l;

    public NewLongWrapper(long l) {
        this.l = l;
    }


    public long getValue() {
        return l;
    }

    public void incrementValue() {
        //synchronized (key) {
            l = l + 1;
        //}
    }
}
