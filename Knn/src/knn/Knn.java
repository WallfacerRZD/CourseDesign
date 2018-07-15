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
    private static List<Point> PointsData = new LinkedList<>();

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
                PointsData.add(new Point(x, y));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        readData();
        QuadTree quadTree = new QuadTree(0, new Boundary(xMin, xMax, yMin, yMax), PointsData);


        double beginTime = System.currentTimeMillis();
        PriorityQueue<Point> quadTreeKNNPoints = quadTree.searchKnnPoints();
        System.out.println("Quad-Tree KNN: " + (System.currentTimeMillis() - beginTime) + "ms");


        PriorityQueue<Point> iterationKNNPoints = new PriorityQueue<>(
                ComputableComparatorSingleton.instance()
        );
        beginTime = System.currentTimeMillis();
        for (Point point : PointsData) {
            Utils.fixedSizeOffer(iterationKNNPoints, point);
        }
        System.out.println("Iteration KNN: " + (System.currentTimeMillis() - beginTime) + "ms");

        System.out.println(iterationKNNPoints);
        System.out.println(quadTreeKNNPoints);
        // 测试两种算法得到的结果是否相同
        while (!quadTreeKNNPoints.isEmpty() && !iterationKNNPoints.isEmpty()) {
            Point source = ComputableComparatorSingleton.source;
            Point point = quadTreeKNNPoints.poll();
            Point point1 = iterationKNNPoints.poll();
            assert (Double.compare(source.compute(point), source.compute(point1)) == 0);
        }
    }
}
