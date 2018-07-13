package knn;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

/**
 * @author WallfacerRZD
 * @date 2018/7/13 14:35
 */
public class Knn {
    private static List<Point> readData() {
        List<Point> points = new LinkedList<>();
        try (BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(new FileInputStream("F:\\_projects\\CourseDesign\\Knn\\src\\knn\\la_points.txt")))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] data = line.split(",");
                points.add(new Point(Double.parseDouble(data[0]), Double.parseDouble(data[1])));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return points;
    }

    public static void main(String[] args) {
        QuadTree root = new QuadTree(0, new Boundary(30, 35, -120, -110));
        List<Point> points = readData();
        for (Point point : points) {
            root.insert(point);
        }
        QuadTree.dfs(root);
    }
}
