public class Query {
    public void query() {
        ConnectionPool pool = new ConnectionPool();
        pool.createConn(1);
        for (int i = 0; i < 10; i++) {
            int finalI1 = i;
            Runnable task = () -> {
                synchronized (this) {
                    try {
                        int val = pool.executeQuery(finalI1);
//                        System.out.println(val);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    notifyAll();
                }
            };
            Thread newThread = new Thread(task);
            newThread.start();
        }

        pool.q();
        System.out.println("END");
    }
}
