package br.com.alura.microservice.zuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ZuulApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZuulApplication.class, args);
	}	
	
	@Bean
	public RouteLocator lojaRouter(RouteLocatorBuilder builder) {
		return builder.routes()
			.route(p -> p
				.path("/fornecedor/**")
				.filters(f -> f.rewritePath("/fornecedor/(?<segment>.*)", "/${segment}"))
				.uri("http://localhost:8081")
			)
			.route(p -> p
				.path("/loja/**")
				.filters(f->f.rewritePath("/loja/(?<segment>.*)", "/${segment}"))
				.uri("http://localhost:8080")
			)
			.build();
	}

}
