import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class RandomTextGeneratorTester {

	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
        File file=new File("testFile");
        Scanner in=new Scanner(file);
        ArrayList temp=new ArrayList();
        while(in.hasNext()){
        	temp.add(in.next());
        }
        RandomTextGenerator test=new RandomTextGenerator(2,200,temp);
        ArrayList array=test.generateText();
        output(array);
	}
    
	
	public static void output(ArrayList<ArrayList> array){
		for(int i=0;i<array.size();i++){
			StringBuilder sb=new StringBuilder();
			for(int j=0;j<array.get(i).size();j++){
				sb.append(array.get(i).get(j));
			}
			System.out.println(sb);
		}
	}
}
