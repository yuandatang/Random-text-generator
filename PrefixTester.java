import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class PrefixTester {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
    LinkedList one=new LinkedList();
    LinkedList two=new LinkedList();
    one.add("tyd");
    one.add("zxc");
    two.add("tyd");
    two.add("zxc");
    Prefix first=new Prefix(one);
    HashMap map=new HashMap<Prefix,Integer>();
    map.put(first, 1);
    Prefix second=new Prefix(one);
    System.out.println(second.toString());
    System.out.println(map.containsKey(second));
    System.out.println(first.hashCode());
    System.out.println(second.hashCode());
	}

}
