package com.ig.igtask.api;

import com.ig.igtask.base.api.BaseController;
import com.ig.igtask.base.exceptions.base.NotFoundException;
import com.ig.igtask.model.Stock;
import com.ig.igtask.services.StockService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class StockController extends BaseController {
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
    @ResponseStatus(HttpStatus.CREATED)
    public void createStock(@RequestBody @Valid Stock stock){
        stockService.createStock(stock);
    }

    @DeleteMapping(
            path = "/stock/{id}"
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteStock(@PathVariable("id") long stockId){
        stockService.deleteStockById(stockId);
    }


}
