package com.service.stocksearchanddisplayservice.models;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "STOCKS")
public class StocksData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "id")
	Integer Id;
    
    @Column(name = "STOCK_NAME")
    @JsonProperty("stockName")
    @ApiModelProperty(value = "stockName")
    String stockName;
	
    @Column(name = "STOCK_SYMBOL")
    @JsonProperty("stockSymbol")
    @ApiModelProperty(value = "stockSymbol")
    String stockSymbol;
	
    @Column(name = "PRICE")
    @JsonProperty("stockPrice")
    @ApiModelProperty(value = "stockPrice")
    String price;

    @Column(name = "PRICE_UPDATED_TIME")
    @JsonProperty("priceUpdatedTime")
    @ApiModelProperty(value = "priceUpdatedTime")
    @Temporal(TemporalType.TIMESTAMP)
    Date priceUpdatedTime;
    
    @PrePersist
    protected void onCreate()
    {
    	priceUpdatedTime = new Date();
    }
    
    @PreUpdate
    protected void onUpdate()
    {
    	priceUpdatedTime = newDate();
    }
    
    public static Date newDate()
    {
    	TimeZone.setDefault(TimeZone.getTimeZone("US/Arizona"));
    	return Calendar.getInstance(TimeZone.getTimeZone("US/Arizona")).getTime();
    }

}