package javaconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigB {

	@Bean(name="B11", initMethod="init", destroyMethod="destroy")
	public B1 getB1() {
		return new B1();
	}
}
