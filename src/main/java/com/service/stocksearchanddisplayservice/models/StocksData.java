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