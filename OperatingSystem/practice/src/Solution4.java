import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Customer {

}

/**
 * 理发店里有1位理发师、1把理发椅和N把供等候理发的顾客坐的椅子
 * 如果没有顾客，理发师便在理发椅上睡觉
 * 当一个顾客到来时，他必须先叫醒理发师
 * 如果理发师正在理发时又有顾客来到，则如果有空椅子可坐，他们就坐下来等。如果没有空椅子，他就离开。
 *
 * @author WallfacerRZD
 * @date 2018/5/9 13:50
 */
public class Solution4 {
    /**
     * 用管程和信号量实现的阻塞队列
     *
     * @param <E>
     */
    private static class BlockingQueue1<E> {
        private int size;
        private Queue<E> elementQueue = new LinkedList<>();
        private Lock mutex = new ReentrantLock();
        private Condition notEmpty = mutex.newCondition();

        private boolean isEmpty() {
            return elementQueue.isEmpty();
        }

        private boolean isFull() {
            return elementQueue.size() == size;
        }

        BlockingQueue1(int size) {
            this.size = size;
        }

        void put(E element) {
            mutex.lock();
            try {
                if (isFull()) {
                    System.out.println("客人满了, 请您滚出去");
                } else {
                    if (!tonyIsWorking) {
                        elementQueue.offer(element);
                        notEmpty.signal();
                        tonyIsWorking = true;
                        System.out.println("客人来了, Tony老师起床干活了");
                    } else {
                        elementQueue.offer(element);
                    }
                    System.out.println(String.format("一位客人入座, 座位数(%d/%d)", elementQueue.size(), this.size));

                }
            } finally {
                mutex.unlock();
            }
        }

        void get() throws InterruptedException {
            mutex.lock();
            try {
                while (isEmpty()) {
                    System.out.println("客人走光, Tony老师睡觉了zzzZZZ");
                    tonyIsWorking = false;
                    notEmpty.await();
                }
                elementQueue.poll();
                System.out.println(String.format("Tony老师请出一位客人,开始剃头 座位数(%d/%d)", elementQueue.size(), size));
            } finally {
                mutex.unlock();
            }
        }
    }

    private static BlockingQueue1<Customer> chairs = new BlockingQueue1(4);

    private static boolean tonyIsWorking = false;

    private static void generateCustomers() {
        new Thread(() -> {
            Random random = new Random();
            while (true) {
                new Thread(() -> {
                    chairs.put(new Customer());
                }).start();
                try {
                    Thread.sleep(random.nextInt(10));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private static void tonyThread() {
        new Thread(() -> {
            Random random = new Random();
            while (true) {
                try {
                    chairs.get();
                    Thread.sleep(random.nextInt(10));
                    System.out.println("Tony老师剃了一碗光头");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static void main(String[] args) throws InterruptedException {
        generateCustomers();
        tonyThread();

        // 运行0.5秒
        Thread.sleep(500);
        System.exit(0);
    }
}
