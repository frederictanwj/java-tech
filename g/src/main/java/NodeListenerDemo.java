import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.Set;

public class NodeListenerDemo {

	public static void main(String[] args) throws IOException {
		ServerSocket s = new ServerSocket(7676);
		while (true) {
			try (Socket c = s.accept(); OutputStream out = c.getOutputStream(); InputStream in = c.getInputStream();) {
				out.write("Hello, I received: ".getBytes());
				// reply back the request
				byte[] b = new byte[1024];
				for (int i = 0; i != -1; i = in.read(b)) {
					out.write(b);
				}
				
				// reply the local system information
				out.write("My system info: ".getBytes());
				RuntimeMXBean runtimeBean = ManagementFactory.getRuntimeMXBean();
				Map<String, String> systemProperties = runtimeBean.getSystemProperties();
				Set<String> keys = systemProperties.keySet();
				for (String key : keys) {
					String value = systemProperties.get(key);
					out.write(String.format("[%s] = %s.\n", key, value).getBytes());
				}

				out.flush();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
