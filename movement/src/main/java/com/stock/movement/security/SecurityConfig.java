package com.stock.movement.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.mvcMatcher("/movement-service/**")
			.authorizeRequests()
			.mvcMatchers("/movement-service/**").access("hasAuthority('offline_access')").and()
			.oauth2ResourceServer()
			.jwt();
//			      .jwtAuthenticationConverter(getJwtAuthenticationConverter());
	}
//	
//	JwtAuthenticationConverter getJwtAuthenticationConverter() {
//		
//		JwtGrantedAuthoritiesConverter converter = new JwtGrantedAuthoritiesConverter();
//		converter.setAuthoritiesClaimName("authorities");
//		converter.setAuthorityPrefix("");
//		JwtAuthenticationConverter authenticationConverter = new JwtAuthenticationConverter();
//		authenticationConverter.setJwtGrantedAuthoritiesConverter(converter);
//		return authenticationConverter;
//		
//	}

}
