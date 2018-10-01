package de.derjonk.home_sweet_home;

import de.derjonk.home_sweet_home.accounting.ExpenseRepository;
import de.derjonk.home_sweet_home.accounting.TransactionRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@SpringBootApplication(scanBasePackageClasses = HomeSweetHomeApplication.class)
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
}
