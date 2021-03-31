package com.service.stocksearchanddisplayservice.services.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.TestPropertySource;

import com.google.common.collect.Iterables;
import com.service.stocksearchanddisplayservice.client.GetFinancialDataClient;
import com.service.stocksearchanddisplayservice.client.GetSimulatedPriceClient;
import com.service.stocksearchanddisplayservice.client.GetSymbolsClient;
import com.service.stocksearchanddisplayservice.models.FinanceDataDBObject;
import com.service.stocksearchanddisplayservice.models.FinancialData;
import com.service.stocksearchanddisplayservice.models.SimulatedPrice;
import com.service.stocksearchanddisplayservice.models.StockSymbols;
import com.service.stocksearchanddisplayservice.models.StocksData;
import com.service.stocksearchanddisplayservice.repository.FinanceRepository;
import com.service.stocksearchanddisplayservice.repository.StocksRepository;
import com.service.stocksearchanddisplayservice.services.GetFinancialDataService;
import com.service.stocksearchanddisplayservice.services.GetSimulatedPriceService;
import com.service.stocksearchanddisplayservice.services.GetValidStockSymbolsService;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(properties = "app.scheduling.enable=false")
public class GetValidStockSymbolsServiceTest {

    @Mock
    StocksRepository stocksRepositoryMock;

    @Mock
    FinanceRepository financeRepositoryMock;

    @InjectMocks
    GetFinancialDataService getFiancialDataServiceMock;

    @InjectMocks
    GetSimulatedPriceService getSimulatedPriceServiceMock;

    @InjectMocks
    GetValidStockSymbolsService stockSymbolsServiceMock;

    @Mock
    GetFinancialDataClient getFinancialDataClient;

    @Mock
    GetSimulatedPriceClient getSimulatedPriceClientMock;

    @Mock
    GetSymbolsClient getSymbolsClientMock;

    List<FinanceDataDBObject> stockFinancialRecordList;

    List<FinanceDataDBObject> stubbedFinanceData = new ArrayList<>();

    List<StocksData> stubbedStocksDataList = new ArrayList<>();

    List<StocksData> stubbedStocksDataPriceNA = new ArrayList<>();

    List<FinancialData> stubbedFinancialData = new ArrayList<>();

    List<SimulatedPrice> stubbedSimulatedPriceObj = new ArrayList<>();

    List<StockSymbols> stubbedStockSymbolsData = new ArrayList<>();

    @Test
    void testgetStockSymbolsFromDB() {

        prepareStocksStubbedDataWithNA();

        when(stocksRepositoryMock.findAll()).thenReturn(prepareStocksDataIterable());

        String expectedStockSymbol = "AAPL";

        Iterable<StocksData> result = null;

            try {
                result = stockSymbolsServiceMock.getStockSymbolsFromDB();

            }catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        assertEquals(expectedStockSymbol, Iterables.get(result, 0).getStockSymbol());

    }

    @Test
    void testgetValidStockSymbols() {

        prepareSymbolsData();

        StocksData stocksDataSub = new StocksData();

        prepareStocksStubbedDataWithNA();
        
        when(stocksRepositoryMock.findAll()).thenReturn(null);

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Ratelimit-Remaining", "0");

        when(getSymbolsClientMock.stockSymbolsClient(anyString())).thenReturn(stubbedStockSymbolsData);

        when(stocksRepositoryMock.save(any())).thenReturn(stocksDataSub);

        String expectedStockSymbol = "AAPL";

        Iterable<StocksData> result = null;

            try {
                result = stockSymbolsServiceMock.getValidStockSymbols();
             } catch (Exception e) {
                e.printStackTrace();
            }
        //assertEquals(expectedStockSymbol, tempResult.get(0).getStockSymbol());
    }


    private Iterable<StocksData> prepareStocksDataIterable() {
        
        StocksData dummyObject = new StocksData();

        dummyObject.setId(1);
        dummyObject.setStockSymbol("AAPL");
        dummyObject.setPriceUpdatedTime(StocksData.newDate());
        dummyObject.setStockName("Apple");
        dummyObject.setPrice("100");

        stubbedStocksDataList.add(dummyObject);

        Iterable<StocksData> returnVal = Iterable.class.cast(stubbedStocksDataList);
        return returnVal;

    }

    private StockSymbols prepareSymbolsData() {
        
        StockSymbols dummyObject = new StockSymbols();

        dummyObject.setName("Apple");
        dummyObject.setSymbol("Apple");

        stubbedStockSymbolsData.add(dummyObject);

        return dummyObject;
    }

    private StocksData prepareStocksStubbedDataWithNA() {
        
        StocksData dummyObject = new StocksData();

        dummyObject.setId(1);
        dummyObject.setStockSymbol("AAPL");
        dummyObject.setPriceUpdatedTime(StocksData.newDate());
        dummyObject.setStockName("Apple");
        dummyObject.setPrice("NA");

        stubbedStocksDataPriceNA.add(dummyObject);
        return dummyObject;
    }

}