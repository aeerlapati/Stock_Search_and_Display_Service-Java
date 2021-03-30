package com.service.stocksearchanddisplayservice.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.service.stocksearchanddisplayservice.models.FinanceDataDBObject;
import com.service.stocksearchanddisplayservice.models.SimulatedPrice;
import com.service.stocksearchanddisplayservice.models.StocksData;
import com.service.stocksearchanddisplayservice.services.GetFiancialDataService;
import com.service.stocksearchanddisplayservice.services.GetSimulatedPriceService;
import com.service.stocksearchanddisplayservice.services.GetValidStockSymbolsService;
import com.service.stocksearchanddisplayservice.util.LogMarker;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;

@RestController
@RequestMapping("/api/v1")
@Api(value = "/service", tags = "Stock Seacrh & Display Services")
@SwaggerDefinition(tags = {@Tag (name = "Stock Seacrh & Display Services", description = "All API(s) related to fetch and displat stock information")})
public class StockSearchAndDisplayController 
{

	private static final Logger log = LoggerFactory.getLogger(StockSearchAndDisplayController.class);

	@Autowired
	private GetValidStockSymbolsService getValidStockSymbolsService;

	@Autowired
	private GetSimulatedPriceService getSimulatedPriceService;

	@Autowired
	private GetFiancialDataService getFiancialDataService;

	@ApiOperation(value = "Get Stock Symbols Info", response = ResponseEntity.class)
	@CrossOrigin({ "http://localhost:8081", "http://stocksearchanddisplay-react.s3-us-west-1.amazonaws.com",
			"https://stocksearchanddisplay-react.s3-us-west-1.amazonaws.com" })
	@GetMapping(value = "/getSymbols")
	public ResponseEntity<Iterable<StocksData>> getStockSymbols() throws Exception 
	{
		log.info(LogMarker.CONTROLLER_ENTRY.getMarker(), "getStockSymbols: call started");
		Iterable<StocksData> response = getValidStockSymbolsService.getStockSymbolsFromDB();
		log.info(LogMarker.CONTROLLER_EXIT.getMarker(), "getStockSymbols: call ended");
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@ApiOperation(value = "Get Stock Finanacial Data", response = ResponseEntity.class)
	@CrossOrigin({ "http://localhost:8081", "http://stocksearchanddisplay-react.s3-us-west-1.amazonaws.com",
			"https://stocksearchanddisplay-react.s3-us-west-1.amazonaws.com" })
	@GetMapping(value = "/getFinancialData")
	public ResponseEntity<List<FinanceDataDBObject>> getFinancialData(
			@ApiParam(value = "stock symbol", required = true) @RequestParam(value = "stockSymbol", required = true) String stockSymbol) throws Exception 
	{
		log.info(LogMarker.CONTROLLER_ENTRY.getMarker(), "getFinancialData: call started for stock symbol: ",
				stockSymbol);
		List<FinanceDataDBObject> financialData = getFiancialDataService.getFinancialDataFromDB(stockSymbol);
		log.info(LogMarker.CONTROLLER_EXIT.getMarker(), "getFinancialData: call ended with financial data: {}",
				financialData);
		return new ResponseEntity<>(financialData, HttpStatus.OK);
	}

	// @ApiOperation(value = "Get Simulated Price", response = ResponseEntity.class)
	@CrossOrigin({ "http://localhost:8081", "http://stocksearchanddisplay-react.s3-us-west-1.amazonaws.com",
			"https://stocksearchanddisplay-react.s3-us-west-1.amazonaws.com" })
	@GetMapping(value = "/simualtedPrice")
	public ResponseEntity<SimulatedPrice> getSimulatedPrice(@ApiParam(value = "stock symbol", required = true) @RequestParam(value = "stockSymbol", required = true) String stockSymbol) throws Exception 
	{
		log.info(LogMarker.CONTROLLER_ENTRY.getMarker(), "getSimulatedPrice: call started");
		ResponseEntity<SimulatedPrice> simulatedPrice = getSimulatedPriceService.getSimulatedPrices(stockSymbol);
		log.info(LogMarker.CONTROLLER_EXIT.getMarker(), "getSimulatedPrice: call ended");
		return new ResponseEntity<>(simulatedPrice.getBody(), HttpStatus.OK);
	}

	//@ApiOperation(value = "Update Stock Prices", response = ResponseEntity.class)
	@GetMapping(value = "/updateStockPrices")
	@CrossOrigin({ "http://localhost:8081", "http://stocksearchanddisplay-react.s3-us-west-1.amazonaws.com", "https://stocksearchanddisplay-react.s3-us-west-1.amazonaws.com"})
	public ResponseEntity<SimulatedPrice> updateStockPrices() throws Exception
	{
		log.info(LogMarker.CONTROLLER_ENTRY.getMarker(), "updateStockPrices: call started");
		SimulatedPrice simulatedPrice = new SimulatedPrice();
		getSimulatedPriceService.updateSimulatedPrices();
		log.info(LogMarker.CONTROLLER_EXIT.getMarker(), "updateStockPrices: call ended");
		return new ResponseEntity<>(simulatedPrice, HttpStatus.OK);
	}

	@ApiOperation(value = "Default Method", response = ResponseEntity.class)
	@CrossOrigin({ "http://localhost:8081", "http://stocksearchanddisplay-react.s3-us-west-1.amazonaws.com", "https://stocksearchanddisplay-react.s3-us-west-1.amazonaws.com"})
	@GetMapping(value = "/")
	public String defaultMethod() throws Exception {
		return "Connection Successfull";
	}

}