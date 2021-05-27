package org.example;

import java.util.ArrayList;

public class Point {

    private int id; //数据索引
    private int dim; //数据维度
    private double[] vec; //数据坐标
    private String label; //原始类别
    private int clusterId; //算法处理后类别,初始为-2，异常为-1
    private boolean isVisited; //算法处理后数据是否被访问过,false没有,true访问过

    public int getClusterId() {
        return clusterId;
    }

    public void setClusterId(int clusterId) {
        this.clusterId = clusterId;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getId() {
        return id;
    }

    public int getDim() {
        return dim;
    }

    public double[] getVec() {
        return vec;
    }

    public boolean getIsVisited() {
        return isVisited;
    }

    public void setIsVisited(boolean isVisited) {
        this.isVisited = isVisited;
    }

    /**
     * @param id   索引号
     * @param data 各维数据
     */
    public Point(int id, ArrayList<Double> data) {
        this.id = id;
        this.dim = data.size();
        this.vec = new double[data.size()];
        for (int i = 0; i < data.size(); i++) {
            this.vec[i] = data.get(i);
        }
        this.clusterId = -2;
        this.isVisited = false;
    }

    public Point(int id, ArrayList<Double> data, String label) {
        this.id = id;
        this.dim = data.size();
        this.vec = new double[data.size()];
        for (int i = 0; i < data.size(); i++) {
            this.vec[i] = data.get(i);
        }
        this.label = label;
        this.clusterId = -2;
        this.isVisited = false;
    }

    public Point(int id, ArrayList<Double> data, int clusterId) {
        this.id = id;
        this.dim = data.size();
        this.vec = new double[data.size()];
        for (int i = 0; i < data.size(); i++) {
            this.vec[i] = data.get(i);
        }
        this.clusterId = clusterId;
        this.isVisited = false;
    }

    public Point(int id, double[] data) {
        this.id = id;
        this.dim = data.length;
        this.vec = new double[data.length];
        for (int i = 0; i < data.length; i++) {
            this.vec[i] = data[i];
        }
        this.clusterId = -2;
        this.isVisited = false;
    }

    public double getDisTo(Point p) {
        double dis = 0;
        double temp = 0;
        double[] pvec = p.vec;
        for (int i = 0; i < dim; i++) {
            temp = pvec[i] - vec[i];
            dis += temp * temp;
        }
        return Math.sqrt(dis);
    }

    /**
     * 打印Point到控制台
     */
    public void print() {
        System.out.print(this.id + " ");
        for (double d : this.vec) {
            System.out.print(d + " ");
        }
        if (label != null) {
            System.out.println(this.label);
        }
        if (clusterId != -2) {
            System.out.println(clusterId);
        }
        System.out.println();
    }

    /**
     * point的id,vec[],label转String[]
     *
     * @return
     */
    public String[] toStringarray() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.id);
        sb.append(",");
        for (double d : this.vec) {
            sb.append(d);
            sb.append(",");
        }
        if (this.label != null) {
            sb.append(this.label);
            sb.append(",");
        }
        if (this.clusterId != -2) {
            sb.append(this.clusterId);
        }

        return sb.toString().split(",");
    }

    @Override
    public String toString() {
        String result = "Point_id=" + id + "  [";
        for (int i = 0; i < vec.length; i++) {
            result += vec[i] + " ";
        }
        return result.trim() + "] clusterId: " + clusterId;
    }
}
