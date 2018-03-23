package com.mashuq.athenaeum.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

@Configuration
public class MongoConfiguration {

	@Value("${mongodb.host}")
	private String mongodbHost;

	@Value("${mongodb.port}")
	private Integer mongodbPort;

	@Value("${mongodb.name}")
	private String mongodbName;

	@Bean
	public MongoClient getMongoClient() {
		return new MongoClient(mongodbHost, mongodbPort);
	}

	@Bean
	public MongoDatabase getMongoDB() {
		return getMongoClient().getDatabase(mongodbName);
	}
}
