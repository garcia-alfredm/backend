package com.revature.SocialNetworkP2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.util.unit.DataSize;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.MultipartConfigElement;

@SpringBootApplication
public class SocialNetworkP2Application {

	public static void main(String[] args) {
		SpringApplication.run(SocialNetworkP2Application.class, args);
	}
	@Bean
	MultipartConfigElement multipartConfigElement() {
		MultipartConfigFactory factory = new MultipartConfigFactory();
		factory.setMaxFileSize(DataSize.ofKilobytes(512));
		factory.setMaxRequestSize(DataSize.ofKilobytes(512));
		return factory.createMultipartConfig();
	}

	@Bean
	WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/files")
						.allowedOrigins("http://localhost:4200");
			}
		};
	}
}
