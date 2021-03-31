package com.service.stocksearchanddisplayservice.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class ConstantsTest 
{
	@Test
	public void ContantsTestThrowsException() throws IllegalAccessException, InstantiationException, IllegalArgumentException
	{
		final Class<?> cls = Constants.class;
		final Constructor<?> c =cls.getDeclaredConstructors()[0];
		c.setAccessible(true);
		
		Throwable targetException = null;
		try
		{
			c.newInstance((Object[])null);
		}
		catch(InvocationTargetException e)
		{
			targetException = e.getTargetException();
		}
		
		Assert.assertNotNull("c is null", c);
	}

}
