package de.derjonk.home_sweet_home;

import de.derjonk.home_sweet_home.accounting.ExpenseRepository;
import de.derjonk.home_sweet_home.accounting.TransactionRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;


@SpringBootApplication(scanBasePackageClasses = HomeSweetHomeApplication.class)
@EnableSwagger2WebMvc
@Import(springfox.documentation.spring.data.rest.configuration.SpringDataRestConfiguration.class)
public class HomeSweetHomeApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomeSweetHomeApplication.class, args);
	}

	@Configuration
	public static class HomeSweetHomeApplicationConfiguration {
		@Bean
		public OnIncomeCreatedEventListener onIncomeCreatedEventListener(ExpenseRepository expenseRepository, TransactionRepository transactionRepository) {
			return new OnIncomeCreatedEventListener(expenseRepository, transactionRepository);
		}
	}

	@Configuration
	public static class SwaggerConfig {
		@Bean
		public Docket api() {
			return new Docket(DocumentationType.SWAGGER_2)
					.select()
					.apis(RequestHandlerSelectors.any())
					.paths(PathSelectors.any())
					.build();
		}
	}
}
