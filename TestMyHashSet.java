public class TestMyHashSet {
  public static void main(String[] args) {
    // Create a MyHashSet
    java.util.Collection<String> set = new MyHashSet<>();
    set.add("Smith");
    set.add("Anderson");
    set.add("Lewis");
    set.add("Cook");
    set.add("Smith");

    System.out.println("Elements in set: " + set);
    System.out.println("Number of elements in set: " + set.size());
    System.out.println("Is Smith in set? " + set.contains("Smith"));

    set.remove("Smith");
    System.out.print("Names in set in uppercase are ");
    for (String s: set)
      System.out.print(s.toUpperCase() + " ");

    System.out.println();
    set.forEach(e -> System.out.print(e.toLowerCase()));

    System.out.println("\nThe average length of the names: " +
      set.stream().mapToInt(e -> e.length()).average().getAsDouble());

    set.clear();
    System.out.println("Elements in set: " + set);
    
    System.out.println("Hello".hashCode());
  }
}
