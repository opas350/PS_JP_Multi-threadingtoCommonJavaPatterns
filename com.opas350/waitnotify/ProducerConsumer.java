package waitnotify;


/**
 * Created by Javier on 3/16/2017.
 */
public class ProducerConsumer {
    private static Object Lock = new Object();

    private static int[] buffer;
    private static int count;

    static class Producer {
        void produce() {
            synchronized (Lock) {
                if (isFull(buffer)) {
                    try {
                        Lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                buffer[count++] = 1;
                Lock.notify();
            }
        }
    }

    static class Consumer {
        void consume() {
            synchronized (Lock) {
                while (isEmpty(buffer)) {
                    try {
                        Lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                buffer[--count] = 0;
                Lock.notify();
            }
        }
    }

    static boolean isFull(int[] buffer) {
        return count == buffer.length;
    }

    static boolean isEmpty(int[] buffer) {
        return count == 0;
    }

    public static void main(String... strings) throws  InterruptedException {

        buffer = new int[10];
        count = 0;

        Producer producer = new Producer();
        Consumer consumer = new Consumer();

        Runnable produceTask = () -> {
            for(int i = 0; i < 50; i++) {
                producer.produce();
            }
            System.out.println("Done producing");
        };

        Runnable consumeTask = () -> {
            for(int i = 0; i < 50; i++) {
                consumer.consume();
            }
            System.out.println("Done consuming");
        };

        Thread consumerThread = new Thread(consumeTask);
        Thread producerThread = new Thread(produceTask);

        consumerThread.start();
        producerThread.start();

        consumerThread.join();
        producerThread.join();

        System.out.println("Data in the buffer: " + count);
    }
}
