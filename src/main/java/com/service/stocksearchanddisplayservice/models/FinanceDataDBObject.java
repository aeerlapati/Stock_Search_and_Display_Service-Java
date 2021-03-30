package com.service.stocksearchanddisplayservice.models;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
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
@Table(name = "STOCKS_FINANCE_DATA")
public class FinanceDataDBObject {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(value = "Id")
	Integer Id;

	@Column(name = "STOCK_SYMBOL")
	@JsonProperty("stockSymbol")
	@ApiModelProperty(value = "stockSymbol")
	private String stockSymbol;
	
	@Column(name = "STOCK_UPDATED_TIME")
	@JsonProperty("stockUpdatedTime")
	@ApiModelProperty(value = "stockUpdatedTime")
	@Temporal(TemporalType.TIMESTAMP)
	Date stockUpdatedTime;
	
	@Lob
	@Column(name = "FINANCE_INFO_PAYLOAD")
	@ApiModelProperty(value = "financeInfoPayload")
	private String financeInfoPayload;

	@PrePersist
	protected void onCreate() {
		stockUpdatedTime = new Date();
	}

	@PreUpdate protected void onUpdate() 
	{ 
		stockUpdatedTime = new Date();
	}
	  //stockUpdatedTime = newDate(); }

	public static Date newDate() {
		TimeZone.setDefault(TimeZone.getTimeZone("US/Arizona"));
		return Calendar.getInstance(TimeZone.getTimeZone("US/Arizona")).getTime();
	}
	
}