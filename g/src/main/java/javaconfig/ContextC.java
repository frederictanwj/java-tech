package javaconfig;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ContextC {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		context.register(ConfigA.class);
		context.refresh();
		System.out.println(context.getBean(A1.class));
		System.out.println(context.getBean("A11"));
		System.out.println(context.getBean("A2"));
		System.out.println(context.getBean("A22"));
		System.out.println(context.getBean(B1.class));
		context.close();
	}
}
