package com.yearup.demospring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootApplication
@EnableScheduling
public class DemoSpringApplication {

	private static final Logger log = LoggerFactory.getLogger(DemoSpringApplication.class);

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HHH:mm:ss");

	public static void main(String[] args) {
		SpringApplication.run(DemoSpringApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder){
		return builder.build();
	}

	@Bean
	public CommandLineRunner run(RestTemplate restTemplate) throws Exception {
		return args -> {
			reportCurrentTime();
		};
	}
	@Scheduled(fixedRate = 2000)
			public void reportCurrentTime(){
				RestTemplate restTemplate = new RestTemplate();
				Quote quote = restTemplate.getForObject(
						"https://gturnquist-quoters.cfapps.io/api/random", Quote.class);
				log.info("The time is now {}", dateFormat.format(new Date()));
				log.info(quote.toString());
	}
}
