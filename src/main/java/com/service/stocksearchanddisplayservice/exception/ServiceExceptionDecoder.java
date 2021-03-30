package com.service.stocksearchanddisplayservice.exception;

import static com.service.stocksearchanddisplayservice.util.Constants.ERROR_CODE;
import static com.service.stocksearchanddisplayservice.util.Constants.ERROR_DETAILS;
import static com.service.stocksearchanddisplayservice.util.Constants.ERROR_TYPE;
import static com.service.stocksearchanddisplayservice.util.Constants.LOCATION;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import com.netflix.hystrix.exception.HystrixBadRequestException;
import com.service.stocksearchanddisplayservice.util.Utility;

import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;

public class ServiceExceptionDecoder implements ErrorDecoder
{
	private static final Logger log = LoggerFactory.getLogger(ServiceExceptionDecoder.class);
	
	private ErrorDecoder delegate = new ErrorDecoder.Default();
	@Override
	public Exception decode(String methodKey, Response response) 
	{
		if(response.status() >= 400 && response.status() <= 599)
		{
			log.error("Enter decoder logic");
			if(response.status() == 429)
			{
				byte[] bodyData;
				try {
					bodyData = Util.toByteArray(response.body().asInputStream());
					String responseBody = new String(bodyData);
					System.out.println("It's a 429 error with data: "+ responseBody);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}	
			try
			{
				byte[] bodyData = Util.toByteArray(response.body().asInputStream());
				String responseBody = new String(bodyData);
				return new HystrixBadRequestException(responseBody);
			}
			catch(IOException e)
			{
				return new ServiceException(Utility.buildErrorResponse(ERROR_TYPE, ERROR_CODE, ERROR_DETAILS, LOCATION, e.getLocalizedMessage()), HttpStatus.BAD_REQUEST);
			}
		}	
		return delegate.decode(methodKey, response);
	}

}
