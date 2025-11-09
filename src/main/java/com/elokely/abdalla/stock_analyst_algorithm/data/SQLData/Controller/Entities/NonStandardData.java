package com.elokely.abdalla.stock_analyst_algorithm.data.SQLData.Controller.Entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table(name = "non_standard")
public class NonStandardData {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id; 

    private String symbol; 
    private String timetype; 
    private LocalDateTime timestamp; 

    private double vsma; 
    private double obv; 
    private double daily; 

    private double smaShort;
    private double smaLong;
    private double emaShort;
    private double emaLong;

    private double macd;
    private double macdSignal;
    private double macdHist;
    private double adx;
    private double atr;

    private double cpi;
    private double gdp;

    public double getSmaShort() {
        return smaShort;
    }

    public void setSmaShort(double smaShort) {
        this.smaShort = smaShort;
    }

    public double getSmaLong() {
        return smaLong;
    }

    public void setSmaLong(double smaLong) {
        this.smaLong = smaLong;
    }

    public double getEmaShort() {
        return emaShort;
    }

    public void setEmaShort(double emaShort) {
        this.emaShort = emaShort;
    }

    public double getEmaLong() {
        return emaLong;
    }

    public void setEmaLong(double emaLong) {
        this.emaLong = emaLong;
    }

    public double getMacd() {
        return macd;
    }

    public void setMacd(double macd) {
        this.macd = macd;
    }

    public double getMacdSignal() {
        return macdSignal;
    }

    public void setMacdSignal(double macdSignal) {
        this.macdSignal = macdSignal;
    }

    public double getMacdHist() {
        return macdHist;
    }

    public void setMacdHist(double macdHist) {
        this.macdHist = macdHist;
    }

    public double getAdx() {
        return adx;
    }

    public void setAdx(double adx) {
        this.adx = adx;
    }

    public double getAtr() {
        return atr;
    }

    public void setAtr(double atr) {
        this.atr = atr;
    }

    public double getCpi() {
        return cpi;
    }

    public void setCpi(double cpi) {
        this.cpi = cpi;
    }

    public double getGdp() {
        return gdp;
    }

    public void setGdp(double gdp) {
        this.gdp = gdp;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getTimetype() {
        return timetype;
    }

    public void setTimetype(String timetype) {
        this.timetype = timetype;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public double getVsma() {
        return vsma;
    }

    public void setVsma(double vsma) {
        this.vsma = vsma;
    }

    public double getObv() {
        return obv;
    }

    public void setObv(double obv) {
        this.obv = obv;
    }

    public double getDaily() {
        return daily;
    }

    public void setDaily(double daily) {
        this.daily = daily;
    }

}
