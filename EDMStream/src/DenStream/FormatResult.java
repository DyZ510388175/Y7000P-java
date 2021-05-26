package DenStream;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class FormatResult {
    /*
     * @Format���� ΢��id�ļ�������id������䵽��΢��id�ļ���label�ļ���ԭ�����ļ���������result�ļ���ʱ�䡣
     * @result ԭ���ݣ�label��,�غ�
     */
	public static void Format(String MCToCluster, String pointToMC,
			String pointToTruth, String point, String output, int time,String outputp)
			throws IOException {

		Map<Integer, Integer> cellToClusterMap = new HashMap<Integer, Integer>();
		BufferedReader br = new BufferedReader(new FileReader(MCToCluster));
		String line = null;
		for (int i = 0; (line = br.readLine()) != null; i++) {
			StringTokenizer st = new StringTokenizer(line);
			while (st.hasMoreTokens()) {
//				΢�غź��к�ӳ��(ÿһ����һ����)
				cellToClusterMap.put(Integer.parseInt(st.nextToken()), i);
			}
		}
		br.close();

		BufferedReader pointToMCReader = new BufferedReader(new FileReader(
				pointToMC));
		BufferedReader pointReader = new BufferedReader(new FileReader(point));
		BufferedReader pointToTruthReader = new BufferedReader(new FileReader(
				pointToTruth));
		BufferedWriter bw = new BufferedWriter(new FileWriter(output));
		BufferedWriter bwp = new BufferedWriter(new FileWriter(outputp));

		String pointToMCLine = null;
		String pointLine = null;
		String pointToTruthLine = null;
		for (int i = 0; i <= time; i++) {
			pointToMCLine = pointToMCReader.readLine(); //�����ݺź�΢�غ�txt
			pointLine = pointReader.readLine();//��ԭ����txt
			pointToTruthLine = pointToTruthReader.readLine();//����ǩtxt

			int cellId = Integer.parseInt(pointToMCLine.split("\\s+")[1]);//΢�غ�
			if (cellToClusterMap.containsKey(cellId)) {
				bw.write(pointLine + " " + pointToTruthLine.split("\\s+")[1]//��ǩ��
						+ " " + cellToClusterMap.get(cellId) + "\n"); //�غ�
//				Դ���ݣ���ǩ���غţ�
				bwp.write(pointToTruthLine.split("\\s+")[1]
						+ " " + cellToClusterMap.get(cellId) + "\n");
			}
		}

		pointToMCReader.close();
		pointReader.close();
		pointToTruthReader.close();
		bw.close();
		bwp.close();
	}
	
	/*
	 * num =20 ;  //�ļ����
	 */
	public static void main(String[] args) throws IOException {

		String streamResultPath = null; // a fold path
		String dataPath = null;
		String labelPath = null;
		int num = 0;

		String pointToMCPath = null;// get by streamResultPath
		String MCToClusterPath = null; // get by streamResultPath
		String output = null; // get by streamResultPath
//		KDD99	covtype	PAMAP2
		streamResultPath = "G:/workspace/eclipse/7-11/result/yDenStream/tmpResult";
		dataPath = "G:/workspace/eclipse/result/covtype1.txt";
		labelPath = "G:/workspace/eclipse/result/covtypelabel.txt";
		String outputp="G:/workspace/eclipse/7-11/result/yDenStream/A/intput";
		num =600 ;  //�ļ����

		for (int i = 0; i < args.length; i++) {
			if (args[i].equals("-rpath")) {
				streamResultPath = args[++i];
			}
			if (args[i].equals("-data")) {
				dataPath = args[++i];
			}
			if (args[i].equals("-lpath")) {
				labelPath = args[++i];
			}
			if (args[i].equals("-num")) {
				num = Integer.parseInt(args[++i]);
			}
		}

		if (streamResultPath == null || dataPath == null || labelPath == null
				|| num == 0) {
			System.err.println("invalid parameter while formatResult!!");
			System.exit(0);
		}

		pointToMCPath = streamResultPath + "/pointToMC.txt";
		MCToClusterPath = streamResultPath + "/mcToCluster";
		output = streamResultPath + "/result";

		for (int i = 25; i < num; i += 25) {
			System.out.println(i);
			Format(MCToClusterPath + i + ".txt", pointToMCPath, labelPath,
					dataPath, output + i + ".txt", i*1000,outputp+i+".txt");
		}
	}

}
