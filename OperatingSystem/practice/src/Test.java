import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;

/**
 * @author WallfacerRZD
 * @date 2018/5/10 23:28
 */
public class Test {
    private static Lock northLock = new ReentrantLock();
    private static Lock southLock = new ReentrantLock();
    private static Condition northEmpty = northLock.newCondition();
    private static Condition southEmpty = southLock.newCondition();
    private static boolean southFull = false;
    private static boolean northFull = false;

    private static Runnable thread1() {
        return () -> {
            while (true) {
                try {
                    southLock.lock();
                    while (southFull) {
                        try {
                            southEmpty.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    southFull = true;

                    System.out.println("进入桥　↑　南");
                    System.out.println("通过桥↑　　面");
                } finally {
                    southFull = false;
                    southEmpty.signal();
                    southLock.unlock();
                }
                try {
                    northLock.lock();
                    while (northFull) {
                        try {
                            northEmpty.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    northFull = true;
                    System.out.println("离开桥　↑　北");
                } finally {
                    northFull = false;
                    northEmpty.signal();
                    northLock.unlock();
                }
            }
        };
    }

    private static Runnable thread2() {
        return () -> {
            while (true) {
                try {
                    northLock.lock();
                    while (northFull) {
                        try {
                            northEmpty.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    northFull = true;
                    System.out.println("进入桥　↓　北");
                    System.out.println("通过桥　　↓面");
                } finally {
                    northFull = false;
                    northEmpty.signal();
                    northLock.unlock();
                }
                try {
                    southLock.lock();
                    while (southFull) {
                        try {
                            southEmpty.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    southFull = true;
                    System.out.println("离开桥　↓　南");
                } finally {
                    southFull = false;
                    southEmpty.signal();
                    southLock.unlock();
                }
            }
        };
    }

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            new Thread(thread1()).start();
            new Thread(thread2()).start();
        }

        Thread.sleep(10000);
        System.exit(0);
    }


}


