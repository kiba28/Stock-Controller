package com.stock.product.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {


	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.mvcMatcher("/product-service/**").authorizeRequests()
		    .mvcMatchers("/product-service/**").authenticated().and()
		    .mvcMatcher("/category-service/**").authorizeRequests()
		    .mvcMatchers("/category-service/**").authenticated().and()
		    .oauth2ResourceServer()
		    .jwt();

	}

}
