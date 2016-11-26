package hello;

import org.springframework.web.client.RestTemplate;

public class Test {

	public static void main(String[] args) {
		Greeting g = new RestTemplate().getForObject(
				"http://localhost:8080/greeting", Greeting.class);
		System.out.println(g);
	}

}
