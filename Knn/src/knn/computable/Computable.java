package knn.computable;

/**
 * @author WallfacerRZD
 * @date 2018/7/14 17:46
 */
public interface Computable {
    /**
     * 计算对象与给定一点的距离
     * QuadTree和Point类实现了这个接口
     * QuadTree计算矩形与给定点的最短距离
     * Point计算与给定点的直线距离
     * @param point 给定点
     * @return  对象与给定点的距离
     */
    double compute(Point point);
}
