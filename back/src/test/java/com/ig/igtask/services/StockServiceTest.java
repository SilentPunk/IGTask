package com.ig.igtask.services;

import com.ig.igtask.base.exceptions.base.NotFoundException;
import com.ig.igtask.base.exceptions.concrete.NotFoundStockException;
import com.ig.igtask.model.Stock;
import com.ig.igtask.repository.StockRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class StockServiceTest {
    //tested resource
    private StockService stockService;

    //mocks
    private StockRepository stockRepository;


    @BeforeEach
    public void setup() {
        this.stockRepository = Mockito.mock(StockRepository.class);

        this.stockService = new StockService(this.stockRepository);
    }

    @ParameterizedTest
    @MethodSource("getStocksProvider")
    public void getStocks(List<Stock> providedStockList) {
        //given
        Mockito.when(this.stockRepository.findAll()).thenReturn(providedStockList);

        //when
        List<Stock> result = this.stockService.getStocks();

        //then
        Assertions
                .assertThat(result)
                .isEqualTo(providedStockList);
    }

    private static Stream<Arguments> getStocksProvider() {
        return Stream.of(
                Arguments.of(new ArrayList<Stock>()),
                Arguments.of(new ArrayList<Stock>() {
                    {
                        add(new Stock());
                    }
                })
        );
    }

    @ParameterizedTest
    @MethodSource("stockProvider")
    public void createStock(Stock givenStock) {
        //when
        this.stockService.createStock(givenStock);

        //then
        Mockito.verify(this.stockRepository, Mockito.times(1))
                .save(givenStock);
    }

    private static Stream<Arguments> stockProvider() {
        return Stream.of(
                Arguments.of(new Stock()),
                Arguments.of(new Stock("test")),
                Arguments.of(new Stock("someFancyName"))
        );
    }

    @ParameterizedTest
    @CsvSource({"1", "22", "354", "231234"})
    public void createStock(long givenId) {
        //when
        this.stockService.deleteStockById(givenId);

        //then
        Mockito.verify(this.stockRepository, Mockito.times(1))
                .deleteById(givenId);
    }

    @ParameterizedTest
    @MethodSource("idAndStockProvider")
    public void findStockByIdReturnsStock(long givenId, Stock givenStock) throws NotFoundException {
        //given
        Mockito
                .when(this.stockRepository.findById(givenId))
                .thenReturn(Optional.of(givenStock));

        //when
        Stock stockResult = this.stockService.findStockById(givenId);

        //then
        Assertions
                .assertThat(stockResult)
                .isEqualTo(givenStock);

        Mockito.verify(this.stockRepository, Mockito.times(1))
                .findById(givenId);

    }

    private static Stream<Arguments> idAndStockProvider() {
        return Stream.of(
                Arguments.of(1, new Stock()),
                Arguments.of(123, new Stock("test")),
                Arguments.of(125, new Stock("funnyName"))
        );
    }

    @ParameterizedTest
    @CsvSource({"1", "22", "354", "231234"})
    public void findStockByIdReturnsNotFoundException(long givenId) throws NotFoundException {
        //given
        Mockito
                .when(this.stockRepository.findById(givenId))
                .thenReturn(Optional.empty());

        //when
        Assertions
                .assertThatThrownBy(() -> this.stockService.findStockById(givenId))
                .isInstanceOf(NotFoundException.class);

        //then
        Mockito.verify(this.stockRepository, Mockito.times(1))
                .findById(givenId);

    }



}
