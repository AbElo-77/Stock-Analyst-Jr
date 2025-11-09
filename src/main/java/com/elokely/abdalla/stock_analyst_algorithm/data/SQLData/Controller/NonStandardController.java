 package com.elokely.abdalla.stock_analyst_algorithm.data.SQLData.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.elokely.abdalla.stock_analyst_algorithm.data.SQLData.Controller.Entities.NonStandardData;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/stocks")
public class NonStandardController {

    private final NonStandardService nonStandardService;

    @Autowired
    public NonStandardController(NonStandardService nonStandardService) {
        this.nonStandardService = nonStandardService;
    }

    @PostMapping
    public ResponseEntity<NonStandardData> saveStockData(@RequestBody NonStandardData nonStandard) {
        NonStandardData savedStock = nonStandardService.saveStockData(nonStandard);
        return ResponseEntity.ok(savedStock);
    }

    @GetMapping("/{symbol}")
    public ResponseEntity<List<NonStandardData>> getStocksBySymbol(@PathVariable String symbol) {
        List<NonStandardData> stocks = nonStandardService.getStockDataBySymbol(symbol);
        return ResponseEntity.ok(stocks);
    }

    @GetMapping("/{symbol}/range")
    public ResponseEntity<List<NonStandardData>> getStocksByDateRange(
            @PathVariable String symbol,
            @RequestParam LocalDateTime startDate,
            @RequestParam LocalDateTime endDate) {
        List<NonStandardData> stocks = nonStandardService.getStockDataBySymbolAndDateRange(symbol, startDate, endDate);
        return ResponseEntity.ok(stocks);
    }
}