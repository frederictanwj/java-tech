package javaconfig;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Scope;

@Configuration
@Import(ConfigB.class)
public class ConfigA {

	@Bean("A11")
	@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	public A1 getA1() {
		return new A1();
	}
	
	@Bean("A2")
	public A2 getA2(A1 a1) {
		System.out.println("getA2: "+a1);
		return new A2(a1);
	}
	
	@Bean("A22")
	public A2 getA22(B1 b1) {
		System.out.println("getA22: "+b1);
		return new A2(b1);
	}
}
