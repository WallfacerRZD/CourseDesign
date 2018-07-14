package knn;

import knn.computable.Point;

/**
 * @author WallfacerRZD
 * @date 2018/7/13 15:49
 */
public class Boundary {
    public Boundary(double xMin, double xMax, double yMin, double yMax) {
        this.xMin = xMin;
        this.xMax = xMax;
        this.yMin = yMin;
        this.yMax = yMax;
    }

    public boolean inRange(Point point) {
        double x = point.getX();
        double y = point.getY();
        return x >= xMin &&
                x <= xMax &&
                y >= yMin &&
                y <= yMax;
    }

    public double getxMin() {
        return xMin;
    }

    public double getxMax() {
        return xMax;
    }

    public double getyMin() {
        return yMin;
    }

    public double getyMax() {
        return yMax;
    }

    private final double xMin;

    private final double xMax;

    private final double yMin;

    private final double yMax;
}
