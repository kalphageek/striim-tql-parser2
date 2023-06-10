package com.skhynix.datahub.striimtqlparser2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication//(exclude = {DataSourceAutoConfiguration.class})
public class StriimTqlParserApplication {

	public static void main(String[] args) {
		SpringApplication.run(StriimTqlParserApplication.class, args);
	}

}
