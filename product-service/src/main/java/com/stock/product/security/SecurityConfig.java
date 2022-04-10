package com.stock.product.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private String jwkSetUri = "http://localhost:8080/realms/ourrealm/protocol/openid-connect/certs";

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.mvcMatcher("/product-service/**").authorizeRequests()
		    .mvcMatchers("/product-service/**").authenticated().and()
		    .mvcMatcher("/category-service/**").authorizeRequests()
		    .mvcMatchers("/category-service/**").authenticated().and()
		    .oauth2ResourceServer()
		    .jwt();

	}

	@Bean
	public JwtDecoder jwtDecoder() {
		return NimbusJwtDecoder.withJwkSetUri(jwkSetUri).build();
	}

}
