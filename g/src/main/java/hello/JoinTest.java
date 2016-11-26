package hello;

public class JoinTest {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		Thread t = new Thread(new Runnable() {
			
			@Override
			public void run() {
				System.out.println("ttt");
			}
		});
		t.start();
		t.join();

		System.out.println("mmm");
	}

}
