package hello;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import restws.Greeting;

@SpringBootApplication(scanBasePackages={"restws","soapws"})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	@Bean
	public CommandLineRunner run(RestTemplate restTemplate) throws Exception {
		return args -> {
			UriComponentsBuilder u = UriComponentsBuilder.fromUriString("http://localhost:8080/greeting");
			u.queryParam("name", "Frederic_Laurier");
			Greeting g = new RestTemplate().getForObject(
					u.toUriString(), Greeting.class);
			System.out.println(g);
		};
	}
}