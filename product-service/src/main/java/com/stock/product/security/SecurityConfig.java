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
		http.authorizeRequests()
		.mvcMatchers("/category-service/**").hasAuthority("SCOPE_offline_access")
		.mvcMatchers("/product-service/**").hasAuthority("SCOPE_offline_access")
		.and()
		    .oauth2ResourceServer()
		    .jwt();

	}

}
