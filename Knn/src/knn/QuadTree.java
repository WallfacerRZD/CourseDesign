package knn;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author WallfacerRZD
 * @date 2018/7/13 14:35
 */
public class QuadTree {
    private static final int MAX_CAPACITY = 1000;

    private final int level;

    private final Boundary boundary;

    private List<Point> points;

    private QuadTree northWest;

    private QuadTree northEast;

    private QuadTree southWest;

    private QuadTree southEast;

    public QuadTree(int level, Boundary boundary) {
        this.level = level;
        this.boundary = boundary;
        points = new LinkedList<>();
    }

    public int getLevel() {
        return level;
    }

    public Boundary getBoundary() {
        return boundary;
    }

    public List<Point> getPoints() {
        return points;
    }

    public static void dfs(QuadTree tree) {
        if (tree == null) {
            return;
        }
        if (tree.isLeaf()) {
            System.out.println("-------------------");
            System.out.println("level: " + tree.getLevel());
            System.out.print("Points: ");
            System.out.println(tree.getPoints());
            System.out.println("-------------------");
        } else {
            dfs(tree.northWest);
            dfs(tree.northEast);
            dfs(tree.southWest);
            dfs(tree.southEast);
        }
    }

    public boolean isLeaf() {
        return points != null;
    }

    /**
     * 分裂Q-Tree, 并将存储在该树中的点转移到相应的子树中
     */
    private void split() {
        double xMin = boundary.getxMin();
        double xMax = boundary.getxMax();
        double yMin = boundary.getyMin();
        double yMax = boundary.getyMax();
        double xOffset = xMin + (xMax - xMin) / 2;
        double yOffset = yMin + (yMax - yMin) / 2;
        northWest = new QuadTree(level + 1, new Boundary(xMin, xOffset, yMin, yOffset));
        northEast = new QuadTree(level + 1, new Boundary(xOffset, xMax, yMin, yOffset));
        southWest = new QuadTree(level + 1, new Boundary(xMin, xOffset, yOffset, yMax));
        southEast = new QuadTree(level + 1, new Boundary(xOffset, xMax, yOffset, yMax));
        for (Point point : points) {
            insertIntoChild(point);
        }
        points = null;
    }

    private void insertIntoChild(Point point) {
        if (northWest == null) {
            throw new RuntimeException("The child is null");
        }
        if (northWest.getBoundary().inRange(point)) {
            northWest.getPoints().add(point);
        } else if (northEast.getBoundary().inRange(point)) {
            northEast.getPoints().add(point);
        } else if (southWest.getBoundary().inRange(point)) {
            southWest.getPoints().add(point);
        } else {
            southEast.getPoints().add(point);
        }
    }

    public void insert(Point point) {
        if (!boundary.inRange(point)) {
            return;
        }
        if (!isLeaf()) {
            if (northWest == null) {
                throw new RuntimeException("The child is null");
            }
            if (northWest.getBoundary().inRange(point)) {
                northWest.insert(point);
            } else if (northEast.getBoundary().inRange(point)) {
                northEast.insert(point);
            } else if (southWest.getBoundary().inRange(point)) {
                southWest.insert(point);
            } else {
                southEast.insert(point);
            }
            return;
        }
        if (points.size() < MAX_CAPACITY) {
            points.add(point);
            return;
        }
        split();
        insertIntoChild(point);
    }
}
