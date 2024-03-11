public class TestBitOperationsExercise {
	public static void main(String[] args) {
		int h = 30;
		System.out.println("h = " + Integer.toBinaryString(h));
		int i = h & 7;
		System.out.println("i = " + Integer.toBinaryString(i));
		int j = h >>> 2;
		System.out.println("j = " + Integer.toBinaryString(j));
		int k = h >>> 3;
		System.out.println("k = " + Integer.toBinaryString(k));
		int m = j ^ k;
		System.out.println("m = " + Integer.toBinaryString(m));
		h ^= m;
		System.out.println("h = " + Integer.toBinaryString(h));
	}
}
