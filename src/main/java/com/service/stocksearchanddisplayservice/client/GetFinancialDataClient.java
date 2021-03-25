package com.service.stocksearchanddisplayservice.client;

import static com.service.stocksearchanddisplayservice.Constants.CLIENT_URI;
import static com.service.stocksearchanddisplayservice.Constants.FINANCIAL_DATA_CLIENT_NAME;
import static com.service.stocksearchanddisplayservice.Constants.FINANCIAL_DATA_CLIENT_URI;
import static com.service.stocksearchanddisplayservice.Constants.API_KEY;
import static com.service.stocksearchanddisplayservice.Constants.PATH_VARIABLE_SYMBOL;

import java.util.List;

import com.service.stocksearchanddisplayservice.models.FinancialData;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;


@FeignClient(name = FINANCIAL_DATA_CLIENT_NAME, url = CLIENT_URI)
public interface GetFinancialDataClient {
        
        @GetMapping(FINANCIAL_DATA_CLIENT_URI)
        List<FinancialData> getFinancialData(@RequestHeader(API_KEY) String apiKey, @PathVariable(PATH_VARIABLE_SYMBOL) String symbol);
}