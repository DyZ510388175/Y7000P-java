package org.example;

import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;

public class Utils_IO {

    //    存放表头
    public String[] head;

    /**
     * @param inputpath 数据路径
     * @param regular   数据分割符号
     * @param haveHead  是否有表头
     * @param haveIndex 是否有索引
     * @param haveLabel 是否有标签
     * @return 返回数组列表
     */
    public ArrayList<Point> csvReader(String inputpath, String regular, int haveHead, int haveIndex, int haveLabel) {
        String filePath = inputpath;
        ArrayList<Point> points = new ArrayList<>();
        int index = -1;
        try {
            // 创建CSV读对象
            CsvReader csvReader = new CsvReader(filePath);

            if (haveHead == 1) {
                // 读表头
                csvReader.readHeaders();
                head = csvReader.getHeaders();
            }
            while (csvReader.readRecord()) {
                // 读一整行,根据分割符切割字符串
                String[] strs = csvReader.getRawRecord().split(regular);
                // 将切割出来的数据放入数组列表中
                ArrayList<Double> data = new ArrayList<>();
                Point point;
                if (haveIndex == 0 && haveLabel == 0) {
                    for (int i = 0; i < strs.length; i++) {
                        data.add(Double.valueOf(strs[i]));
                    }
                    point = new Point(++index, data);
                } else if (haveIndex == 1 && haveLabel == 0) {
                    for (int i = 1; i < strs.length; i++) {
                        data.add(Double.valueOf(strs[i]));
                    }
                    point = new Point(Integer.valueOf(strs[0]), data);
                } else if (haveIndex == 0 && haveLabel == 1) {
                    for (int i = 0; i < strs.length - 1; i++) {
                        data.add(Double.valueOf(strs[i]));
                    }
                    point = new Point(++index, data);
                } else {
                    for (int i = 1; i < strs.length - 1; i++) {
                        data.add(Double.valueOf(strs[i]));
                    }
                    point = new Point(Integer.valueOf(strs[0]), data);
                    point.setLabel(strs[strs.length - 1]);
                }

                points.add(point);
            }
            csvReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return points;
    }

    public void csvWrite(String outputpath, ArrayList<Point> points, char regular) {

        String filepath = outputpath;
        int index = Math.max(filepath.lastIndexOf("\\"), filepath.lastIndexOf("/"));
        String path = filepath.substring(0, index);
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }

        try {
            // 创建CSV写对象
            CsvWriter csvWriter = new CsvWriter(filepath, regular, Charset.forName("GBK"));
            //CsvWriter csvWriter = new CsvWriter(filePath);

            // 写表头
//            String[] headers = {"编号", "姓名", "年龄"};
            if (head != null) {
                csvWriter.writeRecord(head);
            }
            Iterator<Point> it = points.iterator();
            while (it.hasNext()) {
                Point point = it.next();
                csvWriter.writeRecord(point.toStringarray());
            }
            csvWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
