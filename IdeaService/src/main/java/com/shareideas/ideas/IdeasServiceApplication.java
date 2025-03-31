package com.shareideas.ideas;

import java.nio.charset.StandardCharsets;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class IdeasServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(IdeasServiceApplication.class, args);
	}
	
	@Bean
    public WebServerFactoryCustomizer<TomcatServletWebServerFactory> tomcatCustomizer() {
        return factory -> {
            factory.setUriEncoding(StandardCharsets.UTF_8);
        };
    }

}
