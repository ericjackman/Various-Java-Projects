import java.util.Iterator;

public class TestBSTWithIterator {
  public static void main(String[] args) {
    BST<String> tree = new BST<>();
    tree.insert("George");
    tree.insert("Michael");
    tree.insert("Tom");
    tree.insert("Adam");
    tree.insert("Jones");
    tree.insert("Peter");
    tree.insert("Daniel");

    for (String s: tree)
        System.out.print(s.toUpperCase() + " ");
    System.out.println();
    for (Iterator<String> iter = tree.iterator(); iter.hasNext(); ) {
    	String s = iter.next();
    	System.out.print(s.toUpperCase() + " ");
    }
    System.out.println();
  }
}
