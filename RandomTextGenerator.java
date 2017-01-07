// Name:Yuanda Tang
// USC loginid:yuandata
// CS 455 PA4
// Fall 2016
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;


/*
 * This class generator and save the random generated text using Prefix Class.
 */
/**
Representation invariant:
--Use ArrayList<ArrayList> to store the output data.
--Use HashMap<Prefix,LinkedList<String>> to store the pairs 
of prefix and possible words which can be generated.
*/
public class RandomTextGenerator {
    private int prefixLength;
    private int numWords;
    private HashMap<Prefix,LinkedList<String>> map;
    private ArrayList<ArrayList> outputData;
    private ArrayList<String> sourceFile;
    private Random random;
    private static final int charPerLine=80;
    private static final int SEED=1;
    
    
    /*
     * construct function
     */
    public RandomTextGenerator(boolean isDebugMode,int prefixLength, int numWords, ArrayList<String> sourceFile){
    	this.prefixLength=prefixLength;
    	this.numWords=numWords;
    	outputData=new ArrayList();
    	if(isDebugMode){
    		random=new Random(SEED);
    	}
    	else{
    		random=new Random();
    	}
    	map=new HashMap<Prefix,LinkedList<String>>();
    	this.sourceFile=new ArrayList<String>(sourceFile);
    	this.initial();
    }
    
    
    /*
     * Initialization. Record the all possible prefix(along with the possible generated words) in hashMap
     */
    private void initial(){
    	LinkedList<String> temp=new LinkedList<String>();
    	for(int i=0;i<prefixLength;i++){
    		temp.add(sourceFile.get(i));
    	}
    	Prefix first=new Prefix(temp);
    	for(int i=prefixLength;i<sourceFile.size();i++){
    		if(!map.containsKey(first)){
    			temp=new LinkedList<String>();
    			temp.add(sourceFile.get(i));
    		    map.put(first,temp);
    		}
    		else{
    			map.get(first).add(sourceFile.get(i));
    		}
    		first=new Prefix(first.getNewPrefix(sourceFile.get(i)));
    	}
    }
    
    /*
     * generate the random text and save it in the data.
     */
    public ArrayList<ArrayList> generateText(boolean isDebugMode){
    	ArrayList temp=new ArrayList<Character>();
    	Prefix prefix=initialPrefix();
    	String next=new String(nextWord(prefix,isDebugMode));
    	if(isDebugMode==true){
    		System.out.println("DEBUG: chose a new initial prefix: "+prefix.toString());
    		System.out.println("DEBUG: prefix: "+prefix.toString());
    		System.out.println("DEBUG: successors "+map.get(prefix));
    		System.out.println("DEBUG: word generated: "+next);
    	}
    	for(int i=0;i<numWords;i++){
    		if(temp.size()+next.length()+1<=charPerLine){
    			for(int j=0;j<next.length();j++){temp.add(next.charAt(j));}
    			temp.add(" ");
    		}
    		else{
    			outputData.add(new ArrayList(temp));
    			temp=new ArrayList();
    			for(int j=0;j<next.length();j++){
    				temp.add(next.charAt(j));
    			}
    			temp.add(" ");
    		}
    		prefix=new Prefix(prefix.getNewPrefix(next));
    		if(isDebugMode==true&&!map.containsKey(prefix)){
    			System.out.println("DEBUG: prefix: "+prefix.toString());
    		}
    		prefix=checkHitEnd(prefix,isDebugMode);
    		next=nextWord(prefix,isDebugMode);
    		if(isDebugMode==true){
    			System.out.println("DEBUG: prefix: "+prefix.toString());
    			System.out.println("DEBUG: successors: "+map.get(prefix));
    			System.out.println("DEBUG: word generated: "+next);
    		}
    	}
    	outputData.add(new ArrayList(temp));
    	return outputData;
    }
    
    
    /*
     *check if the prefix hit the end of the file, if so, reinitialize the prefix  
     *return the prefix
     */
    private Prefix checkHitEnd(Prefix prefix,boolean isDebugMode){
        while(!map.containsKey(prefix)){
			prefix=initialPrefix();
			if(isDebugMode){
				System.out.println("DEBUG: successors: <END OF FILE>");
				System.out.println("DEBUG: chose a new initial prefix: "+prefix.toString());
			}
		}
        return prefix;
    }
    
    
    /*
     * generate random prefix as initial prefix
     */
    private Prefix initialPrefix(){
    	int firstIndex;
    	firstIndex=random.nextInt(sourceFile.size()-prefixLength);
    	LinkedList<String> prefixArray=new LinkedList<String>();
    	for(int i=firstIndex;i<firstIndex+prefixLength;i++){
    		prefixArray.add(sourceFile.get(i));
    	}
    	Prefix initialPre=new Prefix(prefixArray);
    	return initialPre;
    }
    
    
    /*
     * get a random generated word depends on prefix
     */
    private String nextWord(Prefix prefix,boolean isDebugMode){
    	String nextWord;
    	LinkedList<String> possibleValue=map.get(prefix);
    	int index=random.nextInt(possibleValue.size());
    	nextWord=possibleValue.get(index);
    	return nextWord;
    }
}
