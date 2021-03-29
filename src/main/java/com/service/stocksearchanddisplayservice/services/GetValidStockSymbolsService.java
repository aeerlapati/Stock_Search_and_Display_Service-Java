package com.service.stocksearchanddisplayservice.services;

import static com.service.stocksearchanddisplayservice.util.Constants.LOCATION;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.google.common.collect.Iterables;
import com.service.stocksearchanddisplayservice.client.GetSymbolsClient;
import com.service.stocksearchanddisplayservice.exception.ServiceException;
import com.service.stocksearchanddisplayservice.models.StockSymbols;
import com.service.stocksearchanddisplayservice.models.StocksData;
import com.service.stocksearchanddisplayservice.repository.StocksRepository;
import com.service.stocksearchanddisplayservice.util.LogMarker;
import com.service.stocksearchanddisplayservice.util.Utility;

@Service
public class GetValidStockSymbolsService 
{
    
	private static final Logger log = LoggerFactory.getLogger(GetValidStockSymbolsService.class);
	
    @Autowired
    private GetSymbolsClient getSymbolsClient;

    @Autowired
    private StocksRepository stocksRepository;

    public Iterable<StocksData> getValidStockSymbols() throws ServiceException 
    {
    	log.info(LogMarker.SERVICE_ENTRY.getMarker(), "getValidStockSymbols: call started");

        List<StockSymbols> stockSymbolsResponse = new ArrayList<>();

        try
        {
            Iterable<StocksData> allStocks =  stocksRepository.findAll();
            
            if(Objects.nonNull(allStocks) && Iterables.size(allStocks) > 0)
            {
               return allStocks;
            }else{
                throw new ServiceException(Utility.buildErrorResponse("ERROR", "-1", "", "Stocks Information is currently unavailable, please try after some time", ""), HttpStatus.NOT_FOUND);
            }

        }
        catch(Exception e)
        {
        	log.info(LogMarker.SERVICE_ERROR.getMarker(), "getValidStockSymbols: call ended with error: {}", e.getMessage()); ;
            throw new ServiceException(Utility.buildErrorResponse("ERROR", "-1", "", "Stocks Information is currently unavailable, please try after some time", ""), HttpStatus.EXPECTATION_FAILED);
        }
    }
}