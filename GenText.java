//Name:Yuanda Tang
//USC loginid:yuandata
//CS 455 PA4
//Fall 2016
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;


/*
 * This class contains the main method. This class will have a 
 * main that's responsible for processing the command-line 
 * arguments, opening and closing the files, and handling 
 * any errors related to the above tasks. All the other 
 * functionality will be delegated to other object(s) 
 * created in main and their methods.
 */
public class GenText {
	public static void main(String[] args) {
		boolean isDebugMode=false;
	    int prefixLength;
	    int numWords;
	    String inputFile;
	    String outputFile;
	    try{
	    	if(args[0].equals("-d")){
	    		isDebugMode=true;
	    		prefixLength=Integer.parseInt(args[1]);
	    		numWords=Integer.parseInt(args[2]);
	    		inputFile=args[3];
	    		outputFile=args[4];
	    		checkValidity(isDebugMode,prefixLength,numWords,inputFile,outputFile);
	    	}
	    	else{
	    		prefixLength=Integer.parseInt(args[0]);
	    		numWords=Integer.parseInt(args[1]);
	    		inputFile=args[2];
	    		outputFile=args[3];
	    		checkValidity(isDebugMode,prefixLength,numWords,inputFile,outputFile);
	    	}
	    }
	    catch(NumberFormatException exc){
	    	System.out.println("prefixLength or numWords arguments are not integers");
	    	System.out.println("Please input prefixLength and numWords as integers");
	    	System.out.println("command-line format: [-d] prefixLength numWords sourceFile outFile");
	    	System.exit(1);
		}
	    catch(ArrayIndexOutOfBoundsException exc){
	    	System.out.println("missing command-line arguments");
	    	System.out.println("Please input command-line arguments");
	    	System.out.println("command-line format: [-d] prefixLength numWords sourceFile outFile");
	    	System.exit(1);
	    }
	}
	
	
	/*
	 * check if the input arguments are valid
	 */
	public static void checkValidity(boolean isDebugMode,int prefixLength,int numWords,String inputFile,String outputFile){
		if(prefixLength<1){
			System.out.println("prefixLength<1");
			System.out.println("Please input a prefix with length equal or larger than 1");
			System.out.println("command-line format: [-d] prefixLength numWords sourceFile outFile");
			System.exit(1);
		}
		if(numWords<0){
			System.out.println("numWords<0");
			System.out.println("Please input a numWords with length equal or larger than 0");
			System.out.println("command-line format: [-d] prefixLength numWords sourceFile outFile");
			System.exit(1);
		}
		try{
			File file=new File(inputFile);
			Scanner in=new Scanner(file);
			callRTG(isDebugMode,prefixLength,numWords,inputFile,outputFile);
		}
		catch(FileNotFoundException exc){
			System.out.println("Input file does not exist");
			System.exit(1);
		}
	}
	
	
	/*
	 * if the input is valid, get ready to generate the random text. Call the TandomTextGenerator method.
	 */
	public static void callRTG(Boolean isDebugMode,int prefixLength,int numWords,String inputFile,String outputFile)throws FileNotFoundException{
		ArrayList<String> array=new ArrayList();
		int sourceFileWords=0;
		File file=new File(inputFile);
		Scanner in=new Scanner(file);
		while(in.hasNext()){
			array.add(in.next());
			sourceFileWords++;
		}
		if(prefixLength>=sourceFileWords){
			System.out.println("PrefixLength is equal or larger than number of words in sourceFile");
			System.exit(1);
		}
		RandomTextGenerator textGenerator=new RandomTextGenerator(isDebugMode,prefixLength,numWords,array);
		ArrayList<ArrayList> output=textGenerator.generateText(isDebugMode);
		File writeFile=new File(outputFile);
		try{writeToFile(writeFile,output);
		}
		catch(IOException exc){
			System.out.println("Cannot create the output file");
			System.exit(1);
		}
	}
	
	
	/*
	 * write the random generated word to the file.
	 */
	public static void writeToFile(File writeFile,ArrayList<ArrayList> output) throws IOException {
		writeFile.createNewFile();
		if(!writeFile.canWrite()){
			System.out.println("can't write to output file");
			System.exit(1);
		}
		PrintWriter writer=new PrintWriter(writeFile,"UTF-8");
		for(int i=0;i<output.size();i++){
			StringBuilder sb=new StringBuilder();
			for(int j=0;j<output.get(i).size();j++){
				sb.append(output.get(i).get(j));
			}
			writer.println(sb.toString());
		}
		writer.close();
	}
}
