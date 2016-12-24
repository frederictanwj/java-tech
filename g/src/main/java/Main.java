import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		String s;
		// construct employees tree
		List<Employee> employees = null;
		String[] s0 = null;
		while ((s = in.readLine()) != null) {
			s0 = s.split(",");
			employees = new ArrayList<>(s0.length - 2);
			for (int i = 0; i < s0.length - 2; i++) {
				String[] s1 = s0[i].split("->");

				Employee up = null;
				Employee down = null;
				for (Employee e : employees) {
					if (up == null && e.name.equals(s1[0])) {
						up = e;
					}
					if (down == null && e.name.equals(s1[1])) {
						down = e;
					}
					if (up != null && down != null) {
						break;
					}
				}
				if (up == null) {
					up = new Employee();
					up.name = s1[0];
					employees.add(up);
				}
				if (down == null) {
					down = new Employee();
					down.name = s1[1];
					employees.add(down);
				}
				up.downRefs.add(down);
				down.upRef = up;
			}
			break;
		}

		// find conflict employees
		Employee c0 = null, c1 = null;
		for (Employee e : employees) {
			if (c0 == null && e.name.equals(s0[s0.length-2])) {
				c0 = e;
			}
			if (c1 == null && e.name.equals(s0[s0.length-1])) {
				c1 = e;
			}
			if (c0 != null && c1 != null) {
				break;
			}
		}

		// construct hierarchy from c0 to top
		List<Employee> h0 = new ArrayList<>();
		for (Employee c = c0; c != null; c = c.upRef) {
			h0.add(c);
		}
		// construct hierarchy from c1 to top
		List<Employee> h1 = new ArrayList<>();
		for (Employee c = c1; c != null; c = c.upRef) {
			h1.add(c);
		}

		// find lowest common manager
		boolean b = false;
		for (Employee c00 : h0) {
			for (Employee c11 : h1) {
				if (c00.name.equals(c11.name)) {
					System.out.print(c00.name);
					b = true;
					break;
				}
			}
			if (b) {
				break;
			}
		}
	}

	static class Employee {
		public Employee upRef;
		public String name;
		public List<Employee> downRefs = new ArrayList<>();
	}
}
