package org.example;

import java.util.ArrayList;

public class main {
    public static void main(String[] args) {
//        输入输出路径
        String input = "D:\\WorkSpace\\data\\iris-data\\Iris.csv";
        String output = "D:\\WorkSpace1\\data1\\iris-data1\\Iris2.csv";
        Utils_IO utilsIO = new Utils_IO();
//        读csv数据，数据分割符，1有表头，1有索引，1有标签
        ArrayList<Point> points = (ArrayList<Point>) utilsIO.csvReader(input, ",", 1, 1, 1);
        utilsIO.csvWrite(output, points, ',');
    }
}
