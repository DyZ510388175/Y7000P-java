package D_Stream;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class FormatD_StreamReault {

	public void Format(String cellToCluster, String pointToCell,
			String pointToTruth, String point, String output, int time,String outputp)
			throws IOException {
		// String cellToCluster = "";
		// String pointToCell = "";
		// String pointToTruth = "";
		// String point = "";

		Map<Integer, Integer> cellToClusterMap = new HashMap<Integer, Integer>();
		BufferedReader br = new BufferedReader(new FileReader(cellToCluster));
		String line = null;
		for (int i = 0; (line = br.readLine()) != null; i++) {
			StringTokenizer st = new StringTokenizer(line);
			while (st.hasMoreTokens()) {
				cellToClusterMap.put(Integer.parseInt(st.nextToken()), i);
			}
		}
		br.close();

		BufferedReader pointToCellReader = new BufferedReader(new FileReader(
				pointToCell));
		BufferedReader pointReader = new BufferedReader(new FileReader(point));
		BufferedReader pointToTruthReader = new BufferedReader(new FileReader(
				pointToTruth));
		BufferedWriter bw = new BufferedWriter(new FileWriter(output));
		
//		String outputp="C:/Users/Administrator/Desktop/11/test/yDStream/A";
		BufferedWriter bwp = new BufferedWriter(new FileWriter(outputp));

		String pointToCellLine = null;
		String pointLine = null;
		String pointToTruthLine = null;
		for (int i = 0; i <= time; i++) {
			pointToCellLine = pointToCellReader.readLine();//读数据号和网格号txt
			pointLine = pointReader.readLine();///读原数据txt
			pointToTruthLine = pointToTruthReader.readLine();//读标签txt

			int cellId = Integer.parseInt(pointToCellLine.split("\\s+")[1]);
			if (cellToClusterMap.containsKey(cellId)) {
				bw.write(pointLine + " " + pointToTruthLine.split("\\s+")[1]
						+ " " + cellToClusterMap.get(cellId) + "\n");
				bwp.write(pointToTruthLine.split("\\s+")[1]
						+ " " + cellToClusterMap.get(cellId) + "\n");
			}
		}

		pointToCellReader.close();
		pointReader.close();
		pointToTruthReader.close();
		bw.close();
		bwp.close();
	}

	public static void main(String[] args) throws IOException {

//		String dataPath = "C:/Users/Administrator/Desktop/11/test";
		String dataPath = "G:/workspace/eclipse";
//		String dataPath = null;
		int num = 450;
		for (int i = 0; i < args.length; i++) {
			if (args[i].equals("-in")) {
				dataPath = args[++i];
			}
			if (args[i].equals("-num")) {
				num = Integer.parseInt(args[++i]);
			}
		}
		for (int i = 25; i < num; i += 25) {
			String cellToCluster = dataPath + "/7-11/result/yDStream/tmpResult/gridToCluster"
					+ i + ".txt";
			String pointToCell = dataPath + "/7-11/result/yDStream/tmpResult/pointToGrid.txt";
//			KDD99	covtype	PAMAP2
			String pointToTruth = dataPath + "/result/PAMAP2label.txt"; //读入标签
			String point = dataPath + "/result/PAMAP21.txt";  //读入原数据
			String output = dataPath + "/7-11/result/yDStream/tmpResult/result" + i + ".txt";
			String outputp=dataPath+"/7-11/result/yDStream/A/intput"+i+".txt";
			FormatD_StreamReault fr = new FormatD_StreamReault();
			fr.Format(cellToCluster, pointToCell, pointToTruth, point, output,
					i * 1000,outputp);
			System.out.println(i);
		}
	}
}
