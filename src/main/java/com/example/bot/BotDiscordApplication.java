package com.example.bot;

import com.example.bot.configuration.CustomBeanNameGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BotDiscordApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(BotDiscordApplication.class);
		app.setBeanNameGenerator(new CustomBeanNameGenerator());
		app.run(args);
	}

}
