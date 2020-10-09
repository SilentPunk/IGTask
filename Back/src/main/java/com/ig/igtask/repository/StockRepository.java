package com.ig.igtask.repository;

import com.ig.igtask.model.Stock;
import org.springframework.data.repository.CrudRepository;

public interface StockRepository extends CrudRepository<Stock, Long> {
}
