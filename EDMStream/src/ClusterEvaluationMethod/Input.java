package ClusterEvaluationMethod;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

public class Input {

	public static Vector<Integer>[]Format(String intput,int time) throws IOException{
		Vector<Integer>[] result=new Vector[2];
		for(int i=0;i<2;i++){
			result[i]=new Vector<Integer>();
		}
//		Vector<Integer> A=new Vector<>();
//		result=null;
//		int dim=34+1+2;
		BufferedReader intputt = new BufferedReader(new FileReader(
				intput));
//		BufferedWriter bwA = new BufferedWriter(new FileWriter(outputA));
//		BufferedWriter bwB = new BufferedWriter(new FileWriter(outputB));
		String intputS = null;
		
//		for (int i = 0; i <= time; i++){
//			intputS = intputt.readLine();
			while((intputS = intputt.readLine())!=null){
			int AId = Integer.parseInt(intputS.split("\\s+")[0]);
			int BId = Integer.parseInt(intputS.split("\\s+")[1]);
//			if(AId!=null){
//				result[1].add(AId);
//			}
			result[0].add(AId);
			result[1].add(BId);
//			bwA.write(AId+"\n");
//			bwB.write(AId+"\n");
//			int count=0;
//			count++;
//			System.out.println(count);
		}
		intputt.close();
//		bwA.close();
//		bwB.close();
		return result;
	}
//	return result;
}
