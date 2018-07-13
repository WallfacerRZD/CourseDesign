package knn;

/**
 * @author WallfacerRZD
 * @date 2018/7/13 15:33
 */
public class Point {
    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }

    private final double x;

    private final double y;
}
