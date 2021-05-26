package DenStream;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.StringTokenizer;

public class DenStreamDriver {

	/*
	 * 参数  initSize = 100; //数据到达速率 1000/s
	 * pointToMC.txt id,mcid
	 */
	public static void main(String[] args) throws IOException {
		double epsilon = 0;
		double beta = 0;
		double mu = 0;
		double lambda = 0;
		double a = 0;
		String input = null; // data file path
		String output = null; // result fold path
		String pointToMC = null;// generated from output
		String MCToCluster = null;// generated from output
		int dim = 0;
		int initSize = 0;

		for (int i = 0; i < args.length; i++) {
			if (args[i].equals("-e")) {
				epsilon = Double.parseDouble(args[++i]);
			}
			if (args[i].equals("-b")) {
				beta = Double.parseDouble(args[++i]);
			}
			if (args[i].equals("-m")) {
				mu = Double.parseDouble(args[++i]);
			}
			if (args[i].equals("-l")) {
				lambda = Double.parseDouble(args[++i]);
			}
			if (args[i].equals("-a")) {
				a = Double.parseDouble(args[++i]);
			}
			if (args[i].equals("-in")) {
				input = args[++i];
			}
			if (args[i].equals("-out")) {
				output = args[++i];
			}
		}
//		KDD99	covtype	PAMAP2
		input = "G:/workspace/eclipse/result/covtype1.txt";
		output = "G:/workspace/eclipse/7-11/result/yDenStream/tmpResult";
		epsilon = 0.7;  //论文中说明，数据集特征
		dim = 54;  //  论文中说明，数据集特征

		beta = 0.6; //minw = beta * mu，权重阈值
//		mu = 1.6667;//minw = beta * mu，权重阈值
		mu = 1.6667;
//		mu=2;
		lambda = 0.00288; //论文中说明，控制衰落一致的
//		lambda = 0.00312;
		a = 2;  //论文中说明，控制衰落一致的
		pointToMC = output + "/pointToMC.txt";//输出为数据id和其分配到微簇id
		MCToCluster = output + "/mcToCluster";//输出微簇的id
		initSize = 1000; // cold start size 每读入1000个数据算一秒
		Util.a = a;
		Util.epsilon = epsilon;
		Util.lambda = lambda;
		System.out.println("epsilon=" + Util.epsilon);

		if (epsilon == 0 || beta == 0 || mu == 0 || lambda == 0 || a == 0
				|| input == null || dim == 0 || initSize == 0) {
			System.err.println("Error parameter!");
			System.exit(0);
		}
		BufferedWriter bw = new BufferedWriter(new FileWriter(pointToMC));
		System.out.println(pointToMC);
		DenStream denstream = new DenStream(epsilon, beta, mu, lambda, a,
				initSize);
		BufferedReader br = new BufferedReader(new FileReader(input));
		String line = null;

		long start = System.currentTimeMillis();
		long time = start;
		long end = 0;
		long time2 = 0;

		int id = 0;
		boolean outInit = false;
		while ((line = br.readLine()) != null) {// while data stream is active
			StringTokenizer st = new StringTokenizer(line);
//			id = Integer.parseInt(st.nextToken());
			st.nextToken();
			double[] vec = new double[dim];
			for (int i = 0; i < dim; i++) {
				vec[i] = Double.parseDouble(st.nextToken());
			}
			time2 = System.currentTimeMillis() - start;
			Point p = new Point(id, time2, vec);
			id++;

			denstream.process(p);

			if (outInit) {
				bw.write(p.id + " " + p.mcid + "\n");
			} else if (denstream.isInitial) {
				System.out.println("+++++++++++++++" + p.id);
				for (Point curp : denstream.initBuffer) {
					bw.write(curp.id + " " + curp.mcid + "\n");
				}
				for (MicroCluster mc : denstream.p_micro_cluster) {
					mc.lt = 0;
					mc.to = 0;
				}
				outInit = true;
			}

//			一个文件中存放多少数据
			if (p.id % 25000 == 0 && p.id != 0) { //这个时间输出聚类结果。
				 // output file , every 1000 points ingest in, create a new file,
                // each line stands for one cluster, each item in the line stands for micro cluster
                // the file will output all the cluster (build by micro cluster)
				BufferedWriter bw2 = new BufferedWriter(new FileWriter(
						MCToCluster + (id / 1000) + ".txt"));
				for (Cluster C : denstream.results) {
					for (MicroCluster mc : C.list) {
						bw2.write(mc.id + " ");
					}
					bw2.write("\n");  //每一行是一个簇
				}
				bw2.close();
				System.out.print(id / 1000 + " ");  //论文中，输出数据长度
				end = System.currentTimeMillis();
				if(end-time > 250000){
					System.out.println("time out");
					br.close();
					bw.close();
					System.exit(0);
				}
				System.out.println((end - time)/25.0); //论文中，输出反映时间
				time = end;
			}
		}
		end = System.currentTimeMillis();
		System.out.println(end - start);  //输出算法结束时间。论文中，不同算法在不同维度上反映总时间对比。
		br.close();
		bw.close();
		System.out.println("DenStream");
	}
}
