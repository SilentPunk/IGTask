package com.ig.igtask.api;

import com.ig.igtask.base.exceptions.NotFoundException;
import com.ig.igtask.model.Stock;
import com.ig.igtask.services.StockService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class StockController {
    private StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @GetMapping(
            path = "/stocks",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public List<Stock> getAllStocks() {
        return stockService.getStocks();
    }

    @GetMapping(
            path = "/stock/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Stock getStock(@PathVariable("id") long stockId) throws NotFoundException {
        return stockService.findStockById(stockId);
    }

    @PostMapping(
            path = "/stock",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public void createStock(@RequestBody Stock stock){
        stockService.createStock(stock);
    }

    @DeleteMapping(
            path = "/stock/{id}"
    )
    public void deleteStock(@PathVariable("id") long stockId){
        stockService.deleteStockById(stockId);
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Not found entity for given id")
    public void notFoundException(){
    };

}
