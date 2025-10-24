package com.elokely.abdalla.stock_analyst_algorithm.data.SQLData.Controller;

import jakarta.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table(name = "stock_data")
public class StockData {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id; 

    private String symbol; 
    private LocalDateTime timestamp;
    private int high; 
    private int low; 
    private int open; 
    private int close; 
    private long volume; 

    public long getId() {
        return id;
    }

    public String getSymbol() {
        return symbol;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public int getHigh() {
        return high;
    }

    public int getLow() {
        return low;
    }

    public int getOpen() {
        return open;
    }

    public int getClose() {
        return close;
    }

    public long getVolume() {
        return volume;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public void setHigh(int high) {
        this.high = high;
    }

    public void setLow(int low) {
        this.low = low;
    }

    public void setOpen(int open) {
        this.open = open;
    }

    public void setClose(int close) {
        this.close = close;
    }

    public void setVolume(long volume) {
        this.volume = volume;
    }
}
