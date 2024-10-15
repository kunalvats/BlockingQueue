import java.util.LinkedList;
import java.util.Queue;

public class BlockingQueue<T> {
    Queue<T> queue;
    int capacity;

    public BlockingQueue(int capacity){
        this.capacity = capacity;
        this.queue = new LinkedList<>();
    }


    public void put(T obj, int i){
        System.out.println("PUT" + i);
        Runnable task = () -> {
            synchronized (this) {
                while(queue.size() == capacity) {
                    try {
                        System.out.println("Waiting for " + i);
//                        System.out.println(queue);
                        wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                queue.add(obj);
                notifyAll();
            }
        };
        Thread newThread = new Thread(task);
        newThread.start();
    }

    public void remove() {
        Runnable task = () -> {
            synchronized (this) {
                while (queue.isEmpty()){
                    try {
                        System.out.println("Waiting for remove");
                        System.out.println(queue);
                        wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                queue.remove();
                notifyAll();
            }
        };
        Thread newThread = new Thread(task);
        newThread.start();
    }

}
