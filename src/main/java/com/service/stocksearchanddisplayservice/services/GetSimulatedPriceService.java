package com.service.stocksearchanddisplayservice.services;

import java.util.ArrayList;
import java.util.List;

import com.service.stocksearchanddisplayservice.client.GetSimulatedPriceClient;
import com.service.stocksearchanddisplayservice.models.SimulatedPrice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetSimulatedPriceService {
    
    @Autowired
    private GetSimulatedPriceClient getSimulatedPriceClient;


    @SuppressWarnings("unchecked")
    public SimulatedPrice getSimulatedPrices(String symbol) {
       	        
        SimulatedPrice simulatedPrice = new SimulatedPrice();
        simulatedPrice = getSimulatedPriceClient.getSimulatedPrice("fbd082f1d430-abhinav", symbol);
      
        return simulatedPrice;
    }
}