package de.derjonk.home_sweet_home;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackageClasses = HomeSweetHomeApplication.class)
public class HomeSweetHomeApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomeSweetHomeApplication.class, args);
	}
}
