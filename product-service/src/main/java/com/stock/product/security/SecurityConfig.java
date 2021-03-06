package com.stock.product.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {


	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.mvcMatchers("/category-service/**").hasAuthority("func")
		.mvcMatchers("/product-service/**").hasAuthority("func")
		.mvcMatchers(HttpMethod.DELETE, "/product-service/**").hasAuthority("admin")
		.mvcMatchers(HttpMethod.GET, "/product-service/**").hasAuthority("user")
		.and()
		    .oauth2ResourceServer()
		    .jwt()
	      .jwtAuthenticationConverter(getJwtAuthenticationConverter());
}

JwtAuthenticationConverter getJwtAuthenticationConverter() {

JwtGrantedAuthoritiesConverter converter = new JwtGrantedAuthoritiesConverter();
converter.setAuthoritiesClaimName("authorities");
converter.setAuthorityPrefix("");
JwtAuthenticationConverter authenticationConverter = new JwtAuthenticationConverter();
authenticationConverter.setJwtGrantedAuthoritiesConverter(converter);
return authenticationConverter;

}

}
