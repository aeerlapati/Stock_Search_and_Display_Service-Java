package com.service.stocksearchanddisplayservice.repository;

import java.util.List;

import com.service.stocksearchanddisplayservice.models.FinanceDataDBObject;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FinanceRepository extends CrudRepository<FinanceDataDBObject, Integer> {

    List<FinanceDataDBObject> findByStocksymbol(String stocksymbol);
    
}