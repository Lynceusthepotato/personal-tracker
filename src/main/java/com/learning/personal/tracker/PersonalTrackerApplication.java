package com.learning.personal.tracker;

import com.learning.personal.tracker.Filters.AuthFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PersonalTrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(PersonalTrackerApplication.class, args);
	}

	@Bean
	public FilterRegistrationBean<AuthFilter> filterFilterRegistrationBean() {
		FilterRegistrationBean<AuthFilter> registrationBean = new FilterRegistrationBean<>();
		AuthFilter authFilter = new AuthFilter();
		registrationBean.setFilter(authFilter);
		// Put below the url to authorize api
		registrationBean.addUrlPatterns("/api/finance/*");
		registrationBean.addUrlPatterns("/api/todo/*");
		registrationBean.addUrlPatterns("/api/transaction/*");
		return registrationBean;
	}
}
