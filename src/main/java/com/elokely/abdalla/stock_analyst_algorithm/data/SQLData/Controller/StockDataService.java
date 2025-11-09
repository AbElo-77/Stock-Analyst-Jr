package com.elokely.abdalla.stock_analyst_algorithm.data.SQLData.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elokely.abdalla.stock_analyst_algorithm.data.SQLData.Controller.Entities.StockData;
import com.elokely.abdalla.stock_analyst_algorithm.data.SQLData.Controller.Repositories.StockDataRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class StockDataService {
    
    private final StockDataRepository stockDataRepository;

    @Autowired
    public StockDataService(StockDataRepository stockDataRepository) {
        this.stockDataRepository = stockDataRepository;
    }

    public StockData saveStockData(StockData stockData) {
        return stockDataRepository.save(stockData);
    }

    public List<StockData> getStockDataBySymbol(String symbol) {
        return stockDataRepository.findBySymbol(symbol);
    }

    public List<StockData> getStockDataBySymbolAndDateRange(
            String symbol, LocalDateTime startDate, LocalDateTime endDate) {
        return stockDataRepository.findBySymbolAndTimestampBetween(symbol, startDate, endDate);
    }

    public List<StockData> getAllStockData() {
        return stockDataRepository.findAll();
    }
}