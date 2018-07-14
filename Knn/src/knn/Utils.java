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

    public static <E extends Computable>
    void fixedSizeOffer(PriorityQueue<E> queue, E newElement) {
        if (queue.size() >= K) {
            E oldElement = queue.poll();
            if (oldElement != null) {
                if (ComputableComparatorSingleton.instance().compare(oldElement, newElement) < 1) {
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
