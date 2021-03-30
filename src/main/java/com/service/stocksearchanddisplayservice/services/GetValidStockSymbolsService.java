package com.service.stocksearchanddisplayservice.services;

import static com.service.stocksearchanddisplayservice.util.Constants.API_KEY;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Iterables;
import com.service.stocksearchanddisplayservice.client.GetSymbolsClient;
import com.service.stocksearchanddisplayservice.exception.ServiceException;
import com.service.stocksearchanddisplayservice.models.StockSymbols;
import com.service.stocksearchanddisplayservice.models.StocksData;
import com.service.stocksearchanddisplayservice.repository.StocksRepository;
import com.service.stocksearchanddisplayservice.util.LogMarker;

@Service
public class GetValidStockSymbolsService 
{
    
	private static final Logger log = LoggerFactory.getLogger(GetValidStockSymbolsService.class);
	
    @Autowired
    private GetSymbolsClient getSymbolsClient;

    @Autowired
    private StocksRepository stocksRepository;

    public Iterable<StocksData> getStockSymbolsFromDB() throws ServiceException 
    {
    	log.info(LogMarker.SERVICE_ENTRY.getMarker(), "getValidStockSymbols: call started");

        try
        {
            Iterable<StocksData> allStocks =  stocksRepository.findAll();
            
            if(Objects.nonNull(allStocks) && Iterables.size(allStocks) > 0)
            {
            	log.info(LogMarker.SERVICE_EXIT.getMarker(), "getValidStockSymbols: call ended. stock details fetched: {}", allStocks);	
               return allStocks;
            }
            else
            {
            	return new ArrayList<>();
            }

        }
        catch(Exception e)
        {
        	log.info(LogMarker.SERVICE_ERROR.getMarker(), "getValidStockSymbols: call ended with error: {}", e.getMessage()); ;
            //throw new ServiceException(Utility.buildErrorResponse("ERROR", "-1", "", "Stocks Information is currently unavailable, please try after some time", ""), HttpStatus.EXPECTATION_FAILED);
        }
		return null;
    }
    
    public Iterable<StocksData> getValidStockSymbols() throws ServiceException 
    {
    	log.info(LogMarker.SERVICE_ENTRY.getMarker(), "getValidStockSymbols: call started");

        List<StockSymbols> stockSymbolsResponse = new ArrayList<>();
        try
        {
            Iterable<StocksData> allStocks =  stocksRepository.findAll();
            System.out.println(allStocks);
            if(allStocks == null || Iterables.size(allStocks) == 0)
            {
                stockSymbolsResponse = getSymbolsClient.stockSymbolsClient(API_KEY);
                StocksData stockData = new StocksData();
    
                for(StockSymbols stockrecord: stockSymbolsResponse)
                {
                    stockData.setStockName(stockrecord.getName());
                    stockData.setStockSymbol(stockrecord.getSymbol());
                    stockData.setPrice("NA");

                    stocksRepository.save(stockData);
        
                    stockData = new StocksData();
                }

                 allStocks =  stocksRepository.findAll();
            }
            log.info(LogMarker.SERVICE_EXIT.getMarker(), "getValidStockSymbols: call ended. symbols fetched: {}", allStocks);
            return allStocks;

        }
        catch(Exception e)
        {
        	log.info(LogMarker.SERVICE_ERROR.getMarker(), "getValidStockSymbols: call ended with error: {}", e.getMessage()); ;
            //throw new ServiceException(Utility.buildErrorResponse("ERROR", "-1", "Unable to fetch stock symbols", LOCATION, ""), HttpStatus.INTERNAL_SERVER_ERROR);
        }
		return null;
    }
}