package dbscan;

import org.example.Point;
import org.example.Utils_DistanceCoumpute;

import java.util.ArrayList;
import java.util.Vector;
import java.util.Iterator;

public class Dbscan {


    double Eps = 1;   //区域半径
    int MinPts = 4;   //密度
    private Utils_DistanceCoumpute disC = new Utils_DistanceCoumpute();

    //由于自己到自己的距离是0,所以自己也是自己的neighbor
    public Vector<Point> getNeighbors(Point p, ArrayList<Point> objects) {
        Vector<Point> neighbors = new Vector<Point>();
        Iterator<Point> iter = objects.iterator();
        while (iter.hasNext()) {
            Point q = iter.next();
            double[] arr1 = p.getVec();
            double[] arr2 = q.getVec();
            int len = arr1.length;

            if (disC.getEuclideanDis(arr1, arr2) <= Eps) {      //使用编辑距离
//          if(Global.calEuraDist(arr1, arr2, len)<=Eps){    //使用欧氏距离
//          if(Global.calCityBlockDist(arr1, arr2, len)<=Eps){   //使用街区距离
//          if(Global.calSinDist(arr1, arr2, len)<=Eps){ //使用向量夹角的正弦
                neighbors.add(q);
            }
        }
        return neighbors;
    }

    public int dbscan(ArrayList<Point> objects) {
        int clusterID = 0;
        boolean AllVisited = false;
        while (!AllVisited) {
            Iterator<Point> iter = objects.iterator();
            while (iter.hasNext()) {
                Point p = iter.next();
                if (p.getIsVisited())
                    continue;
                AllVisited = false;
                p.setIsVisited(true);     //设为visited后就已经确定了它是核心点还是边界点
                Vector<Point> neighbors = getNeighbors(p, objects);
                if (neighbors.size() < MinPts) {
                    if (p.getClusterId() <= 0)
                        p.setClusterId(-1);       //cid初始为0,表示未分类；分类后设置为一个正数；设置为-1表示噪声。
                } else {
                    if (p.getClusterId() <= 0) {
                        clusterID++;
                        expandCluster(p, neighbors, clusterID, objects);
                    } else {
                        int iid = p.getClusterId();
                        expandCluster(p, neighbors, iid, objects);
                    }
                }
                AllVisited = true;
            }
        }
        return clusterID;
    }

    private void expandCluster(Point p, Vector<Point> neighbors,
                               int clusterID, ArrayList<Point> objects) {
        p.setClusterId(clusterID);
        Iterator<Point> iter = neighbors.iterator();
        while (iter.hasNext()) {
            Point q = iter.next();
            if (!q.getIsVisited()) {
                q.setIsVisited(true);
                Vector<Point> qneighbors = getNeighbors(q, objects);
                if (qneighbors.size() >= MinPts) {
                    Iterator<Point> it = qneighbors.iterator();
                    while (it.hasNext()) {
                        Point no = it.next();
                        if (no.getClusterId() <= 0)
                            no.setClusterId(clusterID);
                    }
                }
            }
            if (q.getClusterId() <= 0) {       //q不是任何簇的成员
                q.setClusterId(clusterID);
            }
        }
    }

}
