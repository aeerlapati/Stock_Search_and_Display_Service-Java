package com.service.stocksearchanddisplayservice.models;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import java.sql.Timestamp;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "STOCKS")
public class StocksData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer Id;
    
    @Column(name = "stockname")
    @JsonProperty("stockname")
    String stockName;
	
    @Column(name = "stocksymbol")
    @JsonProperty("stocksymbol")
    String stockSymbol;
	
    @Column(name = "price")
    @JsonProperty("stockprice")
    String price;

    @Column(name = "priceUpdatedTime")
    @JsonProperty("priceupdatedtime")
    Timestamp priceUpdatedTime;
}