package com.elokely.abdalla.stock_analyst_algorithm.data.SQLData.Controller;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface StockDataRepository extends JpaRepository<StockData, Long> {
    List<StockData> findBySymbol(String symbol);
    List<StockData> findBySymbolAndTimestampBetween(
        String symbol, LocalDateTime startDate, LocalDateTime endDate);
}