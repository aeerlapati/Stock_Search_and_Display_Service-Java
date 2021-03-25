package com.service.stocksearchanddisplayservice.services;

import java.util.ArrayList;
import java.util.List;

import com.service.stocksearchanddisplayservice.client.GetSymbolsClient;

import com.service.stocksearchanddisplayservice.models.StockSymbols;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetValidStockSymbolsService {
    
    @Autowired
    private GetSymbolsClient getSymbolsClient;


    @SuppressWarnings("unchecked")
    public List<StockSymbols> getValidStockSymbols() {
       	        
        List<StockSymbols> stockSymbolsResponse = new ArrayList<>();
        stockSymbolsResponse = getSymbolsClient.stockSymbolsClient("fbd082f1d430-abhinav");
        System.out.println(stockSymbolsResponse.toString());
      
        return stockSymbolsResponse;
    }
}