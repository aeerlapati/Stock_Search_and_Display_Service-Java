package com.service.stocksearchanddisplayservice.util;

import com.google.gson.Gson;
import com.service.stocksearchanddisplayservice.models.ErrorResponse;

public final class Utility 
{

	public static ErrorResponse buildErrorResponse(String type, String code, String details, String location, String moreInfo)
	{
		ErrorResponse errorResponse= new ErrorResponse();
		errorResponse.setType(type);
		errorResponse.setCode(code);
		errorResponse.setDetails(details);
		errorResponse.setLocation(location);
		errorResponse.setMoreInfo(moreInfo);
		return errorResponse;
	}
	
	public static String convertObjectToJson(Object object)
	{
		Gson gson = new Gson();
		String jsonInString = gson.toJson(object);
		return jsonInString;
	}
	
}
