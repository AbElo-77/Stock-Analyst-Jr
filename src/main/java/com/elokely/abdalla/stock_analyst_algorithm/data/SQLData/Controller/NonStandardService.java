package com.elokely.abdalla.stock_analyst_algorithm.data.SQLData.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elokely.abdalla.stock_analyst_algorithm.data.SQLData.Controller.Entities.NonStandardData;
import com.elokely.abdalla.stock_analyst_algorithm.data.SQLData.Controller.Repositories.NonStandardRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NonStandardService {
    
    private final NonStandardRepository nonStandardRepository;

    @Autowired
    public NonStandardService(NonStandardRepository nonStandardRepository) {
        this.nonStandardRepository = nonStandardRepository;
    }

    public NonStandardData saveStockData(NonStandardData nonStandard) {
        return nonStandardRepository.save(nonStandard);
    }

    public List<NonStandardData> getStockDataBySymbol(String symbol) {
        return nonStandardRepository.findBySymbol(symbol);
    }

    public List<NonStandardData> getStockDataBySymbolAndDateRange(
            String symbol, LocalDateTime startDate, LocalDateTime endDate) {
        return nonStandardRepository.findBySymbolAndTimestampBetween(symbol, startDate, endDate);
    }

    public List<NonStandardData> getAllStockData() {
        return nonStandardRepository.findAll();
    }
}