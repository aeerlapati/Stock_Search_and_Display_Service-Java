package com.service.stocksearchanddisplayservice.client;

import static com.service.stocksearchanddisplayservice.util.Constants.CLIENT_URI;
import static com.service.stocksearchanddisplayservice.util.Constants.STOCK_SYMBOL_CLIENT_NAME;
import static com.service.stocksearchanddisplayservice.util.Constants.STOCK_SYMBOL_CLIENT_URI;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import com.service.stocksearchanddisplayservice.models.StockSymbols;


@FeignClient(name = STOCK_SYMBOL_CLIENT_NAME, url = CLIENT_URI)
public interface GetSymbolsClient 
{
    @GetMapping(STOCK_SYMBOL_CLIENT_URI)
    List<StockSymbols> stockSymbolsClient(@RequestHeader("api-key") String apiKey);
}