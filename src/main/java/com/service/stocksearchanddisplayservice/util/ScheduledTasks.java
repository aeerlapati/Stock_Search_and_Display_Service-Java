package com.service.stocksearchanddisplayservice.util;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.google.common.collect.Iterables;
import com.service.stocksearchanddisplayservice.exception.ServiceException;
import com.service.stocksearchanddisplayservice.models.StocksData;
import com.service.stocksearchanddisplayservice.services.GetFinancialDataService;
import com.service.stocksearchanddisplayservice.services.GetSimulatedPriceService;
import com.service.stocksearchanddisplayservice.services.GetValidStockSymbolsService;

@Component
public class ScheduledTasks {

	@Autowired
	GetValidStockSymbolsService getValidStockSymbolsService;

	@Autowired
	GetSimulatedPriceService getSimulatedPriceService;

	@Autowired
	GetFinancialDataService financialDataService;

	boolean fetchFlag = true;

	public Iterable<StocksData> fetchStockSymbolsJob() throws ServiceException {
		Iterable<StocksData> stockSymbolsData = getValidStockSymbolsService.getValidStockSymbols();
		return stockSymbolsData;
	}

	@Scheduled(fixedDelay = 50000)
	public void updateStockPricesJob() throws InterruptedException, Exception {
		if (fetchFlag) {
			Iterable<StocksData> stockSymbolsData = fetchStockSymbolsJob();
			if (Objects.nonNull(stockSymbolsData) && Iterables.size(stockSymbolsData) > 0) {
				fetchFlag = false;
			} else {
				fetchFlag = true;
			}

		}
		getSimulatedPriceService.updateSimulatedPrices();

	}

	@Scheduled(initialDelay = 5000, fixedDelay = 60000)
	public void updateStockFinancialInfoJob() throws InterruptedException, Exception {

		financialDataService.getFinancialData();
	}

}