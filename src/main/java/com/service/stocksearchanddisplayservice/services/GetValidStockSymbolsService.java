package com.service.stocksearchanddisplayservice.services;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Objects;

import com.service.stocksearchanddisplayservice.repository.*;
import com.google.common.collect.Iterables;
import com.service.stocksearchanddisplayservice.client.GetSymbolsClient;

import com.service.stocksearchanddisplayservice.models.StockSymbols;
import com.service.stocksearchanddisplayservice.models.StocksData;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetValidStockSymbolsService {
    
    @Autowired
    private GetSymbolsClient getSymbolsClient;

    @Autowired
    private StocksRepository stocksRepository;

    @SuppressWarnings("unchecked")
    public Iterable<StocksData> getValidStockSymbols() {
        System.out.println("allStocks");

        List<StockSymbols> stockSymbolsResponse = new ArrayList<>();
        try{
            Iterable<StocksData> allStocks =  stocksRepository.findAll();
            System.out.println(allStocks);
            if(allStocks == null || Iterables.size(allStocks) == 0){
                stockSymbolsResponse = getSymbolsClient.stockSymbolsClient("fbd082f1d430-abhinav");
                StocksData stockData = new StocksData();
    
                for(StockSymbols stockrecord: stockSymbolsResponse){
                    stockData.setStockName(stockrecord.getName());
                    stockData.setStockSymbol(stockrecord.getSymbol());
                    stockData.setPrice("NA");

                    stocksRepository.save(stockData);
        
                    stockData = new StocksData();
                }

                 allStocks =  stocksRepository.findAll();
            }
            return allStocks;

        }catch(Exception e){
            System.out.println(e.getStackTrace());
            throw e;
        }

        //System.out.println(stockSymbolsResponse.toString());
    }
}