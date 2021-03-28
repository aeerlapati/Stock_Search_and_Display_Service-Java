package com.service.stocksearchanddisplayservice.models;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SimulatedPrice implements Serializable 
{
	private String symbol;
	private String price;
}