package com.stock.product.config;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.server.ResponseStatusException;

import com.stock.product.dto.CredentialsDTO;

import feign.RequestInterceptor;
import feign.RequestTemplate;

@Configuration
public class FeignInterceptor {
	
	@Autowired
	private ModelMapper mapper;
	
	@Bean
	public RequestInterceptor getFeignInterceptor() {
		
		return new RequestInterceptor() {
			
			@Override
			public void apply(RequestTemplate template) {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
				if(auth==null) {
					throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
				}
			var credentials = auth.getCredentials();
			CredentialsDTO dto = mapper.map(credentials, CredentialsDTO.class );
			
			template.header("Authorization", "Bearer "+dto.getTokenValue());
			
			}
		};
		
	}

}
