package com.service.stocksearchanddisplayservice.services;

import java.util.ArrayList;
import java.util.List;

import com.service.stocksearchanddisplayservice.client.GetFinancialDataClient;
import com.service.stocksearchanddisplayservice.models.FinancialData;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetFiancialDataService {
    
    @Autowired
    private GetFinancialDataClient getFinancialDataClient;


    @SuppressWarnings("unchecked")
    public List<FinancialData> getFinancialData(String stockSymbol) {
       	        
        List<FinancialData> financialData = new ArrayList<>();
        financialData = getFinancialDataClient.getFinancialData("fbd082f1d430-abhinav", stockSymbol);      
        return financialData;
    }
}