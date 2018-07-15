package knn.computable;

/**
 * @author WallfacerRZD
 * @date 2018/7/13 15:33
 */
public class Point implements Computable {
    private final double x;

    private final double y;

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

    @Override
    public double compute(Point point) {
        if (point == null) {
            return Double.POSITIVE_INFINITY;
        }
        double otherX = point.getX();
        double otherY = point.getY();
        return Math.sqrt(
                (this.x - otherX) * (this.x - otherX) + (this.y - otherY) * (this.y - otherY)
        );
    }
}
