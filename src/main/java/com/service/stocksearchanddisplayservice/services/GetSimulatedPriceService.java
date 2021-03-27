package com.service.stocksearchanddisplayservice.services;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.service.stocksearchanddisplayservice.client.GetSimulatedPriceClient;
import com.service.stocksearchanddisplayservice.models.SimulatedPrice;
import com.service.stocksearchanddisplayservice.models.StocksData;
import com.service.stocksearchanddisplayservice.repository.StocksRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import antlr.StringUtils;

@Service
public class GetSimulatedPriceService {
    
    @Autowired
    private GetSimulatedPriceClient getSimulatedPriceClient;

    @Autowired
    private StocksRepository stocksRepository;


    @SuppressWarnings("unchecked")
    public ResponseEntity<SimulatedPrice> getSimulatedPrices(String symbol) {
       	        
        //ResponeEntity<SimulatedPrice> simulatedPrice = new SimulatedPrice();
        ResponseEntity<SimulatedPrice> simulatedPrice = getSimulatedPriceClient
                .getSimulatedPrice("fbd082f1d430-abhinav", symbol);
      
        return simulatedPrice;
    }


    @SuppressWarnings("unchecked")
    public void updateSimulatedPrices() throws InterruptedException, Exception {
        StocksData stocksInDB = new StocksData();
        //ResponseEntity<SimulatedPrice> simulatedPriceResponse = null;
        int counter = -1;
        try{
            
            List<StocksData> noPricesStocksList = stocksRepository.findByPrice("NA");

            if(!noPricesStocksList.isEmpty() && noPricesStocksList.size() > 0){
                    for(StocksData stockRecord:noPricesStocksList ){

                      if(counter == 0)
                      {
                        Thread.sleep(30000);
                        System.out.println("Reached limit");
                      } 

                        ResponseEntity<SimulatedPrice> simulatedPriceResponse = getSimulatedPriceClient.getSimulatedPrice("fbd082f1d430-abhinav", stockRecord.getStockSymbol());
                        System.out.println(simulatedPriceResponse.getHeaders().getFirst("X-Ratelimit-Limit"));
                        System.out.println(simulatedPriceResponse.getHeaders().getFirst("X-Ratelimit-Remaining"));
                        System.out.println("ended");
                        counter = Integer.parseInt(simulatedPriceResponse.getHeaders().getFirst("X-Ratelimit-Remaining"));
                        
                        if(!Objects.isNull(simulatedPriceResponse.getBody())){

                            stocksInDB.setId(stockRecord.getId());
                            stocksInDB.setPrice(simulatedPriceResponse.getBody().getPrice());
                            stocksInDB.setStockName(stockRecord.getStockName());
                            stocksInDB.setStockSymbol(stockRecord.getStockSymbol());
        
                            Date date = new Date(0);
                            Timestamp timestamp2 = new Timestamp(date.getTime());
        
                            stocksInDB.setPriceUpdatedTime(timestamp2);
                            stocksRepository.save(stocksInDB);
            
                            stocksInDB = new StocksData();
                            Thread.sleep(10000);
                        }

                    }
            }else{

                Iterable<StocksData> queryStocksList = stocksRepository.findAll();

                    for(StocksData stockRecord:queryStocksList ){


                        //if(stockRecord.getPrice() != null && !stockRecord.getPrice().isEmpty())
                        
                        ResponseEntity<SimulatedPrice> simulatedPrice = getSimulatedPriceClient.getSimulatedPrice("fbd082f1d430-abhinav", stockRecord.getStockSymbol());
                        
                        if(!Objects.isNull(simulatedPrice.getBody())){
                            stocksInDB.setId(stockRecord.getId());
                            stocksInDB.setPrice(simulatedPrice.getBody().getPrice());
                            stocksInDB.setStockName(stockRecord.getStockName());
                            stocksInDB.setStockSymbol(stockRecord.getStockSymbol());

                            Date date = new Date(0);
                            Timestamp timestamp2 = new Timestamp(date.getTime());

                            stocksInDB.setPriceUpdatedTime(timestamp2);
                            stocksRepository.save(stocksInDB);
            
                            stocksInDB = new StocksData();
                            Thread.sleep(10000);
                        }
                    }
            }
        }catch(Exception e){

            System.out.println(e);
        
        }
        }
        //simulatedPrice = getSimulatedPriceClient.getSimulatedPrice("fbd082f1d430-abhinav", symbol);
      
    }