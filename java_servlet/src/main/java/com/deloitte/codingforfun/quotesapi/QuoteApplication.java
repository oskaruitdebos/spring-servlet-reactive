package com.deloitte.codingforfun.quotesapi;

import com.mongodb.MongoClientSettings;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.Arrays;

@SpringBootApplication
public class QuoteApplication {

	@Value("${mongo-hostname}")
	private String mongoHostname;

	@Bean
	public MongoTemplate mongoTemplate() {
		MongoClientSettings settings = MongoClientSettings.builder()
				.applyToConnectionPoolSettings(builder -> builder.minSize(0).maxSize(500))
				.applyToClusterSettings(builder -> builder.hosts(Arrays.asList(new ServerAddress(mongoHostname, 27017)))).build();

		MongoClient client = MongoClients.create(settings);
		return new MongoTemplate(client, "quotes");
	}

	public static void main(String[] args) {
		SpringApplication.run(QuoteApplication.class, args);
	}
}
