package com.service.stocksearchanddisplayservice.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.service.stocksearchanddisplayservice.client.GetFinancialDataClient;
import com.service.stocksearchanddisplayservice.exception.ServiceException;
import com.service.stocksearchanddisplayservice.models.FinanceDataDBObject;
import com.service.stocksearchanddisplayservice.repository.FinanceRepository;
import com.service.stocksearchanddisplayservice.util.LogMarker;
import com.service.stocksearchanddisplayservice.util.Utility;
@Service
public class GetFiancialDataService 
{
	
	private static final Logger log = LoggerFactory.getLogger(GetFiancialDataService.class);
    
    @Autowired
    private GetFinancialDataClient getFinancialDataClient;

    @Autowired
    private FinanceRepository financeRepository;
    
    @Autowired
    GetValidStockSymbolsService stockSymbolsService;
	
	/*
	 * @SuppressWarnings("null") public void getFinancialData() throws
	 * ServiceException { log.info(LogMarker.SERVICE_ENTRY.getMarker(),
	 * "getFinancialData: call started");
	 * 
	 * Iterable<StocksData> stockSymbolsDataFromDb =
	 * stockSymbolsService.getStockSymbolsFromDB();
	 * 
	 * int counter = -1;
	 * 
	 * if(Objects.nonNull(stockSymbolsDataFromDb) &&
	 * Iterables.size(stockSymbolsDataFromDb) > 0) { Iterator<StocksData>
	 * stockDataIterator = stockSymbolsDataFromDb.iterator(); while
	 * (stockDataIterator.hasNext()) { StocksData stocksDataRequest =
	 * stockDataIterator.next();
	 * 
	 * //dumping stocks data into finance db
	 * 
	 * FinanceDataDBObject financeDataDBObject = new FinanceDataDBObject();
	 * financeDataDBObject.setStocksymbol(stocksDataRequest.getStockSymbol());
	 * financeDataDBObject.setFinanceInfoPayload(Utility.convertObjectToJson(" "));
	 * financeDataDBObject.setFinanceInfoSavedFlag("FINANCIAL_INFO_PENDING");
	 * log.info("financeDataDBObject saved for first time: {}",
	 * financeDataDBObject); financeRepository.save(financeDataDBObject); }
	 * 
	 * 
	 * List<FinanceDataDBObject> financeDataListFromDb =
	 * financeRepository.findByFinanceInfoSavedFlag("FINANCIAL_INFO_PENDING");
	 * 
	 * for(FinanceDataDBObject financeData : financeDataListFromDb) { if (counter ==
	 * 0) { break; }
	 * 
	 * ResponseEntity<List<FinancialData>> financialDataResponse =
	 * getFinancialDataClient.getFinancialData(API_KEY,
	 * financeData.getStockSymbol()); }
	 * 
	 * 
	 * 
	 * 
	 * 
	 * log.
	 * info("Total attempts: {}, Remaining attempts: {} for getFinancialData API call"
	 * , financialDataResponse.getHeaders().getFirst("X-Ratelimit-Limit"),
	 * financialDataResponse.getHeaders().getFirst("X-Ratelimit-Remaining"));
	 * counter = Integer.parseInt(financialDataResponse.getHeaders().getFirst(
	 * "X-Ratelimit-Remaining"));
	 * 
	 * 
	 * if (Objects.nonNull(financialDataResponse.getBody())) {
	 * List<FinanceDataDBObject> financeDataListFromDb = financeRepository
	 * .findByStocksymbol(stocksDataRequest.getStockSymbol());
	 * 
	 * if (!financeDataListFromDb.isEmpty() && financeDataListFromDb.size() > 0) {
	 * 
	 * for(FinanceDataDBObject financeData: financeDataListFromDb) {
	 * //FinanceDataDBObject financeDataDBObject = financeDataListFromDb.get(0);
	 * if(financeDataDBObject.getStocksymbol() == stocksDataRequest.getStockSymbol()
	 * && financeDataDBObject.getFinanceInfoSavedFlag().equals("SAVE_COMPLETED")) {
	 * break; } else {
	 * financeDataDBObject.setFinanceInfoPayload(Utility.convertObjectToJson(
	 * financialDataResponse.getBody()));
	 * financeDataDBObject.setFinanceInfoSavedFlag("UPDATE_COMPLETED");
	 * log.info("financeDataDBObject updated: {}", financeDataDBObject);
	 * financeRepository.save(financeDataDBObject); } }
	 * 
	 * 
	 * } else { FinanceDataDBObject financeDataDBObject = new FinanceDataDBObject();
	 * financeDataDBObject.setStocksymbol(stocksDataRequest.getStockSymbol());
	 * financeDataDBObject.setFinanceInfoPayload(Utility.convertObjectToJson(
	 * financialDataResponse.getBody()));
	 * financeDataDBObject.setFinanceInfoSavedFlag("SAVE_COMPLETED");
	 * log.info("financeDataDBObject saved: {}", financeDataDBObject);
	 * financeRepository.save(financeDataDBObject); } }
	 * 
	 * } // return financialData.getBody(); }
	 * log.info(LogMarker.SERVICE_EXIT.getMarker(), "getFinancialData: call ended");
	 * }
	 */
	 

	public List<FinanceDataDBObject> getFinancialDataFromDB(String stockSymbol) throws ServiceException 
    {
  
        log.info(LogMarker.SERVICE_ENTRY.getMarker(), "getFinancialDataFromDB: call started");        

        try
        {

            List<FinanceDataDBObject> financeData =  financeRepository.findByStockSymbol(stockSymbol);
            
            if(Objects.nonNull(financeData) && financeData.size() > 0)
            {
               return financeData;

            }else{
                throw new ServiceException(Utility.buildErrorResponse("ERROR", "-1", "", "Financial Data is unavailable for this Stock, Please try again after sometime", ""), HttpStatus.NOT_FOUND);
            }

        }
        catch(Exception e)
        {
        	log.info(LogMarker.SERVICE_ERROR.getMarker(), "getFinancialDataFromDB: Failed: {}", e.getMessage());
            //throw new ServiceException(Utility.buildErrorResponse("ERROR", "-1", "", "Financial Data is unavailable for this Stock, Please try again after sometime", ""), HttpStatus.NOT_FOUND);
        	return new ArrayList<>();
        }
    }
}