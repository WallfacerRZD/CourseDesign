package knn.comparator;

import knn.computable.Computable;
import knn.computable.Point;

import java.util.Comparator;

/**
 * @author WallfacerRZD
 * @date 2018/7/14 17:59
 */
public final class ComputableComparatorSingleton {
    private ComputableComparatorSingleton() {

    }

    public static Point source = new Point(34.12685485839844,-118.08755493164062);

    private static class ComparatorHolder {
        private static Comparator<Computable> instance = (o1, o2) -> {
            double distance1 = o1.compute(source);
            double distance2 = o2.compute(source);
            return Double.compare(distance1, distance2);
        };
    }

    public static Comparator<Computable> instance() {
        return ComparatorHolder.instance;
    }
}
