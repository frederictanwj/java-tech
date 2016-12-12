package hello;

public class LambdaTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		A a = (b,c)->b+c;
		System.out.println(a.add(10, 22));
	}

	interface A {
		public int add(int b, int c);
	}
	
}
