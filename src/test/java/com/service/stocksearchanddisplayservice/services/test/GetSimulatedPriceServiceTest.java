package com.service.stocksearchanddisplayservice.services.test;

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
import com.service.stocksearchanddisplayservice.client.GetSimulatedPriceClient;
import com.service.stocksearchanddisplayservice.models.FinanceDataDBObject;
import com.service.stocksearchanddisplayservice.models.FinancialData;
import com.service.stocksearchanddisplayservice.models.SimulatedPrice;
import com.service.stocksearchanddisplayservice.models.StocksData;
import com.service.stocksearchanddisplayservice.repository.FinanceRepository;
import com.service.stocksearchanddisplayservice.repository.StocksRepository;
import com.service.stocksearchanddisplayservice.services.GetFinancialDataService;
import com.service.stocksearchanddisplayservice.services.GetSimulatedPriceService;
import com.service.stocksearchanddisplayservice.services.GetValidStockSymbolsService;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@AutoConfigureMockMvc
@TestPropertySource(properties = "app.scheduling.enable=false")
public class GetSimulatedPriceServiceTest {

    @Mock
    StocksRepository stocksRepositoryMock;

    @Mock
    FinanceRepository financeRepositoryMock;

    @InjectMocks
    GetFinancialDataService getFiancialDataServiceMock;

    @InjectMocks
    GetSimulatedPriceService getSimulatedPriceServiceMock;

    @InjectMocks
    GetValidStockSymbolsService stockSymbolsService;

    @Mock
    GetFinancialDataClient getFinancialDataClient;

    @Mock
    GetSimulatedPriceClient getSimulatedPriceClient;

    List<FinanceDataDBObject> stockFinancialRecordList;

    List<FinanceDataDBObject> stubbedFinanceData = new ArrayList<>();

    List<StocksData> stubbedStocksData = new ArrayList<>();

    List<StocksData> stubbedStocksDataPriceNA = new ArrayList<>();

    List<FinancialData> stubbedFinancialData = new ArrayList<>();

    List<SimulatedPrice> stubbedSimulatedPriceObj = new ArrayList<>();

    @Test
    void testUpdateStockInfowWithNARecors() {

        StocksData stocksDataSub = new StocksData();

        prepareStocksStubbedDataWithNA();

        when(stocksRepositoryMock.save(any())).thenReturn(stocksDataSub);

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Ratelimit-Remaining", "0");

        when(getSimulatedPriceClient.getSimulatedPrice(anyString(), anyString())).thenReturn(
                new ResponseEntity<SimulatedPrice>(prepareSimulatedPriceDataStub(), headers, HttpStatus.OK));

        when(financeRepositoryMock.findByStockSymbol(anyString())).thenReturn(stubbedFinanceData);

        when(stocksRepositoryMock.findByPrice(any())).thenReturn(stubbedStocksDataPriceNA);

        String expectedStockSymbol = "AAPL";

            try {
                getSimulatedPriceServiceMock.updateSimulatedPrices();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        //assertEquals(expectedStockSymbol, tempResult.get(0).getStockSymbol());

    }

    @Test
    void testUpdateStockInfoWithoutNARecords() {

        StocksData stocksDataSub = new StocksData();

        prepareStocksStubbedDataWithNA();
        
        when(stocksRepositoryMock.save(any())).thenReturn(stocksDataSub);

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Ratelimit-Remaining", "0");

        when(getSimulatedPriceClient.getSimulatedPrice(anyString(), anyString())).thenReturn(
                new ResponseEntity<SimulatedPrice>(prepareSimulatedPriceDataStub(), headers, HttpStatus.OK));

        when(financeRepositoryMock.findByStockSymbol(anyString())).thenReturn(stubbedFinanceData);

        when(stocksRepositoryMock.findByPrice(any())).thenReturn(new ArrayList<StocksData>());

        when(stocksRepositoryMock.findAll()).thenReturn(prepareStocksDataIterable());

        String expectedStockSymbol = "AAPL";

            try {
                getSimulatedPriceServiceMock.updateSimulatedPrices();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        //assertEquals(expectedStockSymbol, tempResult.get(0).getStockSymbol());

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

    private StocksData prepareStocksStubbedDataWithNA() {
        
        StocksData stocksDataObj = new StocksData();

        stocksDataObj.setId(1);
        stocksDataObj.setStockSymbol("AAPL");
        stocksDataObj.setPriceUpdatedTime(StocksData.newDate());
        stocksDataObj.setStockName("Apple");
        stocksDataObj.setPrice("NA");

        stubbedStocksDataPriceNA.add(stocksDataObj);
        return stocksDataObj;
    }


    private SimulatedPrice prepareSimulatedPriceDataStub() {
        
        SimulatedPrice simulateddPrice = new SimulatedPrice();

        simulateddPrice.setPrice("123");
        simulateddPrice.setSymbol("AAPL");

        stubbedSimulatedPriceObj.add(simulateddPrice);

        return simulateddPrice;
    }

}