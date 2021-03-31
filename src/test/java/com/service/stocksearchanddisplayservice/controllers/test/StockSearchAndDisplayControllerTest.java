package com.service.stocksearchanddisplayservice.controllers.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.common.collect.Iterables;
import com.service.stocksearchanddisplayservice.client.GetFinancialDataClient;
import com.service.stocksearchanddisplayservice.controllers.StockSearchAndDisplayController;
import com.service.stocksearchanddisplayservice.exception.ServiceException;
import com.service.stocksearchanddisplayservice.models.FinanceDataDBObject;
import com.service.stocksearchanddisplayservice.models.FinancialData;
import com.service.stocksearchanddisplayservice.models.StocksData;
import com.service.stocksearchanddisplayservice.repository.FinanceRepository;
import com.service.stocksearchanddisplayservice.repository.StocksRepository;
import com.service.stocksearchanddisplayservice.services.GetFinancialDataService;
import com.service.stocksearchanddisplayservice.services.GetSimulatedPriceService;
import com.service.stocksearchanddisplayservice.services.GetValidStockSymbolsService;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringJUnit4ClassRunner.class)
@TestPropertySource(properties = "app.scheduling.enable=false")
public class StockSearchAndDisplayControllerTest {

    @Mock
    StocksRepository stocksRepositoryMock;

    @Mock
    FinanceRepository financeRepositoryMock;

    List<FinanceDataDBObject> stockFinancialRecordList;

    List<FinanceDataDBObject> stubbedFinanceData = new ArrayList<>();

    List<StocksData> stubbedStocksData = new ArrayList<>();
     
    @Mock
    GetFinancialDataService getFinancialDataServiceMock;

    @Mock
    GetValidStockSymbolsService stockSymbolsService;

    @Mock
    GetSimulatedPriceService getSimulatedPriceService;

    @InjectMocks
    StockSearchAndDisplayController stockSearchAndDisplayController;

    @Mock
    GetFinancialDataClient getFinancialDataClient;

    List<FinancialData> stubbedFinancialData = new ArrayList<>();

	private OngoingStubbing<Iterable<StocksData>> thenReturn;

    @Test
    void testStockSymbolsFromDB() {
        try {
            when(stockSymbolsService.getStockSymbolsFromDB()).thenReturn(prepareStocksDataIterable());
            
            String expectedStockSymbol = "AAPL";
            ResponseEntity<Iterable<StocksData>> tempResult = stockSearchAndDisplayController.getStockSymbols();

            assertEquals(expectedStockSymbol, Iterables.get(tempResult.getBody(), 0).getStockSymbol());

        } catch (ServiceException e) {
            e.printStackTrace();
        } catch(Exception e){
            e.printStackTrace();
        }

    }


    @Test
    void testgetFinancialData() {
        try {

            prepareFinanaceStubbedDataObject();

            when(getFinancialDataServiceMock.getFinancialDataFromDB(anyString())).thenReturn(stockFinancialRecordList);
            
            String expectedStockSymbol = "AAPL";
            ResponseEntity<List<FinanceDataDBObject>> tempResult = stockSearchAndDisplayController.getFinancialData("AAPL");

            assertEquals(expectedStockSymbol, Iterables.get(tempResult.getBody(), 0).getStockSymbol());

        } catch (ServiceException e) {
            e.printStackTrace();
        } catch(Exception e){
            e.printStackTrace();
        }

    }


    @Test
    void testupdateStockPrices() {
        try {

            prepareFinanaceStubbedDataObject();
            
            getSimulatedPriceService.updateSimulatedPrices();

            String expectedStockSymbol = "AAPL";
            stockSearchAndDisplayController.updateStockPrices();

            //assertEquals(expectedStockSymbol, Iterables.get(tempResult.getBody(), 0).getStockSymbol());

        } catch (ServiceException e) {
            e.printStackTrace();
        } catch(Exception e){
            e.printStackTrace();
        }

    }


    private Iterable<StocksData> prepareStocksDataIterable() {
        
        StocksData dummyObject = new StocksData();

        dummyObject.setId(1);
        dummyObject.setStockSymbol("AAPL");
        dummyObject.setPriceUpdatedTime(StocksData.newDate());
        dummyObject.setStockName("Apple");
        dummyObject.setPrice("100");

        stubbedStocksData.add(dummyObject);

        Iterable<StocksData> returnVal = Iterable.class.cast(stubbedStocksData);
        return returnVal;

    }

    private FinanceDataDBObject prepareFinanaceStubbedDataObject() {
        
        FinanceDataDBObject dummyObject = new FinanceDataDBObject();

        dummyObject.setId(1);
        dummyObject.setStockSymbol("AAPL");
        dummyObject.setStockUpdatedTime(FinanceDataDBObject.newDate());
        dummyObject.setFinanceInfoPayload("NA");

        stockFinancialRecordList.add(dummyObject);
        return dummyObject;
    }


    private void prepeareFinancialDataStub() {
        
        FinancialData dummyObject = new FinancialData();

        dummyObject.setCaptial_expenditure("123");
        dummyObject.setRevenue("123");
        dummyObject.setCurrency("USD");
        dummyObject.setDate("NA");
        dummyObject.setEbitda("123");
        dummyObject.setEpsl("777");
        dummyObject.setFree_cash_flow("123");
        dummyObject.setGross_profit("736");
        dummyObject.setLong_term_debt("111");
        dummyObject.setNet_income("914");
        dummyObject.setSec_link("https://www.google.com");
        dummyObject.setIncome_before_tax("98");
        dummyObject.setRevenue("1986");
        dummyObject.setWeighted_shares_outstanding("32");

        stubbedFinancialData.add(dummyObject);
    }

}