package com.skhynix.datahub.striimtqlparser2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class StriimTqlParserApplication {

	public static void main(String[] args) {
		SpringApplication.run(StriimTqlParserApplication.class, args);
	}

}
