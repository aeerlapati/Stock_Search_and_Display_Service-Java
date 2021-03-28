package com.service.stocksearchanddisplayservice.services;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.service.stocksearchanddisplayservice.client.GetFinancialDataClient;
import com.service.stocksearchanddisplayservice.models.FinancialData;
import com.service.stocksearchanddisplayservice.util.LogMarker;

@Service
public class GetFiancialDataService 
{
	
	private static final Logger log = LoggerFactory.getLogger(GetFiancialDataService.class);
    
    @Autowired
    private GetFinancialDataClient getFinancialDataClient;

    public List<FinancialData> getFinancialData(String stockSymbol) 
    {
    	log.info(LogMarker.SERVICE_ENTRY.getMarker(), "getFinancialData: call started");        
        List<FinancialData> financialData = new ArrayList<>();
        financialData = getFinancialDataClient.getFinancialData("fbd082f1d430-abhinav", stockSymbol);
        log.info(LogMarker.SERVICE_EXIT.getMarker(), "getFinancialData: call started");
        return financialData;
    }
}