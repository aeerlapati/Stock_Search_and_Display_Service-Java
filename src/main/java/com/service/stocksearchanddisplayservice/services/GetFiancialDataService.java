package com.service.stocksearchanddisplayservice.services;

import java.util.List;
import java.util.Objects;

import com.service.stocksearchanddisplayservice.client.GetFinancialDataClient;
import com.service.stocksearchanddisplayservice.exception.ServiceException;
import com.service.stocksearchanddisplayservice.models.FinanceDataDBObject;
import com.service.stocksearchanddisplayservice.repository.FinanceRepository;
import com.service.stocksearchanddisplayservice.util.LogMarker;
import com.service.stocksearchanddisplayservice.util.Utility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class GetFiancialDataService 
{
	
	private static final Logger log = LoggerFactory.getLogger(GetFiancialDataService.class);
    
    @Autowired
    private GetFinancialDataClient getFinancialDataClient;

    @Autowired
    private FinanceRepository financeRepository;

    ////////                                ////////
       //Update this to work as a Batch Process
    /////////                               ////////


    // public List<FinancialData> getFinancialData(String stockSymbol) 
    // {
    // 	log.info(LogMarker.SERVICE_ENTRY.getMarker(), "getFinancialData: call started");        
    //     List<FinancialData> financialData = new ArrayList<>();
    //     financialData = getFinancialDataClient.getFinancialData("fbd082f1d430-abhinav", stockSymbol);
    //     log.info(LogMarker.SERVICE_EXIT.getMarker(), "getFinancialData: call started");
    //     return financialData;
    // }

    public List<FinanceDataDBObject> getFinancialData(String stockSymbol) throws ServiceException 
    {
  
        log.info(LogMarker.SERVICE_ENTRY.getMarker(), "getFinancialData: call started");        

        try
        {

            List<FinanceDataDBObject> financeData =  financeRepository.findByStocksymbol(stockSymbol);
            
            if(Objects.nonNull(financeData) && financeData.size() > 0)
            {
               return financeData;

            }else{
                throw new ServiceException(Utility.buildErrorResponse("ERROR", "-1", "", "Financial Data is unavailable for this Stock, Please try again after sometime", ""), HttpStatus.NOT_FOUND);
            }

        }
        catch(Exception e)
        {
        	log.info(LogMarker.SERVICE_ERROR.getMarker(), "getFinancialData() Failed: {}", e.getMessage());
            throw new ServiceException(Utility.buildErrorResponse("ERROR", "-1", "", "Financial Data is unavailable for this Stock, Please try again after sometime", ""), HttpStatus.NOT_FOUND);
        }
    }
}