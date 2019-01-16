package br.com.comex.v1.thiago.contabil;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@EnableAutoConfiguration
//@EnableJpaRepositories(basePackages = {"br.com.comex.v1.thiago.contabil.repository"})
//@EntityScan(basePackages = {"br.com.comex.v1.thiago.contabil.model"})
public class ContabilApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(ContabilApplication.class, args);
	}
}

