public class TestBSTWithIterator2 {
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

    java.util.Iterator<String> iter = tree.iterator();
    iter.next();
    iter.remove();
    iter.next();
    iter.remove();

    for (String s: tree)
      System.out.print(s.toUpperCase() + " ");
    System.out.println();

    System.out.println();
    tree.forEach(e -> System.out.print(e.toLowerCase() + " "));

    System.out.println("\nAverage number of characters in each name: "
      + tree.stream().mapToInt(e -> e.length())
            .average().getAsDouble());
  }
}
