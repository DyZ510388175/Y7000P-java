package kmeans;

import org.example.Point;
import org.example.Utils_IO;

import java.util.ArrayList;
import java.util.Set;

public class KmeansRun {

    public static void main(String[] args) {
        //        输入输出路径
        String input = "D:\\WorkSpace\\data\\iris-data\\Iris.csv";
        String output = "D:\\WorkSpace\\data\\iris-data\\KmeansResult.csv";
        ArrayList<Point> result = new ArrayList<>();
        Utils_IO utilsIO = new Utils_IO();
//        读csv数据，数据分割符，1有表头，1有索引，1有标签
        ArrayList<Point> points = utilsIO.csvReader(input, ",", 1, 1, 1);

        Kmeans kRun = new Kmeans(2, points);

        Set<Cluster> clusterSet = kRun.run();
        System.out.println("单次迭代运行次数：" + kRun.getIterTimes());
        int index = 1;
        for (Cluster cluster : clusterSet) {
//            System.out.println(cluster);
            result.addAll(cluster.getMembers());
        }
        utilsIO.csvWrite(output, result, ',');
    }
}
