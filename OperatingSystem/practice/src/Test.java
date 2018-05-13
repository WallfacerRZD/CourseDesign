import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author WallfacerRZD
 * @date 2018/5/10 23:28
 */
public class Test {
    private static Lock lock = new ReentrantLock();
    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            new Thread(()->{
                lock.lock();
                System.out.println("---------");
                System.out.println("asdf");
                System.out.println("asdgasdg--");
                System.out.println("assahasdhasdh");
                System.out.println("---------");
                lock.unlock();
            }).start();
        }

    }


}


