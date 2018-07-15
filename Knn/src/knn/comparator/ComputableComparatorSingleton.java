package knn.comparator;

import knn.computable.Computable;
import knn.computable.Point;

import java.util.Comparator;

/**
 * @author WallfacerRZD
 * @date 2018/7/14 17:59
 * Comparator的单例, 用来定义PriorityQueue中元素的顺序
 * 距离源点source较远的Point在堆顶
 * 距离源点source较近的QuadTree在堆顶
 */
public final class ComputableComparatorSingleton {
    private ComputableComparatorSingleton() {

    }

    public static Point source = new Point(34.42616271972656,-119.70829010009766);

    private static class ComparatorHolder {
        private static Comparator<Computable> pointComparatorInstance = (o1, o2) -> {
            double distance1 = o1.compute(source);
            double distance2 = o2.compute(source);
            return -Double.compare(distance1, distance2);
        };

        private static Comparator<Computable> quadTreeComparatorInstance = (o1, o2) -> {
            double distance1 = o1.compute(source);
            double distance2 = o2.compute(source);
            return Double.compare(distance1, distance2);
        };
    }

    public static Comparator<Computable> pointComparatorInstance() {
        return ComparatorHolder.pointComparatorInstance;
    }

    public static Comparator<Computable> quadTreeComparatorInstance() {
        return ComparatorHolder.quadTreeComparatorInstance;
    }
}
