package com.deloitte.codingforfun.quotesapi;

import com.mongodb.MongoClientSettings;
import com.mongodb.ServerAddress;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

import java.util.Arrays;

@SpringBootApplication
@EnableReactiveMongoRepositories
public class QuotesApplication {

	@Value("${mongo-hostname}")
	private String mongoHostname;

	@Bean
	public MongoClient mongoClient() {
		MongoClientSettings settings = MongoClientSettings.builder()
				.applyToConnectionPoolSettings(builder -> builder.minSize(0).maxSize(500))
				.applyToClusterSettings(builder -> builder.hosts(Arrays.asList(new ServerAddress(mongoHostname, 27017)))).build();

		return MongoClients.create(settings);
	}

	public static void main(String[] args) {
		SpringApplication.run(QuotesApplication.class, args);
	}
}
