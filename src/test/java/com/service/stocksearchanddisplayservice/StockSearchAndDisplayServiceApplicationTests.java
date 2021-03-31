package com.service.stocksearchanddisplayservice;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = StockSearchAndDisplayServiceApplicationTests.class)
public class StockSearchAndDisplayServiceApplicationTests 
{
	@Test(expected = Test.None.class)
	public void contextLoads() 
	{
		try
		{
			StockSearchAndDisplayServiceApplication.main(new String[] {"args"});
		}
		catch(Exception e )
		{
			e.printStackTrace();
		}
		
	}
}

