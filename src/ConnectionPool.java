import java.util.Random;

public class ConnectionPool {

    BlockingQueue<ConnectionPool> queue;
    public void createConn(int capacity){
        queue = new BlockingQueue<>(capacity);
        for (int i = 0; i < capacity; i++) {
            queue.put(new ConnectionPool(), i);
        }
    }

    public int executeQuery(int i) throws InterruptedException {
        System.out.println("Execute Q for " + i);
        Random r = new Random();
        Runnable task = () -> {
            synchronized (this) {
                //                    System.out.println("INSIDE" + i);
                queue.remove();
                try {
                    Thread.sleep(r.nextInt((2000 - 1000) + 1));
                    System.out.println("Completed Q for " + i);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
//                    wait(1000 + r.nextInt((6000 - 1000) + 1));
                notifyAll();
            }
        };
        Thread newThread = new Thread(task);
//        Thread.sleep(10000 + r.nextInt((20000 - 1000) + 1));
        newThread.start();
        queue.put(new ConnectionPool(), i);
//        System.out.println("EOF" + i);
    return 109;
    }

    public void q (){
        System.out.println(queue.queue);
    }
}
