package com.service.stocksearchanddisplayservice.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.service.stocksearchanddisplayservice.exception.ServiceException;
import com.service.stocksearchanddisplayservice.services.GetSimulatedPriceService;
import com.service.stocksearchanddisplayservice.services.GetValidStockSymbolsService;

@Component
public class ScheduledTasks 
{

	@Autowired
    GetValidStockSymbolsService getValidStockSymbolsService;
	
	@Autowired
	GetSimulatedPriceService getSimulatedPriceService;
	boolean fetchFlag = true;
	
	public boolean fetchStockSymbolsJob() throws ServiceException
    {
		getValidStockSymbolsService.getValidStockSymbols();
		return true;
    }
	
	@Scheduled(fixedDelay = 30000)
	public void updateStockPricesJob() throws InterruptedException, Exception
	{
		if(fetchFlag)
		{
			fetchStockSymbolsJob();
			fetchFlag = false;
		}
		getSimulatedPriceService.updateSimulatedPrices();
	}
	
}