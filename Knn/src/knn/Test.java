package knn;

import knn.comparator.ComputableComparatorSingleton;
import knn.computable.Point;

/**
 * @author WallfacerRZD
 * @date 2018/7/15 9:23
 */
public class Test {
    public static void main(String[] args) {
        Point point1 = new Point(34.12763977050781,-118.09089660644531);
        Point point2 = new Point(34.12763977050781,-118.09089660644531);
        System.out.println(ComputableComparatorSingleton.source.compute(point1));
        System.out.println(ComputableComparatorSingleton.source.compute(point2));
    }
}
