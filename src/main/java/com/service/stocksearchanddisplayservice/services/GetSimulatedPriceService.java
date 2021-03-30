package com.service.stocksearchanddisplayservice.services;

import static com.service.stocksearchanddisplayservice.util.Constants.API_KEY;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.service.stocksearchanddisplayservice.client.GetSimulatedPriceClient;
import com.service.stocksearchanddisplayservice.models.SimulatedPrice;
import com.service.stocksearchanddisplayservice.models.StocksData;
import com.service.stocksearchanddisplayservice.repository.StocksRepository;
import com.service.stocksearchanddisplayservice.util.LogMarker;

@Service
public class GetSimulatedPriceService 
{
    
	private static final Logger log = LoggerFactory.getLogger(GetSimulatedPriceService.class);
	
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	
    @Autowired
    private GetSimulatedPriceClient getSimulatedPriceClient;
    
    @Autowired
    private StocksRepository stocksRepository;

    public ResponseEntity<SimulatedPrice> getSimulatedPrices(String symbol) 
    {
       	        
        ResponseEntity<SimulatedPrice> simulatedPrice = getSimulatedPriceClient.getSimulatedPrice(API_KEY, symbol);
      
        return simulatedPrice;
    }
    
    public void updateSimulatedPrices() throws InterruptedException, Exception 
    {
    	log.info(LogMarker.SERVICE_ENTRY.getMarker(), "updateSimulatedPrices: call started at time: {}",  dateFormat.format(new Date()));
    	//log.info("The time is now {}", dateFormat.format(new Date()));
        StocksData stocksInDB = new StocksData();
        int counter = -1;
        try
        {
            
            List<StocksData> noPricesStocksList = stocksRepository.findByPrice("NA");

            if(!noPricesStocksList.isEmpty() && noPricesStocksList.size() > 0)
            {
            	log.info("Entered into IF logic where the prices of all stocks haven't updated for the first time");
            	
                    updateStockInfo(stocksInDB, counter, noPricesStocksList, false);
                    log.info(LogMarker.SERVICE_EXIT.getMarker(), "updateSimulatedPrices: call ended at time: {}",  dateFormat.format(new Date()));
            }
            else
            {
            	log.info("Entered into ELSE logic, where the prices of all stocks are refreshing");
                Iterable<StocksData> queryStocksIterable = stocksRepository.findAll();
                
                List<StocksData>  stocksList = new ArrayList<>();
                if(Objects.nonNull(queryStocksIterable))
                {
                	queryStocksIterable.forEach(stocksList::add);
                	Collections.sort(stocksList, (s1, s2) -> s1.getPriceUpdatedTime().compareTo(s2.getPriceUpdatedTime()));
                    updateStockInfo(stocksInDB, counter, stocksList, true);
                }	
                log.info(LogMarker.SERVICE_EXIT.getMarker(), "updateSimulatedPrices: call ended");
            }
        }
        catch(Exception e)
        {
        	log.info(LogMarker.SERVICE_ERROR.getMarker(), "updateSimulatedPrices: call ended with exception : {}", e.getMessage());
        	//RetryableException retryableException = (RetryableException)e;
        	//System.out.println("Retry after value:" + retryableException..getFirst("Retry-After"));
        	//Thread.sleep(20000);
        	//throw new ServiceException(Utility.buildErrorResponse("ERROR", "-1", "Failed to refresh stock prices", LOCATION, ""), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

	private void updateStockInfo(StocksData stocksInDB, int counter, List<StocksData> noPricesStocksList, boolean updateFlag)
			throws InterruptedException 
	{
		for(StocksData stockRecord:noPricesStocksList )
		{

		  if(counter == 0)
		  {
			//log.info("Sleep started at: {} ", dateFormat.format(new Date()));
		    break;
			//Thread.sleep(40000);
		    //log.info("Sleep ended at: {} ", dateFormat.format(new Date()));
		  } 

		    ResponseEntity<SimulatedPrice> simulatedPriceResponse = getSimulatedPriceClient.getSimulatedPrice(API_KEY, stockRecord.getStockSymbol());
			
		    log.info("Total attempts: {}, Remaining attempts: {} for getStockPrice API call", simulatedPriceResponse.getHeaders().getFirst("X-Ratelimit-Limit"), simulatedPriceResponse.getHeaders().getFirst("X-Ratelimit-Remaining"));
		    counter = Integer.parseInt(simulatedPriceResponse.getHeaders().getFirst("X-Ratelimit-Remaining"));
		    
		    if(!Objects.isNull(simulatedPriceResponse.getBody()))
		    {

		        stocksInDB.setId(stockRecord.getId());
		        stocksInDB.setPrice(simulatedPriceResponse.getBody().getPrice());
		        stocksInDB.setStockName(stockRecord.getStockName());
		        stocksInDB.setStockSymbol(stockRecord.getStockSymbol());
		        stocksInDB.setPriceUpdatedTime(StocksData.newDate()); 
		        stocksRepository.save(stocksInDB);
		        
		        if(updateFlag)
		        {
		        	log.info("price: {} for symbol: {} has been updated in db", stocksInDB.getPrice(), stocksInDB.getStockSymbol());
		        }
		        else
		        {
		        	log.info("price: {} for symbol: {} has been saved in db for first time", stocksInDB.getPrice(), stocksInDB.getStockSymbol());
		        }	
		    }

		}
	}
}