public class TestBitOperations {
  public static void main(String[] args) {
	System.out.println(55 % 4);
	System.out.println(55 & (4 - 1));
	System.out.println(1 << 4);
	
	int h = 55;
	System.out.println("h = " + h);
	System.out.println("h in binary = " + Integer.toBinaryString(h));
	System.out.println("h >> 4 in binary = " + Integer.toBinaryString(h >> 4));
	System.out.println("h >> 4 = " + (h >> 4));
	System.out.println("16 - 1 in binary = " + Integer.toBinaryString(16 - 1));
	System.out.println("h & (16 - 1) in binary = " + Integer.toBinaryString(h & (16 - 1)));
	System.out.println("h & (16 - 1) = " + (h & (16 - 1)));
	System.out.println("h in binary = " + Integer.toBinaryString(h));
	System.out.println("~h in binary = " + Integer.toBinaryString(~h));
	System.out.println("13 in binary = " + Integer.toBinaryString(13));
	System.out.println("h & 13 in binary = " + Integer.toBinaryString(h & 13));
	System.out.println("h | 13 in binary = " + Integer.toBinaryString(h | 13));
	System.out.println("h ^ 13 in binary = " + Integer.toBinaryString(h ^ 13));
	System.out.println("h ^ 13 = " + (h ^ 13));	
	System.out.println("h >>> 2 in binary = " + Integer.toBinaryString(h >>> 2));
	System.out.println("h >>> 2 = " + (h >>> 2));
	h ^= h >>> 2;
	System.out.println("After h ^= h >>> 2 executed");
	System.out.println("h = " + h);
	System.out.println("h in binary = " + Integer.toBinaryString(h));
	int i = h & (32 - 1);
	System.out.println("After i = h & (32 - 1)");
	System.out.println("i = " + i);
	System.out.println("i in binary = " + Integer.toBinaryString(i));
	
	System.out.println("The following bit operations involve negative numbers and are here to satisfy my curiousity.");
	System.out.println("You will not be tested on how the binary representation of negative numbers work.");
	System.out.println("Look up \"two's complement\" if you are interested:");

	
	System.out.println("-1 in binary = " + Integer.toBinaryString(-1));
	System.out.println("-2 in binary = " + Integer.toBinaryString(-2));
	System.out.println("-55 in binary = " + Integer.toBinaryString(-55));
	System.out.println("-55 >> 4 in binary = " + Integer.toBinaryString(-55 >> 4));
	System.out.println("-55 >>> 4 in binary = " + Integer.toBinaryString(-55 >>> 4));
	
	System.out.println("1 << 31 in binary = " + Integer.toBinaryString(1 << 31));
	System.out.println("1 << 31 = " + (1 << 31));
	System.out.println("1 << 31 << 1 in binary = " + Integer.toBinaryString(1 << 31 << 1));
	System.out.println("1 << 31 << 1 = " + (1 << 31 << 1));
  }
}
