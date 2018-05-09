import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 水井每次只能容一个桶取水
 * 水缸可容纳10桶水
 * 水桶总数为3个
 * 如何打水取水？
 *
 * @author WallfacerRZD
 * @date
 */
public class Solution3 {
    /**
     * 一个水工厂, 水井
     */
    static class Well {
        private Well() {

        }

        static Water createWater() {
            return new Water();
        }
    }

    static private BlockingQueue<Water> waterVat = new BlockingQueue<>(10);

    static private Thread bucket(String name) {

        return new Thread(() -> {
            // 水桶线程随机选择打水或取水
            // 可能出现全部打水, 或者全部取水
            Random random = new Random();
            boolean putWater = random.nextBoolean();
            if (putWater) {
                while (true) {
                    Water water = Well.createWater();
                    try {
                        waterVat.put(water);
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            } else {
                while (true) {
                    try {
                        waterVat.get();
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }

        }, name);
    }

    public static void main(String[] args) throws InterruptedException {
        int bucketSum = 3;
        for (int i = 0; i < bucketSum; i++) {
            bucket(String.format("%d号水桶", i)).start();
        }
        // 运行2秒
        Thread.sleep(2 * 1000);
        System.exit(0);
    }
}

class Water {

}

/**
 * 用管程和信号量实现的阻塞队列
 *
 * @param <E>
 */
class BlockingQueue<E> {
    private int size;
    private Queue<E> elementQueue = new LinkedList<>();
    private Lock mutex = new ReentrantLock();
    private Condition notFull = mutex.newCondition();
    private Condition notEmpty = mutex.newCondition();

    private boolean isEmpty() {
        return elementQueue.isEmpty();
    }

    private boolean isFull() {
        return elementQueue.size() == size;
    }

    BlockingQueue(int size) {
        this.size = size;
    }

    public void put(E element) throws InterruptedException {
        mutex.lock();
        try {
            while (isFull()) {
                System.out.println(String.format("水缸满了, %s先等一下哦~", Thread.currentThread().getName()));
                notFull.await();
            }
            elementQueue.offer(element);
            System.out.println(String.format("%s 打了一桶水 水缸里有%d桶水", Thread.currentThread().getName(), elementQueue.size()));
            notEmpty.signal();
        } finally {
            mutex.unlock();
        }

    }

    public void get() throws InterruptedException {
        mutex.lock();
        try {
            while (isEmpty()) {
                System.out.println(String.format("水缸空了, %s先等一下哦~", Thread.currentThread().getName()));
                notEmpty.await();
            }
            elementQueue.poll();
            System.out.println(String.format("%s 取了一桶水 水缸里有%d桶水", Thread.currentThread().getName(), elementQueue.size()));
            notFull.signal();
        } finally {
            mutex.unlock();
        }
    }
}

