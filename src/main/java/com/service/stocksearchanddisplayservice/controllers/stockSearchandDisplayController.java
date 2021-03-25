package com.service.stocksearchanddisplayservice.controllers;

import java.util.List;

import com.service.stocksearchanddisplayservice.models.FinancialData;
import com.service.stocksearchanddisplayservice.models.SimulatedPrice;
import com.service.stocksearchanddisplayservice.models.StockSymbols;
import com.service.stocksearchanddisplayservice.services.GetFiancialDataService;
import com.service.stocksearchanddisplayservice.services.GetSimulatedPriceService;
import com.service.stocksearchanddisplayservice.services.GetValidStockSymbolsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;

@RestController
public class StockSearchandDisplayController {

    @Autowired
    private GetValidStockSymbolsService getValidStockSymbolsService;

    @Autowired
    private GetSimulatedPriceService getSimulatedPriceService;

    @Autowired
    private GetFiancialDataService getFiancialDataService;

    @ApiOperation(value = "", response = ResponseEntity.class)
    @GetMapping(value = "/getsymbols")
    public ResponseEntity<List<StockSymbols>> getStockSymbols() throws Exception {
        // if (mainHeader != "") {
        //     throw new Exception("Invalid Header");
        // }
        List<StockSymbols> response = getValidStockSymbolsService.getValidStockSymbols();

        //log.info("ONLINE SUBMISSION Transaction Successful for "+patientCertification.getFlowName()+" flow");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation(value = "", response = ResponseEntity.class)
    @GetMapping(value = "/getsimualtedprice")
    public ResponseEntity<SimulatedPrice> getSimulatedPrice(@RequestParam(value = "stockSymbol", required = true) String stockSymbol) throws Exception {
        // if (mainHeader != "") {
        //     throw new Exception("Invalid Header");
        // }
       SimulatedPrice simulatedPrice = getSimulatedPriceService.getSimulatedPrices(stockSymbol);

        //log.info("ONLINE SUBMISSION Transaction Successful for "+patientCertification.getFlowName()+" flow");
        return new ResponseEntity<>(simulatedPrice, HttpStatus.OK);
    }

    @ApiOperation(value = "", response = ResponseEntity.class)
    @GetMapping(value = "/getfinancialdata")
    public ResponseEntity<List<FinancialData>> getfinancialdata(@RequestParam(value = "stockSymbol", required = true) String stockSymbol) throws Exception {
        // if (mainHeader != "") {
        //     throw new Exception("Invalid Header");
        // }
       List<FinancialData> financialData = getFiancialDataService.getFinancialData(stockSymbol);

        //log.info("ONLINE SUBMISSION Transaction Successful for "+patientCertification.getFlowName()+" flow");
        return new ResponseEntity<>(financialData, HttpStatus.OK);
    }

    
}