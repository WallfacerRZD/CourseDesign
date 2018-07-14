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
        for (Point point : points) {
            root.insert(point);
        }

        PriorityQueue<QuadTree> quadTrees = new PriorityQueue<>(
                5,
                ComputableComparatorSingleton.instance()
        );
        PriorityQueue<Point> knnPoints = new PriorityQueue<>(
                5,
                ComputableComparatorSingleton.instance()
        );
        quadTrees.offer(root);
        while (!quadTrees.isEmpty()) {
            QuadTree tree = quadTrees.poll();
            if (tree != null) {
                if (!tree.isLeaf()) {
                    Utils.fixedSizeOffer(quadTrees, tree.getNorthWest());
                    Utils.fixedSizeOffer(quadTrees, tree.getNorthEast());
                    Utils.fixedSizeOffer(quadTrees, tree.getSouthEast());
                    Utils.fixedSizeOffer(quadTrees, tree.getSouthWest());
                } else {
                    for (Point point : tree.getPoints()) {
                        Utils.fixedSizeOffer(knnPoints, point);
                    }
                }
            }
        }
        System.out.println(knnPoints);
    }
}
