package de.derjonk.home_sweet_home;

import de.derjonk.home_sweet_home.accounting.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

import javax.annotation.PostConstruct;
import java.util.Arrays;


@SpringBootApplication(scanBasePackageClasses = HomeSweetHomeApplication.class)
@EnableSwagger2WebMvc
@Import(springfox.documentation.spring.data.rest.configuration.SpringDataRestConfiguration.class)
public class HomeSweetHomeApplication {

	private final AccountRepository accountRepository;
	private final ExpenseRepository expenseRepository;

	@Autowired
	public HomeSweetHomeApplication(AccountRepository accountRepository, ExpenseRepository expenseRepository) {
		this.accountRepository = accountRepository;
		this.expenseRepository = expenseRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(HomeSweetHomeApplication.class, args);
	}

	@PostConstruct
	public void createSomeSampleData() {
		{
			Account account = accountRepository.save(Account.withName("Meiers Mietkonto"));
			expenseRepository.saveAll(Arrays.asList(
					Expense.forAccount(account).withAmount(750).withTitle("Miete Oktober").end(),
					Expense.forAccount(account).withAmount(250).withTitle("Nebenkosten Oktober").end(),
					Expense.forAccount(account).withAmount(750).withTitle("Miete November").end(),
					Expense.forAccount(account).withAmount(250).withTitle("Nebenkosten November").end()
			));
		}

		{
			Account account = accountRepository.save(Account.withName("Müllers Mietkonto"));
			expenseRepository.saveAll(Arrays.asList(
					Expense.forAccount(account).withAmount(500).withTitle("Miete Oktober").end(),
					Expense.forAccount(account).withAmount(150).withTitle("Nebenkosten Oktober").end(),
					Expense.forAccount(account).withAmount(500).withTitle("Miete November").end(),
					Expense.forAccount(account).withAmount(150).withTitle("Nebenkosten November").end()
			));
		}
	}

	@Configuration
	public static class HomeSweetHomeApplicationConfiguration {
		@Bean
		public OnIncomeCreatedEventListener onIncomeCreatedEventListener(ExpenseRepository expenseRepository, TransactionRepository transactionRepository) {
			return new OnIncomeCreatedEventListener(expenseRepository, transactionRepository);
		}

		@Bean
        public SpringDataRestCustomization springDataRestCustomization() {
		    return new SpringDataRestCustomization();
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

	@EnableWebSecurity
	public class SecurityConfig extends WebSecurityConfigurerAdapter {

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.authorizeRequests().anyRequest().fullyAuthenticated();
			http.httpBasic();
			http.cors();
			http.csrf().disable();
		}

        @Bean
        public CorsConfigurationSource corsConfigurationSource() {
            final CorsConfiguration configuration = new CorsConfiguration();
            configuration.setAllowedOrigins(Arrays.asList("*"));
            configuration.setAllowedMethods(Arrays.asList("HEAD",
                    "GET", "POST", "PUT", "DELETE", "PATCH"));
            // setAllowCredentials(true) is important, otherwise:
            // The value of the 'Access-Control-Allow-Origin' header in the response must not be the wildcard '*' when the request's credentials mode is 'include'.
            configuration.setAllowCredentials(true);
            // setAllowedHeaders is important! Without it, OPTIONS preflight request
            // will fail with 403 Invalid CORS request
            configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));
            final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
            source.registerCorsConfiguration("/**", configuration);
            return source;
        }
	}
}
