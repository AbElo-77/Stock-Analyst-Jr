package com.elokely.abdalla.stock_analyst_algorithm.data.SQLData.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/stocks")
public class StockDataController {

    private final StockDataService stockDataService;

    @Autowired
    public StockDataController(StockDataService stockDataService) {
        this.stockDataService = stockDataService;
    }

    @PostMapping
    public ResponseEntity<StockData> saveStockData(@RequestBody StockData stockData) {
        StockData savedStock = stockDataService.saveStockData(stockData);
        return ResponseEntity.ok(savedStock);
    }

    @GetMapping("/{symbol}")
    public ResponseEntity<List<StockData>> getStocksBySymbol(@PathVariable String symbol) {
        List<StockData> stocks = stockDataService.getStockDataBySymbol(symbol);
        return ResponseEntity.ok(stocks);
    }

    @GetMapping("/{symbol}/range")
    public ResponseEntity<List<StockData>> getStocksByDateRange(
            @PathVariable String symbol,
            @RequestParam LocalDateTime startDate,
            @RequestParam LocalDateTime endDate) {
        List<StockData> stocks = stockDataService.getStockDataBySymbolAndDateRange(symbol, startDate, endDate);
        return ResponseEntity.ok(stocks);
    }
}