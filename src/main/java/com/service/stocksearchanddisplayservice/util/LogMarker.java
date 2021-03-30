package com.service.stocksearchanddisplayservice.util;

import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

public enum LogMarker 
{
	REST_ENTRY(MarkerFactory.getMarker("REST_ENTRY")), REST_EXIT(MarkerFactory.getMarker("REST_EXIT")), 
	SERVICE_ENTRY(MarkerFactory.getMarker("SERVICE_ENTRY")), SERVICE_EXIT(MarkerFactory.getMarker("SERVICE_EXIT")),
	SERVICE_INFO(MarkerFactory.getMarker("SERVICE_INFO")), SERVICE_ERROR(MarkerFactory.getMarker("SERVICE_ERROR")),
	CONTROLLER_ENTRY(MarkerFactory.getMarker("CONTROLLER_ENTRY")), CONTROLLER_EXIT(MarkerFactory.getMarker("CONTROLLER_EXIT"));
	
	private final Marker marker;
	
	private LogMarker(Marker marker)
	{
		this.marker = marker;
	}
	
	public Marker getMarker()
	{
		return marker;
	}
}
