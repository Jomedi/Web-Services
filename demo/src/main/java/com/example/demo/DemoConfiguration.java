package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.model.Liga;

@Configuration
public class DemoConfiguration {

	@Bean
	public Liga liga() {
		return new Liga();
	}
}