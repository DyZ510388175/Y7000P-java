package org.example;

import java.util.ArrayList;

public class Point {

    private int id;
    private int dim;
    private double[] vec;
    private String label;

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
        if (label != null) {
            sb.append(label);
        }
        return sb.toString().split(",");
    }

}
