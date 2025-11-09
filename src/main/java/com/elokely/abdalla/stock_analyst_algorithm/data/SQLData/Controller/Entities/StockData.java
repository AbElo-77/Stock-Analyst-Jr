package com.elokely.abdalla.stock_analyst_algorithm.data.SQLData.Controller.Entities;

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
    private double vwap;  

    private double stochK;  
    private double stochD;  
    private double rsi;
    private double aroonUp;
    private double aroonDown;
    private double bbandsUpper;
    private double bbandsMid;
    private double bbandsLower;

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

    public double getVwap() {
        return vwap;
    }

    public double getStochK() {
        return stochK;
    }

    public double getStochD() {
        return stochD;
    }

    public double getRsi() {
        return rsi;
    }

    public double getAroonUp() {
        return aroonUp;
    }

    public double getAroonDown() {
        return aroonDown;
    }

    public double getBbandsUpper() {
        return bbandsUpper;
    }

    public double getBbandsMid() {
        return bbandsMid;
    }

    public double getBbandsLower() {
        return bbandsLower;
    }

    public void setVwap(double vwap) {
        this.vwap = vwap;
    }

    public void setStochK(double stochK) {
        this.stochK = stochK;
    }

    public void setStochD(double stochD) {
        this.stochD = stochD;
    }

    public void setRsi(double rsi) {
        this.rsi = rsi;
    }

    public void setAroonUp(double aroonUp) {
        this.aroonUp = aroonUp;
    }

    public void setAroonDown(double aroonDown) {
        this.aroonDown = aroonDown;
    }

    public void setBbandsUpper(double bbandsUpper) {
        this.bbandsUpper = bbandsUpper;
    }

    public void setBbandsMid(double bbandsMid) {
        this.bbandsMid = bbandsMid;
    }

    public void setBbandsLower(double bbandsLower) {
        this.bbandsLower = bbandsLower;
    }
}
