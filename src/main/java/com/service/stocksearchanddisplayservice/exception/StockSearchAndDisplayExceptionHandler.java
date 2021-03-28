package com.service.stocksearchanddisplayservice.exception;

import static com.service.stocksearchanddisplayservice.util.Constants.ERROR_CODE;
import static com.service.stocksearchanddisplayservice.util.Constants.ERROR_DETAILS;
import static com.service.stocksearchanddisplayservice.util.Constants.ERROR_TYPE;
import static com.service.stocksearchanddisplayservice.util.Constants.LOCATION;

import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.netflix.hystrix.exception.HystrixBadRequestException;
import com.service.stocksearchanddisplayservice.models.ErrorResponse;
import com.service.stocksearchanddisplayservice.util.Utility;

//@ControllerAdvice
public class StockSearchAndDisplayExceptionHandler {

	private static final Logger log = LoggerFactory.getLogger(StockSearchAndDisplayExceptionHandler.class);
	ObjectReader errorResponseReader;
	
	@PostConstruct
	public void init()
	{
		errorResponseReader = new ObjectMapper().readerFor(ErrorResponse.class);
	}
	
	@ExceptionHandler(value = {ServiceException.class})
	public ResponseEntity<ErrorResponse> serviceExceptionHanlder(HttpServletRequest req, Exception e)
	{
		ServiceException serviceException = (ServiceException)e;
		String declinedResponse = Objects.toString(serviceException.getErrorResponse());
		log.error("Service Exception occured: "+ declinedResponse);
		return new ResponseEntity<>(serviceException.getErrorResponse(), serviceException.getHttpStatus());
	}
	
	@ExceptionHandler(value = {HystrixBadRequestException.class})
	public ResponseEntity<ErrorResponse> hystrixBadRequestExceptionHanlder(HttpServletRequest req, Exception e) throws JsonMappingException, JsonProcessingException
	{
		ServiceException serviceException = null;
		if(e instanceof HystrixBadRequestException)
		{
			try 
			{
				ErrorResponse errorResponse =  errorResponseReader.readValue(e.getMessage());
				serviceException = new ServiceException(errorResponse, HttpStatus.BAD_REQUEST);
			}
			catch(Exception ioException)
			{
				serviceException =  new ServiceException(Utility.buildErrorResponse(ERROR_TYPE, ERROR_CODE, ERROR_DETAILS, LOCATION, ioException.getMessage()), HttpStatus.BAD_REQUEST);
			}
			
		}
		else
		{
			serviceException = (ServiceException) e;
		}	
		return new ResponseEntity<>(serviceException.getErrorResponse(), serviceException.getHttpStatus());	
	}
}
