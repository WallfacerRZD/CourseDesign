import java.util.Timer;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.lang.InterruptedException;

/**
 * @author WallfacerRZD
 * @date 2018/5/10 19:58
 */
public class Bridge {
    private static Semaphore SouthEntry = new Semaphore(1);
    private static Semaphore NorthEntry = new Semaphore(1);

    private static Runnable south2NorthThread() {
        return () -> {
            while (true) {
                try {
                    NorthEntry.acquire();
                    System.out.println("进入桥　↑　南");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    System.out.println("通过桥↑　　面");
                    NorthEntry.release();
                }
                try {
                    SouthEntry.acquire();
                    System.out.println("离开桥　↑　北");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    SouthEntry.release();
                }
            }
        };
    }

    private static Runnable north2SouthThread() {
        return () -> {
            while (true) {
                try {
                    SouthEntry.acquire();
                    System.out.println("进入桥　↓　北");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    System.out.println("通过桥　　↓面");
                    SouthEntry.release();
                }
                try {
                    NorthEntry.acquire();
                    System.out.println("离开桥　↓　南");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    NorthEntry.release();
                }
            }
        };
    }

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        executor.execute(south2NorthThread());
        executor.execute(north2SouthThread());
        Thread.sleep(100);
        System.exit(0);
    }
}

