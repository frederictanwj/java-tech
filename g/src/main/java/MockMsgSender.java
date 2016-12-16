import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;

public class MockMsgSender {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		try (Socket s = new Socket("127.0.0.1", 7676);
				OutputStream out = s.getOutputStream();
				InputStream in = s.getInputStream()) {
			out.write("Hello, this is my photo: ".getBytes());
//			Files.copy(Paths.get("/Users/lulu/Desktop/mockup.png"), out);
			out.flush();

			byte[] b = new byte[1024];
			for (int i = 0; i != -1; i = in.read(b)) {
				System.out.print(new String(b));
			}
		}
	}

}
