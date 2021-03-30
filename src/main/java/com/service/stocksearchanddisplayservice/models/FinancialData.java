package com.service.stocksearchanddisplayservice.models;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FinancialData implements Serializable {
    private String stockSymbol;
    private String date;
    private String currency;
    private String revenue;
    private String gross_profit;
    private String income_before_tax;
    private String ebitda;
    private String net_income;
    private String epsl;
    private String weighted_shares_outstanding;
    private String long_term_debt;
    private String free_cash_flow;
    private String captial_expenditure;
    private String sec_link;

}