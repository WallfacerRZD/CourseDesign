package knn;

import knn.comparator.ComputableComparatorSingleton;
import knn.computable.Computable;
import knn.computable.Point;

import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * @author WallfacerRZD
 * @date 2018/7/13 14:35
 */
public class QuadTree implements Computable {
    private static final int MAX_CAPACITY = 1000;

    private final int level;

    private final Boundary boundary;

    private List<Point> points;

    private QuadTree northWest;

    private QuadTree northEast;

    private QuadTree southWest;

    private QuadTree southEast;

    public QuadTree getNorthWest() {
        return northWest;
    }

    public QuadTree getNorthEast() {
        return northEast;
    }

    public QuadTree getSouthWest() {
        return southWest;
    }

    public QuadTree getSouthEast() {
        return southEast;
    }

    public QuadTree(int level, Boundary boundary, List<Point> pointsData) {
        this.level = level;
        this.boundary = boundary;
        points = new LinkedList<>();
        for (Point point : pointsData) {
            insert(point);
        }
    }

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

    public PriorityQueue<Point> searchKnnPoints() {
        PriorityQueue<QuadTree> quadTreePQ = new PriorityQueue<>(
                ComputableComparatorSingleton.instance()
        );
        PriorityQueue<Point> knnPoints = new PriorityQueue<>(
                ComputableComparatorSingleton.instance()
        );
        quadTreePQ.offer(this);
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
        return knnPoints;
    }

    @Override
    public double compute(Point point) {
        if (point == null) {
            return Double.POSITIVE_INFINITY;
        }
        double xMin = boundary.getxMin();
        double xMax = boundary.getxMax();
        double yMin = boundary.getyMin();
        double yMax = boundary.getyMax();

        if (boundary.inRange(point)) {
            return 0.0;
        }
        double x = point.getX();
        double y = point.getY();
        if (x >= xMin && x <= xMax) {
            return Double.min(Math.abs(y - yMax), Math.abs(yMin - y));
        } else if (y >= yMin && y <= yMax) {
            return Double.min(Math.abs(x - xMax), Math.abs(xMin - x));
        } else {
            if (x < xMin && y > yMax) {
                return Math.sqrt((x - xMin) * (x - xMin) + (y - yMax) * (y - yMax));
            } else if (x > xMax && y > yMax) {
                return Math.sqrt((x - xMax) * (x - xMax) + (y - yMax) * (y - yMax));
            } else if (x < xMin && y < yMin) {
                return Math.sqrt((x - xMin) * (x - xMin) + (y - yMin) * (y - yMin));
            } else {
                return Math.sqrt((x - xMax) * (x - xMax) + (y - yMin) * (y - yMin));
            }
        }
    }
}
