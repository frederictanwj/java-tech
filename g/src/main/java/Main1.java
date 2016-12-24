import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Main1 {
	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		String s;
		Map<String, String> m = new HashMap<>();
		while ((s = in.readLine()) != null) {
			for (String s1 : s.split("|")) {
				List<Character> c = new ArrayList<>();
				for (char c1 : s1.toCharArray()) {
					if (Character.isAlphabetic(c1)) {
						c.add(c1);
					}
				}
				char[] cc = new char[c.size()];
				for (int i=0;i<c.size();i++) {
					cc[i] = c.get(i);
				}
				m.put(s1, String.copyValueOf(cc).toLowerCase().trim().replace("\\s+", " "));
			}
		}

		Map<String, String> f = new HashMap<>();
		for (Entry<String, String> e1 : m.entrySet()) {
			boolean b = false;
			for (Entry<String, String> e2 : m.entrySet()) {
				if (e1.getValue().contains(e2.getValue())) {
					f.put(e1.getKey(), e1.getValue());
					b = true;
					break;
				}
				if (e2.getValue().contains(e1.getValue())) {
					f.put(e2.getKey(), e2.getValue());
					b = true;
					break;
				}
			}
			if (b) {
				continue;
			}
			f.put(e1.getKey(), e1.getValue());
		}
		StringBuffer sb = new StringBuffer();
		for (String k : f.keySet()) {
			if (sb.length()==0) {
				sb.append(k);
			}
			else {
				sb.append('|').append(k);
			}
		}
		System.out.print(sb);
	}
}
