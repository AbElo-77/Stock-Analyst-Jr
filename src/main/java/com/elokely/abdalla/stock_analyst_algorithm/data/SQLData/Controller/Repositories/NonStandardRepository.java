package com.elokely.abdalla.stock_analyst_algorithm.data.SQLData.Controller.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.elokely.abdalla.stock_analyst_algorithm.data.SQLData.Controller.Entities.NonStandardData;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface NonStandardRepository extends JpaRepository<NonStandardData, Long> {
    List<NonStandardData> findBySymbol(String symbol);
    List<NonStandardData> findBySymbolAndTimestampBetween(
        String symbol, LocalDateTime startDate, LocalDateTime endDate);
}