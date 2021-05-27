package kmeans;

import org.example.Point;
import org.example.Utils_DistanceCoumpute;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Kmeans {
    private int kNum;                             //簇的个数
    private int iterNum = 10;                     //迭代次数

    private int iterMaxTimes = 100000;            //单次迭代最大运行次数
    private int iterRunTimes = 0;                 //单次迭代实际运行次数
    private float disDiff = (float) 0.01;         //单次迭代终止条件，两次运行中类中心的距离差

    private static List<Point> pointList = null;  //用于存放，原始数据集所构建的点集
    private Utils_DistanceCoumpute disC = new Utils_DistanceCoumpute();
    private int len = 0;                          //用于记录每个数据点的维度

    public Kmeans(int k, List<Point> points) {
        this.kNum = k;
        this.pointList = points;
        this.len = pointList.get(0).getVec().length;
        //检查规范
        check();
    }

    /**
     * 检查规范
     */
    private void check() {
        if (kNum == 0) {
            throw new IllegalArgumentException("k must be the number > 0");
        }
        if (pointList == null) {
            throw new IllegalArgumentException("program can't get real data");
        }
    }

    /**
     * 随机选取中心点，构建成中心类。
     */
    private Set<Cluster> chooseCenterCluster() {
        Set<Cluster> clusterSet = new HashSet<Cluster>();
        Random random = new Random();
        for (int id = 0; id < kNum; ) {
            Point point = pointList.get(random.nextInt(pointList.size()));
            // 用于标记是否已经选择过该数据。
            boolean flag = true;
            for (Cluster cluster : clusterSet) {
                if (cluster.getCenter().equals(point)) {
                    flag = false;
                }
            }
            // 如果随机选取的点没有被选中过，则生成一个cluster
            if (flag) {
                Cluster cluster = new Cluster(id, point);
                clusterSet.add(cluster);
                id++;
            }
        }
        return clusterSet;
    }

    /**
     * 为每个点分配一个类！
     */
    public void cluster(Set<Cluster> clusterSet) {
        // 计算每个点到K个中心的距离，并且为每个点标记类别号
        for (Point point : pointList) {
            float min_dis = Integer.MAX_VALUE;
            for (Cluster cluster : clusterSet) {
                float tmp_dis = (float) Math.min(disC.getEuclideanDis(point, cluster.getCenter()), min_dis);
                if (tmp_dis != min_dis) {
                    min_dis = tmp_dis;
                    point.setClusterId(cluster.getId());
                }
            }
        }
        // 新清除原来所有的类中成员。把所有的点，分别加入每个类别
        for (Cluster cluster : clusterSet) {
            cluster.getMembers().clear();
            for (Point point : pointList) {
                if (point.getClusterId() == cluster.getId()) {
                    cluster.addPoint(point);
                }
            }
        }
    }

    /**
     * 计算每个类的中心位置！
     */
    public boolean calculateCenter(Set<Cluster> clusterSet) {
        boolean ifNeedIter = false;
        for (Cluster cluster : clusterSet) {
            List<Point> point_list = cluster.getMembers();
            double[] sumAll = new double[len];
            // 所有点，对应各个维度进行求和
            for (int i = 0; i < len; i++) {
                for (int j = 0; j < point_list.size(); j++) {
                    sumAll[i] += point_list.get(j).getVec()[i];
                }
            }
            // 计算平均值
            for (int i = 0; i < sumAll.length; i++) {
                sumAll[i] = (float) sumAll[i] / point_list.size();
            }
            // 计算两个新、旧中心的距离，如果任意一个类中心移动的距离大于dis_diff则继续迭代。
            if (disC.getEuclideanDis(cluster.getCenter(), sumAll) > disDiff) {
                ifNeedIter = true;
            }
            // 设置新的类中心位置
            cluster.setCenter(new Point(1, sumAll));
        }
        return ifNeedIter;
    }

    /**
     * 运行 k-means
     */
    public Set<Cluster> run() {
        Set<Cluster> clusterSet = chooseCenterCluster();
        boolean ifNeedIter = true;
        while (ifNeedIter) {
            cluster(clusterSet);
            ifNeedIter = calculateCenter(clusterSet);
            iterRunTimes++;
        }
        return clusterSet;
    }

    /**
     * 返回实际运行次数
     */
    public int getIterTimes() {
        return iterRunTimes;
    }
}
