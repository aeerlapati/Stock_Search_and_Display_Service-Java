package com.service.stocksearchanddisplayservice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import com.service.stocksearchanddisplayservice.client.GetFinancialDataClient;
import com.service.stocksearchanddisplayservice.exception.ServiceException;
import com.service.stocksearchanddisplayservice.models.FinanceDataDBObject;
import com.service.stocksearchanddisplayservice.models.FinancialData;
import com.service.stocksearchanddisplayservice.models.StocksData;
import com.service.stocksearchanddisplayservice.repository.FinanceRepository;
import com.service.stocksearchanddisplayservice.repository.StocksRepository;
import com.service.stocksearchanddisplayservice.services.GetFinancialDataService;
import com.service.stocksearchanddisplayservice.services.GetValidStockSymbolsService;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@AutoConfigureMockMvc
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
        
         FinanceDataDBObject dummyObject = new FinanceDataDBObject();

         dummyObject.setId(1);
         dummyObject.setStockSymbol("AAPL");
         dummyObject.setStockUpdatedTime(FinanceDataDBObject.newDate());
         dummyObject.setFinanceInfoPayload("NA");

         stubbedFinanceData.add(dummyObject);
     }

     private Iterable<FinanceDataDBObject> prepareFinanceDataIterable() {
        
        FinanceDataDBObject dummyObject = new FinanceDataDBObject();

        dummyObject.setId(1);
        dummyObject.setStockSymbol("AAPL");
        dummyObject.setStockUpdatedTime(FinanceDataDBObject.newDate());
        dummyObject.setFinanceInfoPayload("NA");

        stubbedFinanceData.add(dummyObject);

        Iterable<FinanceDataDBObject> returnVal =   Iterable.class.cast(stubbedFinanceData);
        
        //(Iterable<FinanceDataDBObject>) Arrays.asList(stubbedFinanceData).iterator();
        return returnVal;

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


    private StocksData prepareStocksStubbedData() {
        
        StocksData dummyObject = new StocksData();

        dummyObject.setId(1);
        dummyObject.setStockSymbol("AAPL");
        dummyObject.setPriceUpdatedTime(StocksData.newDate());
        dummyObject.setStockName("Apple");
        dummyObject.setPrice("100");

        return dummyObject;
    }


    private FinanceDataDBObject prepareFinanaceStubbedDataObject() {
        
        FinanceDataDBObject dummyObject = new FinanceDataDBObject();

        dummyObject.setId(1);
        dummyObject.setStockSymbol("AAPL");
        dummyObject.setStockUpdatedTime(FinanceDataDBObject.newDate());
        dummyObject.setFinanceInfoPayload("NA");

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