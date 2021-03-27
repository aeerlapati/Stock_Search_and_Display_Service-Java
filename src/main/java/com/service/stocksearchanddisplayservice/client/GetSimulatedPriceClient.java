package com.service.stocksearchanddisplayservice.client;

import static com.service.stocksearchanddisplayservice.Constants.CLIENT_URI;
import static com.service.stocksearchanddisplayservice.Constants.SIMULATED_PRICE_CLIENT_NAME;
import static com.service.stocksearchanddisplayservice.Constants.SIMULATED_PRICE_CLIENT_URI;
import static com.service.stocksearchanddisplayservice.Constants.API_KEY;
import static com.service.stocksearchanddisplayservice.Constants.PATH_VARIABLE_SYMBOL;

import com.service.stocksearchanddisplayservice.models.SimulatedPrice;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;


 @FeignClient(name = SIMULATED_PRICE_CLIENT_NAME, url = CLIENT_URI)
 public interface GetSimulatedPriceClient {
    
     @GetMapping(SIMULATED_PRICE_CLIENT_URI)
     ResponseEntity<SimulatedPrice> getSimulatedPrice(@RequestHeader(API_KEY) String apiKey, @PathVariable(PATH_VARIABLE_SYMBOL) String symbol);
 }