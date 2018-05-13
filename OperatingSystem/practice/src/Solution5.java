import java.util.concurrent.Semaphore;

/**
 * @author WallfacerRZD
 * @date 2018/5/10 0:33
 */
public class Solution5 {
    private static int customerCount = 0;
    private static int carCount = 0;
    private static Semaphore mutex = new Semaphore(1);
    private static Semaphore CarQueue = new Semaphore(0);

    public static void main(String[] args) {

    }

    private static void carThread() {
        new Thread(() -> {
            while (true) {
                new Thread(() -> {
                    while (customerCount < 2) {
                        try {
                            CarQueue.acquire();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println("观光车等待游客");
                    }
                    System.out.println("一辆观光车启动了");
                });
            }
        }).start();

    }

    private static void CustomerThread() {
        new Thread(()->{
           while (true) {
               new Thread(()->{
                   try {
                       mutex.acquire();
                       customerCount++;

                   } catch (InterruptedException e) {
                       e.printStackTrace();
                   } finally {
                       mutex.release();
                   }
               }).start();
           }
        }).start();
    }

}
