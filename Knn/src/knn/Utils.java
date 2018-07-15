package knn;

import knn.comparator.ComputableComparatorSingleton;
import knn.computable.Computable;

import java.util.PriorityQueue;

/**
 * @author WallfacerRZD
 * @date 2018/7/14 12:05
 */
public final class Utils {
    private static final int K = 10;

    /**
     * JDK的PriorityQueue是unbounded的,
     * 为了实现bounded PriorityQueue编写此辅助函数.
     * 如果queue的size大于了K, 从queue取出优先级最高的元素oldElement
     * 与要添加的元素newElement比较, 如果newElement的优先级比oldElement的优先级高
     * 则向队列中添加newElement, 否则添加oldElement
     * @param queue PriorityQueue
     * @param newElement 要添加的新元素
     * @param <E> 继承了Computable接口的类, Point 或者 QuadTree
     */
    public static <E extends Computable>
    void fixedSizeOffer(PriorityQueue<E> queue, E newElement) {
        if (queue.size() >= K) {
            E oldElement = queue.poll();
            if (oldElement != null) {
                if (ComputableComparatorSingleton.instance().compare(
                        oldElement, newElement) >= 0) {
                    queue.offer(oldElement);
                } else {
                    queue.offer(newElement);
                }
            }
        } else {
            queue.offer(newElement);
        }
    }
}
