package com.ig.igtask.repository;

import com.ig.igtask.model.Stock;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface StockRepository extends CrudRepository<Stock, Long> {

    Optional<Stock> findByStockName(String stockName);

}
