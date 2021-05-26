package org.example;

import java.util.ArrayList;

public class main {
    public static void main(String[] args) {
        String input = "D:\\WorkSpace\\data\\iris-data\\Iris.csv";
        String output = "D:\\WorkSpace1\\data1\\iris-data1\\Iris2.csv";
        Utils_IO utilsIO = new Utils_IO();
        ArrayList<Point> points = utilsIO.csvReader(input, ",", 1, 1, 1);
        utilsIO.csvWrite(output, points, ',');
    }
}
