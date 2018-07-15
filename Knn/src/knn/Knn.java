package knn;

import knn.comparator.ComputableComparatorSingleton;
import knn.computable.Point;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * @author WallfacerRZD
 * @date 2018/7/13 14:35
 */
public class Knn {
    private static List<Point> points = new LinkedList<>();

    private static double xMin = Double.MAX_VALUE;

    private static double xMax = -Double.MAX_VALUE;

    private static double yMin = Double.MAX_VALUE;

    private static double yMax = -Double.MAX_VALUE;

    private static void readData() {
        try (BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(new FileInputStream("F:\\_projects\\CourseDesign\\Knn\\src\\knn\\la_points.txt")))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] data = line.split(",");
                double x = Double.parseDouble(data[0]);
                double y = Double.parseDouble(data[1]);
                xMin = x > xMin ? xMin : x;
                xMax = x > xMax ? x : xMax;
                yMin = y > yMin ? yMin : y;
                yMax = y > yMax ? y : yMax;
                points.add(new Point(x, y));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        readData();
        QuadTree root = new QuadTree(0, new Boundary(xMin, xMax, yMin, yMax));
        PriorityQueue<Point> knnPoints1 = new PriorityQueue<>(
                ComputableComparatorSingleton.instance()
        );
        for (Point point : points) {
            root.insert(point);
            // Utils.fixedSizeOffer(knnPoints1, point);
        }
        PriorityQueue<QuadTree> quadTreePQ = new PriorityQueue<>(
                ComputableComparatorSingleton.instance()
        );
        PriorityQueue<Point> knnPoints = new PriorityQueue<>(
                ComputableComparatorSingleton.instance()
        );
        double beginTime = System.currentTimeMillis();
        quadTreePQ.offer(root);
        while (!quadTreePQ.isEmpty()) {
            QuadTree tree = quadTreePQ.poll();
            if (tree != null) {
                if (!tree.isLeaf()) {
                    Utils.fixedSizeOffer(quadTreePQ, tree.getNorthWest());
                    Utils.fixedSizeOffer(quadTreePQ, tree.getNorthEast());
                    Utils.fixedSizeOffer(quadTreePQ, tree.getSouthEast());
                    Utils.fixedSizeOffer(quadTreePQ, tree.getSouthWest());
                } else {
                    for (Point point : tree.getPoints()) {
                        Utils.fixedSizeOffer(knnPoints, point);
                    }
                }
            }
        }
        System.out.println(System.currentTimeMillis() - beginTime);
        beginTime = System.currentTimeMillis();
        for (Point point : points) {
            Utils.fixedSizeOffer(knnPoints1, point);
        }
        System.out.println(System.currentTimeMillis() - beginTime);
/*        System.out.println(knnPoints);
        System.out.println(knnPoints1);*/
        while (!knnPoints.isEmpty() && !knnPoints1.isEmpty()) {
            Point source = ComputableComparatorSingleton.source;
            Point point = knnPoints.poll();
            Point point1 = knnPoints1.poll();
            assert (Double.compare(source.compute(point), source.compute(point1)) == 0);
        }
    }
}
