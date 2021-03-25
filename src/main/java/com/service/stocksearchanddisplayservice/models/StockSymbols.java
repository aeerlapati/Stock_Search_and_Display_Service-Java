package com.service.stocksearchanddisplayservice.models;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockSymbols implements Serializable{

    private static final long serialVersionUID = 1L;

    @SerializedName("symbol")
    private String symbol;

    @SerializedName("name")
    private String name;
}