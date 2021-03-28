package com.service.stocksearchanddisplayservice.util;

public final class Constants {

    public static final String CLIENT_URI = "https://hiring-project-307416.uk.r.appspot.com";
    public static final String API_KEY = "api-key";
    public static final String PATH_VARIABLE_SYMBOL = "symbol";



    public static final String STOCK_SYMBOL_CLIENT_NAME = "STOCK-SYMBOL-CLIENT";
    public static final String STOCK_SYMBOL_CLIENT_URI = "/api/v1/symbols";
    
    public static final String FINANCIAL_DATA_CLIENT_NAME = "FINANCIAL-DATA-CLIENT";
    public static final String FINANCIAL_DATA_CLIENT_URI = "/api/v1/data/{symbol}";

    public static final String SIMULATED_PRICE_CLIENT_NAME = "SIMULATED-PRICE-CLIENT";
    public static final String SIMULATED_PRICE_CLIENT_URI = "/api/v1/price/{symbol}";
  
    
    public static final String ERROR_TYPE = "ERROR";
    public static final String ERROR_CODE = "-1";
    public static final String ERROR_DETAILS = "Exception occured in deserializing Stock Service response";
    public static final String LOCATION = "Stock Seach & Display Service";
    //public static final String API_KEY = "api-key";
    
}