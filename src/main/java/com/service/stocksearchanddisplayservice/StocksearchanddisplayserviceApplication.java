package com.service.stocksearchanddisplayservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
@EnableScheduling
public class StockSearchAndDisplayServiceApplication 
{
	public static void main(String[] args) 
	{
		SpringApplication.run(StockSearchAndDisplayServiceApplication.class, args);
	}
}
