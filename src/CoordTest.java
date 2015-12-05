/**
 * Created by qqiu on 12/5/15.
 */

class Slave extends Thread {
    private Coordinator c;

    public void run() {
        c.register();
        int count = 0;
        System.out.println("Created thread: " + Thread.currentThread().getName());
        while(count < 1000000) {
            count++;
            System.out.println("Thread " + Thread.currentThread().getName() + " iteration: " + count);
        }
        c.unregister();
    }

    public Slave(Coordinator C) {
        c = C;
    }
}

public class CoordTest extends Coordinator {
    private int threads = 5;
    private Thread[] slaves;
    Coordinator C;

    public void TestStart() {
        for (int i = 0; i < threads; i++) {
            slaves[i].start();
        }
    }

    public void TestToggle() {
        for (int i = 0; i < threads; i++) {
            slaves[i].start();
        }
    }

    public CoordTest(int N, Thread T) {
        threads = N;
        slaves = new Thread[threads];
        C = new Coordinator();
        for (int i = 0; i < threads; i++) {
            slaves[i] = new Slave(C);
        }
    }
}

