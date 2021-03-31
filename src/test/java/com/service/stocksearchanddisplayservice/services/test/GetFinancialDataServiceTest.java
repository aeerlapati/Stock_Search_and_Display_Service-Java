package com.service.stocksearchanddisplayservice.services.test;

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
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.service.stocksearchanddisplayservice.client.GetFinancialDataClient;
import com.service.stocksearchanddisplayservice.exception.ServiceException;
import com.service.stocksearchanddisplayservice.models.FinanceDataDBObject;
import com.service.stocksearchanddisplayservice.models.FinancialData;
import com.service.stocksearchanddisplayservice.models.StocksData;
import com.service.stocksearchanddisplayservice.repository.FinanceRepository;
import com.service.stocksearchanddisplayservice.repository.StocksRepository;
import com.service.stocksearchanddisplayservice.services.GetFinancialDataService;
import com.service.stocksearchanddisplayservice.services.GetValidStockSymbolsService;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringJUnit4ClassRunner.class)
@TestPropertySource(properties = "app.scheduling.enable=false")
public class GetFinancialDataServiceTest {

    @Mock
    StocksRepository stocksRepositoryMock;

    @Mock
    FinanceRepository financeRepositoryMock;

    List<FinanceDataDBObject> stockFinancialRecordList;

    List<FinanceDataDBObject> stubbedFinanceData = new ArrayList<>();

    List<StocksData> stubbedStocksData = new ArrayList<>();
     
    @InjectMocks
    GetFinancialDataService getFinancialDataServiceMock;

    @InjectMocks
    GetValidStockSymbolsService stockSymbolsService;

    @Mock
    GetFinancialDataClient getFinancialDataClient;

    List<FinancialData> stubbedFinancialData = new ArrayList<>();

    @Test
    void testGetFinancialDataFromDB() {
        when(financeRepositoryMock.findByStockSymbol(anyString())).thenReturn(stubbedFinanceData);
        System.out.println("stockFinancialRecordList");
        System.out.println(stockFinancialRecordList);
        String expectedStockSymbol = "AAPL";
        prepareFinanceStubbesData();
        List<FinanceDataDBObject> tempResult = new ArrayList<>();
        try {
            tempResult = getFinancialDataServiceMock.getFinancialDataFromDB(expectedStockSymbol);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        assertEquals(expectedStockSymbol, tempResult.get(0).getStockSymbol());

    }

    @Test
    void testGetFinancialData() {

        prepareFinanceStubbesData();

        prepeareFinancialDataStub();

        try {

                    when(financeRepositoryMock.findAll()).thenReturn(prepareFinanceDataIterable());

                    when(financeRepositoryMock.findByFinanceInfoPayload(anyString())).thenReturn(stubbedFinanceData);
                    
                    when(stockSymbolsService.getStockSymbolsFromDB()).thenReturn(prepareStocksDataIterable());

                    when(financeRepositoryMock.save(any())).thenReturn(prepareFinanaceStubbedDataObject());
                    
                    HttpHeaders headers = new HttpHeaders();
                    headers.add("X-Ratelimit-Remaining", "0");
                    
					when(getFinancialDataClient.getFinancialData(anyString(),anyString())).thenReturn( new ResponseEntity<List<FinancialData>>(stubbedFinancialData, headers,HttpStatus.OK));

                    getFinancialDataServiceMock.getFinancialData();

        } catch (ServiceException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

    }

     private void prepareFinanceStubbesData() {
        
         FinanceDataDBObject financeDBObject = new FinanceDataDBObject();

         financeDBObject.setId(1);
         financeDBObject.setStockSymbol("AAPL");
         financeDBObject.setStockUpdatedTime(FinanceDataDBObject.newDate());
         financeDBObject.setFinanceInfoPayload("NA");

         stubbedFinanceData.add(financeDBObject);
     }

     private Iterable<FinanceDataDBObject> prepareFinanceDataIterable() {
        
        FinanceDataDBObject financeDBObject = new FinanceDataDBObject();

        financeDBObject.setId(1);
        financeDBObject.setStockSymbol("AAPL");
        financeDBObject.setStockUpdatedTime(FinanceDataDBObject.newDate());
        financeDBObject.setFinanceInfoPayload("NA");

        stubbedFinanceData.add(financeDBObject);

        Iterable<FinanceDataDBObject> returnVal =   Iterable.class.cast(stubbedFinanceData);
        
        //(Iterable<FinanceDataDBObject>) Arrays.asList(stubbedFinanceData).iterator();
        return returnVal;

    }


    private Iterable<StocksData> prepareStocksDataIterable() {
        
        StocksData stocksDataObj = new StocksData();

        stocksDataObj.setId(1);
        stocksDataObj.setStockSymbol("AAPL");
        stocksDataObj.setPriceUpdatedTime(StocksData.newDate());
        stocksDataObj.setStockName("Apple");
        stocksDataObj.setPrice("100");

        stubbedStocksData.add(stocksDataObj);

        Iterable<StocksData> returnVal = Iterable.class.cast(stubbedStocksData);
        return returnVal;

    }

    private FinanceDataDBObject prepareFinanaceStubbedDataObject() {
        
        FinanceDataDBObject financeDBObject = new FinanceDataDBObject();

        financeDBObject.setId(1);
        financeDBObject.setStockSymbol("AAPL");
        financeDBObject.setStockUpdatedTime(FinanceDataDBObject.newDate());
        financeDBObject.setFinanceInfoPayload("NA");

        return financeDBObject;
    }


    private void prepeareFinancialDataStub() {
        
        FinancialData financialDataObj = new FinancialData();

        financialDataObj.setCaptial_expenditure("123");
        financialDataObj.setRevenue("123");
        financialDataObj.setCurrency("USD");
        financialDataObj.setDate("NA");
        financialDataObj.setEbitda("123");
        financialDataObj.setEpsl("777");
        financialDataObj.setFree_cash_flow("123");
        financialDataObj.setGross_profit("736");
        financialDataObj.setLong_term_debt("111");
        financialDataObj.setNet_income("914");
        financialDataObj.setSec_link("https://www.google.com");
        financialDataObj.setIncome_before_tax("98");
        financialDataObj.setRevenue("1986");
        financialDataObj.setWeighted_shares_outstanding("32");

        stubbedFinancialData.add(financialDataObj);
    }

}