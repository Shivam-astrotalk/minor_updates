package com.astrotalk.live;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.io.FileInputStream;
import java.io.IOException;


@EnableAsync
@EnableScheduling
@EnableTransactionManagement
@SpringBootApplication(exclude = {
		org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class, org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration.class}
)
public class AstroYetiApplication {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(AstroYetiApplication.class, args);
	}
}
