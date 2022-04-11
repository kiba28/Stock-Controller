package com.stock.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.mvcMatcher("/stock-service/**")
			.authorizeRequests()
			.mvcMatchers("/stock-service/**").hasAuthority("SCOPE_offline_access").and()
			.oauth2ResourceServer()
			.jwt();
	}
}