package DenStream;

import java.util.ArrayList;
import java.util.List;

public class Cluster {
	
	public int id;
	public List<MicroCluster> list;  //΢��
	public int size; //΢�ش�С
	public double weight; //Ȩ��
	
	public Cluster(int id){
		this.id = id;
		list = new ArrayList<MicroCluster>();
	}
	
	public void add(MicroCluster mc){
		list.add(mc);
		size++;
	}
}
