package com.service.stocksearchanddisplayservice.models;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SimulatedPrice implements Serializable 
{
	@ApiModelProperty(value = "stock symbol")
	private String symbol;
	
	@ApiModelProperty(value = "stock price")
	private String price;
}