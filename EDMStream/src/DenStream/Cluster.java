package DenStream;

import java.util.ArrayList;
import java.util.List;

public class Cluster {
	
	public int id;
	public List<MicroCluster> list;  //微簇
	public int size; //微簇大小
	public double weight; //权重
	
	public Cluster(int id){
		this.id = id;
		list = new ArrayList<MicroCluster>();
	}
	
	public void add(MicroCluster mc){
		list.add(mc);
		size++;
	}
}
