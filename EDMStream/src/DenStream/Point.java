package DenStream;

import java.io.BufferedWriter;
import java.io.IOException;

public class Point {

	public int id;
	public long startTime;  //开始时间
	public int dim;
	public double[] vec; //数据坐标
	public int mcid; //微簇id

	public double maxDis;
	public double minDis;
	public boolean visited;

	// public Point(long st, int dim, float[] vec){
	// this.startTime = st;
	// this.dim = dim;
	// this.vec = vec;
	// }

	public Point(int id, long startTime, double[] vec) {
		this.id = id;
		this.startTime = startTime;
		this.dim = vec.length;
		this.vec = vec;
		visited = false;
		mcid = -1;
	}
    
//	到p的距离
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

//	写 数据坐标
	public void print(BufferedWriter bw) {

		try {
			for (double var : vec) {
				bw.write(var + " ");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
