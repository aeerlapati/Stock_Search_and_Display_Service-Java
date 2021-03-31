package com.service.stocksearchanddisplayservice.util;

import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UtilityTest {
	
	@Test
	public void buildErrorResponseTest()
	{
		Utility.buildErrorResponse("Error", "-1", "Error Details", "Stock Service", "Stock Service Details");
		
	}
	
}
