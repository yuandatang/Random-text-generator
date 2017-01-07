// Name:Yuanda Tang
// USC loginid:yuandata
// CS 455 PA4
// Fall 2016
import java.util.LinkedList;
import java.util.ListIterator;


/*
 * This class represent a sequence of string which is the prefix of the generate text
 */


/**
Representation invariant:
--The linkedList stands for the prefix. And the length of the LinkedList equals to prefix's length
--Integer prefixLength stands for the prefix's length
*/
public class Prefix {
    private LinkedList<String> prefixWord;
    private int prefixLength;
    //private String curPrefix;
    
    /*
     * construct function 
     */
    public Prefix(LinkedList<String> prefixWord){
        prefixLength=prefixWord.size();
        this.prefixWord=new LinkedList<String>(prefixWord);
    }
    
    
    /*
     * return the new prefix(abandon the first element and add the generated word to the end of linkedlist)
     */
    public LinkedList<String> getNewPrefix(String wordGenerate){
    	LinkedList<String> newPrefix=new LinkedList<String>(prefixWord);
    	newPrefix.remove();
    	newPrefix.add(wordGenerate);
    	return newPrefix;
    }
    
    
    /*
     * return the Linkedlist which represent the prefix 
     */
    private LinkedList<String> getPrefix(){
    	return prefixWord;
    }
    
    
    //@override
    /*
     * if the String in the linkedList which represent the prefix is the same,
     * we same these two prefix is the same.
     */
    public boolean equals(Object object){
    	Prefix compare=(Prefix)object;
    	if(prefixWord.size()!=compare.getPrefix().size()){
    		return false;
    	}
    	ListIterator<String> iterPrefix=prefixWord.listIterator();
    	ListIterator<String> iterCompare=compare.getPrefix().listIterator();
    	while(iterPrefix.hasNext()){
    		if(!iterPrefix.next().equals(iterCompare.next())){
    		    return false;	
    		}
    	}
    	return true;
    }
    

    /*
     * output the prefix in string format
     * @see java.lang.Object#toString()
     */
    public String toString(){
    	String result="";
    	ListIterator<String> iter=prefixWord.listIterator();
    	if(iter.hasNext()){
    		result=result+iter.next();
    	}
    	while(iter.hasNext()){
    		result=result+" "+iter.next();
    	}
    	return result;
    }
    
    
    /*
     * the hashCode is based on the the string value in prefix.
     * If two prefix have same string in their prefix, they have 
     * the same hashCode. 
     * @see java.lang.Object#hashCode()
     */
    public int hashCode(){
    	return prefixWord.toString().hashCode();
    }
}
