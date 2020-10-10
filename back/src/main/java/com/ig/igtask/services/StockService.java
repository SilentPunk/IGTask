package com.ig.igtask.services;

import com.ig.igtask.base.exceptions.base.NotFoundException;
import com.ig.igtask.base.exceptions.concrete.NotFoundStockException;
import com.ig.igtask.model.Stock;
import com.ig.igtask.repository.StockRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StockService {
    private StockRepository stockRepository;

    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    public List<Stock> getStocks() {
        ArrayList<Stock> allStocks = new ArrayList<>();
        stockRepository
                .findAll()
                .forEach(stock -> allStocks.add(stock));

        return allStocks;
    }

    public void createStock(Stock stock) {
        stockRepository.save(stock);
    }

    public void deleteStockById(long id){
        stockRepository.deleteById(id);
    }

    public Stock findStockById(long id) throws NotFoundException {
        return stockRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundStockException("STOCK-0001", "There is no Stock for given id: " + id));
    }
}
