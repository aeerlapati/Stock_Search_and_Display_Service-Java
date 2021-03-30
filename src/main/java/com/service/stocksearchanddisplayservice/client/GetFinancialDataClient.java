package com.service.stocksearchanddisplayservice.client;

import static com.service.stocksearchanddisplayservice.util.Constants.CLIENT_URI;
import static com.service.stocksearchanddisplayservice.util.Constants.FINANCIAL_DATA_CLIENT_NAME;
import static com.service.stocksearchanddisplayservice.util.Constants.FINANCIAL_DATA_CLIENT_URI;
import static com.service.stocksearchanddisplayservice.util.Constants.PATH_VARIABLE_SYMBOL;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import com.service.stocksearchanddisplayservice.models.FinancialData;


@FeignClient(name = FINANCIAL_DATA_CLIENT_NAME, url = CLIENT_URI)
public interface GetFinancialDataClient 
{
        @GetMapping(FINANCIAL_DATA_CLIENT_URI)
        ResponseEntity<List<FinancialData>> getFinancialData(@RequestHeader("api-key") String apiKey, @PathVariable(PATH_VARIABLE_SYMBOL) String symbol);
}
