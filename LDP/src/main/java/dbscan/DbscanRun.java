package dbscan;

import org.example.Point;
import org.example.Utils_IO;

import java.io.File;
import java.util.ArrayList;

public class DbscanRun {
    public static void main(String[] args) {
        Utils_IO utils_io = new Utils_IO();
        // 输入输出路径
        String input = "D:\\WorkSpace\\data\\iris-data\\Iris.csv";
        String output = "D:\\WorkSpace\\data\\iris-data\\DbscanResult.csv";
        Utils_IO utilsIO = new Utils_IO();
        ArrayList<Point> points = utilsIO.csvReader(input, ",", 1, 1, 1);
        //Eps=2.5,MinPts=4
//      datasource.readMatrix(new File("/home/orisun/text.normalized.mat"));
//      datasource.readRLabel(new File("/home/orisun/text.rlabel"));
        Dbscan ds = new Dbscan();
        int clunum = ds.dbscan(points);
        utilsIO.csvWrite(output, points, ',');
    }
}
