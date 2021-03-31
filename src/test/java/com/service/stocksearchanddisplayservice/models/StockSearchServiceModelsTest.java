package com.service.stocksearchanddisplayservice.models;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.Ignore;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;
import org.springframework.boot.test.context.SpringBootTest;

import com.google.common.collect.Lists;
import com.openpojo.random.RandomFactory;
import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.PojoMethod;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.Validator;
import com.openpojo.validation.ValidatorBuilder;
import com.openpojo.validation.rule.impl.GetterMustExistRule;
import com.openpojo.validation.rule.impl.SetterMustExistRule;
import com.openpojo.validation.test.Tester;
import com.openpojo.validation.test.impl.GetterTester;
import com.openpojo.validation.test.impl.SetterTester;

@SpringBootTest
public class StockSearchServiceModelsTest 
{
	private List<Class> classes = Lists.newArrayList(ErrorResponse.class, FinanceDataDBObject.class,FinancialData.class, SimulatedPrice.class,
			StocksData.class, StockSymbols.class);
	
	private List<PojoClass> pojoClasses = classes.stream().map(PojoClassFactory::getPojoClass).collect(Collectors.toList());
	
	@Rule 
	public ExpectedException thrown = ExpectedException.none();
	
	/*
	 * @Before public void setUp() throws Exception { classes =
	 * Lists.newArrayList(ErrorResponse.class,
	 * FinanceDataDBObject.class,FinancialData.class, SimulatedPrice.class,
	 * StocksData.class, StockSymbols.class); pojoClasses =
	 * classes.stream().map(PojoClassFactory::getPojoClass).collect(Collectors.
	 * toList()); }
	 */
	
	@Ignore
	@Test
	public void testPojoStructureAndBehavior() throws Exception
	{
		Tester tester = new Tester()
		{
			@Override
			public void run(PojoClass pojoClass)
			{
				List<PojoMethod> list = pojoClass.getPojoConstructors();
				for(PojoMethod method : list)
				{
					Class<?>[] types = method.getParameterTypes();
					int count = types.length;
					Object[] values = new Object[count];
					for(int i=0;i<count;i++)
					{
						values[i] = RandomFactory.getRandomValue(types[i]);
					}
					method.invoke(null, values);
				}
			}
		};
		
		Validator validator = ValidatorBuilder.create().with(new GetterMustExistRule()).with(new SetterMustExistRule()).with(new SetterTester())
				.with(new GetterTester()).with(tester).build();
		
		validator.validate(pojoClasses);
		
	}

}
