package com.service.stocksearchanddisplayservice.exception;

import org.springframework.http.HttpStatus;

import com.service.stocksearchanddisplayservice.models.ErrorResponse;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ServiceException extends Exception{

	private HttpStatus httpStatus;
	
	private transient ErrorResponse errorResponse;
	
	public ServiceException()
	{
		
	}
	
	public ServiceException(ErrorResponse errorResponse, HttpStatus httpStatus)
	{
		super();
		this.errorResponse = errorResponse;
		this.httpStatus = httpStatus;
	}
}
