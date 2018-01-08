package com.myTwitter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.myTwitter.rest.MyTwitterRestImpl;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Configuration
public class MyTwitterBeanConfig {
	@Bean
	public BCryptPasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public MyTwitterRestImpl restServiceImpl() {
		return new MyTwitterRestImpl();
	}
}
