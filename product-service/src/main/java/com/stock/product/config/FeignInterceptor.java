package com.stock.product.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import feign.RequestInterceptor;
import feign.RequestTemplate;

@Configuration
public class FeignInterceptor {
	
	@Bean
	public RequestInterceptor getFeignInterceptor() {
		
		return new RequestInterceptor() {
			
			@Override
			public void apply(RequestTemplate template) {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
				System.out.println("token "+auth);
			}
		};
		
	}

}
