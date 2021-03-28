package com.service.stocksearchanddisplayservice.client;

import static com.service.stocksearchanddisplayservice.util.Constants.API_KEY;
import static com.service.stocksearchanddisplayservice.util.Constants.PATH_VARIABLE_SYMBOL;
import static com.service.stocksearchanddisplayservice.util.Constants.SIMULATED_PRICE_CLIENT_NAME;
import static com.service.stocksearchanddisplayservice.util.Constants.SIMULATED_PRICE_CLIENT_URI;
import static com.service.stocksearchanddisplayservice.util.Constants.CLIENT_URI;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import com.service.stocksearchanddisplayservice.models.SimulatedPrice;


 @FeignClient(name = SIMULATED_PRICE_CLIENT_NAME, url = CLIENT_URI)
 public interface GetSimulatedPriceClient 
 {
     @GetMapping(SIMULATED_PRICE_CLIENT_URI)
     ResponseEntity<SimulatedPrice> getSimulatedPrice(@RequestHeader(API_KEY) String apiKey, @PathVariable(PATH_VARIABLE_SYMBOL) String symbol);
 }