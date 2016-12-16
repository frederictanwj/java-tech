import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.Map;
import java.util.Set;

public class GetSystemInfo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		RuntimeMXBean runtimeBean = ManagementFactory.getRuntimeMXBean();
		Map<String, String> systemProperties = runtimeBean.getSystemProperties();
		Set<String> keys = systemProperties.keySet();
		for (String key : keys) {
			String value = systemProperties.get(key);
			System.out.print((String.format("[%s] = %s.\n", key, value)));
		}
	}

}
