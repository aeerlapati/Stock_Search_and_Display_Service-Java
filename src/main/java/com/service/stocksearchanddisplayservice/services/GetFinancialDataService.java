package com.service.stocksearchanddisplayservice.services;

import static com.service.stocksearchanddisplayservice.util.Constants.API_KEY;

import java.util.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.google.common.collect.Iterables;
import com.service.stocksearchanddisplayservice.client.GetFinancialDataClient;
import com.service.stocksearchanddisplayservice.exception.ServiceException;
import com.service.stocksearchanddisplayservice.models.FinanceDataDBObject;
import com.service.stocksearchanddisplayservice.models.FinancialData;
import com.service.stocksearchanddisplayservice.models.StocksData;
import com.service.stocksearchanddisplayservice.repository.FinanceRepository;
import com.service.stocksearchanddisplayservice.util.LogMarker;
import com.service.stocksearchanddisplayservice.util.Utility;

@Service
public class GetFinancialDataService {

	private static final Logger log = LoggerFactory.getLogger(GetFinancialDataService.class);

	@Autowired
	private GetFinancialDataClient getFinancialDataClient;

	@Autowired
	private FinanceRepository financeRepository;

	@Autowired
	GetValidStockSymbolsService stockSymbolsService;

	
	@SuppressWarnings("null")
	public void getFinancialData() throws ServiceException 
	{
		log.info(LogMarker.SERVICE_ENTRY.getMarker(), "getFinancialData: call started");
		
		Iterable<FinanceDataDBObject> financeInfoListFromDb = financeRepository.findAll();
		
		log.info("stock finance db records size: {}", Iterables.size(financeInfoListFromDb));
		//ArrayList<FinanceDataDBObject> stockFinanceDataList = new ArrayList<>();
		
		List<FinanceDataDBObject> financeDataListFromDb = financeRepository.findByFinanceInfoPayload("NA");
		
		if((Objects.isNull(financeInfoListFromDb) || Iterables.size(financeInfoListFromDb) == 0 ))
        {
			Iterable<StocksData> stockSymbolsDataFromDb = stockSymbolsService.getStockSymbolsFromDB();
			
			log.info("stock symbols fetched from db: {}", stockSymbolsDataFromDb);
			
			int counter = -1;

			if (Objects.nonNull(stockSymbolsDataFromDb) && Iterables.size(stockSymbolsDataFromDb) > 0) 
			{
				Iterator<StocksData> stockDataIterator = stockSymbolsDataFromDb.iterator();
				
				//"saving stock symbols to financial info DB"
				while (stockDataIterator.hasNext())
				{
					StocksData stocksDataRequest = stockDataIterator.next();

					// dumping stocks data into finance db

					FinanceDataDBObject financeDataDBObject = new FinanceDataDBObject();
					financeDataDBObject.setStockSymbol(stocksDataRequest.getStockSymbol());
					financeDataDBObject.setFinanceInfoPayload("NA");
					financeDataDBObject.setStockUpdatedTime(FinanceDataDBObject.newDate());
					//financeDataDBObject.setFinanceInfoSavedFlag("FINANCIAL_INFO_PENDING");
					log.info("financeDataDBObject saved for first time: {}", financeDataDBObject);
					financeRepository.save(financeDataDBObject);
				}

				//List<FinanceDataDBObject> financeDataListFromDb = financeRepository.findByFinanceInfoPayload("NA");
				
				if (!financeDataListFromDb.isEmpty() && financeDataListFromDb.size() > 0) 
				{

					for (FinanceDataDBObject financeData : financeDataListFromDb) 
					{
						if (counter == 0) 
						{
							break;
						}

						ResponseEntity<List<FinancialData>> financialDataResponse = getFinancialDataClient
								.getFinancialData(API_KEY, financeData.getStockSymbol());

						log.info("Total attempts: {}, Remaining attempts: {} for getFinancialData API call",
								financialDataResponse.getHeaders().getFirst("X-Ratelimit-Limit"),
								financialDataResponse.getHeaders().getFirst("X-Ratelimit-Remaining"));
						counter = Integer.parseInt(financialDataResponse.getHeaders().getFirst("X-Ratelimit-Remaining"));

						if (Objects.nonNull(financialDataResponse.getBody())
								&& financialDataResponse.getBody().size() > 0)
						{
							financeData.setFinanceInfoPayload(Utility.convertObjectToJson(financialDataResponse.getBody()));
							financeData.setStockUpdatedTime(FinanceDataDBObject.newDate());
							financeRepository.save(financeData);
							log.info("financeDataDBObject updated for first time: {}", financeData);
						}

					}
				}
				/*
				 * else { log.info("updating financial data (In Sub ELSE block: )");
				 * 
				 * financeInfoListFromDb = financeRepository.findAll();
				 * 
				 * stockFinanceDataList = new ArrayList<>();
				 * 
				 * if(Objects.nonNull(financeInfoListFromDb)) {
				 * financeInfoListFromDb.forEach(stockFinanceDataList::add); }
				 * 
				 * Collections.sort(stockFinanceDataList, (s1, s2) ->
				 * s1.getStockUpdatedTime().compareTo(s2.getStockUpdatedTime()));
				 * System.out.println(stockFinanceDataList);
				 * 
				 * for (FinanceDataDBObject financeData : stockFinanceDataList) { if (counter ==
				 * 0) { break; }
				 * 
				 * ResponseEntity<List<FinancialData>> financialDataResponse =
				 * getFinancialDataClient .getFinancialData(API_KEY,
				 * financeData.getStockSymbol());
				 * 
				 * log.
				 * info("Total attempts: {}, Remaining attempts: {} for getFinancialData API call"
				 * , financialDataResponse.getHeaders().getFirst("X-Ratelimit-Limit"),
				 * financialDataResponse.getHeaders().getFirst("X-Ratelimit-Remaining"));
				 * counter = Integer.parseInt(financialDataResponse.getHeaders().getFirst(
				 * "X-Ratelimit-Remaining"));
				 * 
				 * if (Objects.nonNull(financialDataResponse.getBody()) &&
				 * financialDataResponse.getBody().size() > 0) {
				 * financeData.setFinanceInfoPayload(Utility.convertObjectToJson(
				 * financialDataResponse.getBody())); financeRepository.save(financeData); } } }
				 */
			
          }
		} 
		else
		{
			
			log.info("updating financial data (In Main ELSE block)");
			
			int counter = -1;
			
			Iterable<FinanceDataDBObject> financeInfoListDb = financeRepository.findAll();

			List<FinanceDataDBObject> stockFinanceData = new ArrayList<>();
			
			if(!financeDataListFromDb.isEmpty() && financeDataListFromDb.size() > 0)
			{
				stockFinanceData = financeDataListFromDb;
			}
			else
			{
				if(Objects.nonNull(financeInfoListDb))
				{
					financeInfoListDb.forEach(stockFinanceData::add);
				}	

				Collections.sort(stockFinanceData, (s1, s2) -> s1.getStockUpdatedTime().compareTo(s2.getStockUpdatedTime()));
			}	

			for (FinanceDataDBObject financeData : stockFinanceData) 
			{
				if (counter == 0)
				{
					break;
				}

				ResponseEntity<List<FinancialData>> financialDataResponse = getFinancialDataClient
						.getFinancialData(API_KEY, financeData.getStockSymbol());

				log.info("Total attempts: {}, Remaining attempts: {} for getFinancialData API call",
						financialDataResponse.getHeaders().getFirst("X-Ratelimit-Limit"),
						financialDataResponse.getHeaders().getFirst("X-Ratelimit-Remaining"));
				counter = Integer.parseInt(financialDataResponse.getHeaders().getFirst("X-Ratelimit-Remaining"));

				if (Objects.nonNull(financialDataResponse.getBody())
						&& financialDataResponse.getBody().size() > 0) 
				{
					financeData.setFinanceInfoPayload(Utility.convertObjectToJson(financialDataResponse.getBody()));
					financeData.setStockUpdatedTime(FinanceDataDBObject.newDate());
					financeRepository.save(financeData);
					log.info("financeDataDBObject updated (In Main ELSE block): {}", financeData);
				}
			}	

		}
		log.info(LogMarker.SERVICE_EXIT.getMarker(), "getFinancialData: call ended");
	}

	public List<FinanceDataDBObject> getFinancialDataFromDB(String stockSymbol) throws ServiceException {

		log.info(LogMarker.SERVICE_ENTRY.getMarker(), "getFinancialDataFromDB: call started");

		try {

			List<FinanceDataDBObject> financeData = financeRepository.findByStockSymbol(stockSymbol);

			if (Objects.nonNull(financeData) && financeData.size() > 0) {
				return financeData;

			} 

		} catch (Exception e) {
			log.error(LogMarker.SERVICE_ERROR.getMarker(), "getFinancialDataFromDB: Failed: {}", e.getMessage());
			// throw new ServiceException(Utility.buildErrorResponse("ERROR", "-1", "",
			// "Financial Data is unavailable for this Stock, Please try again after
			// sometime", ""), HttpStatus.NOT_FOUND);
			return new ArrayList<>();
		}
		return new ArrayList<>();
	}
}