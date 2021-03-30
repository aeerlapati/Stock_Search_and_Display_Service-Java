package com.service.stocksearchanddisplayservice.repository;

import java.util.List;

import com.service.stocksearchanddisplayservice.models.StocksData;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StocksRepository extends CrudRepository<StocksData, Integer> {
    

    List<StocksData> findByPrice(String price);
    
}