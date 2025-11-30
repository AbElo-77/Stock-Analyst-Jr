package com.elokely.abdalla.stock_analyst_algorithm.backend.algorithmicProcessing.prePostProcessing;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.elokely.abdalla.stock_analyst_algorithm.data.SQLData.Controller.Entities.*;
import com.elokely.abdalla.stock_analyst_algorithm.data.SQLData.NonStandardService;
import com.elokely.abdalla.stock_analyst_algorithm.data.SQLData.StockDataService;

public class FeatureAssembly {
    
    private final StockDataService stockDataService; 
    private final NonStandardService nonStandardService; 
    private final ArrayList<FeatureSamples> featureSamples = new ArrayList<>(); 
    private static final String API_SYMBOL = "AMZN";

    public FeatureAssembly(StockDataService stockDataService, NonStandardService nonStandardService) {

        this.stockDataService = stockDataService; 
        this.nonStandardService = nonStandardService; 
    }

    public ArrayList<FeatureSamples> getSamples() {
        
        return featureSamples;
    }

    public void assembleFeatures() {

        LocalDate startDate = LocalDate.of(2000, 1, 1); 
        LocalDate endDate = LocalDate.of(2025, 12, 31); 
        
        for (LocalDate curDate = startDate; curDate.isBefore(endDate); curDate = curDate.plusDays(1)) {

            featureSamples.add(new FeatureSamples(createDaily(API_SYMBOL, curDate), 
                                                  createWeekly(API_SYMBOL, curDate.minusDays(7), curDate),
                                                  createIntraday(API_SYMBOL, curDate))); 
        }
    }
    
    public double[][] createIntraday(String API, LocalDate timestamp) {

        List<StockData> intradayData = stockDataService.getStockDataBySymbolAndDateRange(API, timestamp, timestamp); 
        int rows = intradayData.size();
        double[][] intradayFeatures = new double[rows][14];

        if (intradayData.isEmpty()) {
            return intradayFeatures; 
        }

        int curRecord = 0; 
        for (StockData stockData : intradayData) {
            
            intradayFeatures[curRecord][0] = stockData.getHigh(); 
            intradayFeatures[curRecord][1] = stockData.getLow();
            intradayFeatures[curRecord][2] = stockData.getOpen();
            intradayFeatures[curRecord][3] = stockData.getClose();
            intradayFeatures[curRecord][4] = stockData.getVolume();
            intradayFeatures[curRecord][5] = stockData.getVwap();

            intradayFeatures[curRecord][6] = stockData.getStochK();
            intradayFeatures[curRecord][7] = stockData.getStochD();
            intradayFeatures[curRecord][8] = stockData.getRsi();
            intradayFeatures[curRecord][9] = stockData.getAroonUp();
            intradayFeatures[curRecord][10] = stockData.getAroonDown();
            intradayFeatures[curRecord][11] = stockData.getBbandsLower();
            intradayFeatures[curRecord][12] = stockData.getBbandsMid();
            intradayFeatures[curRecord][13] = stockData.getBbandsUpper();

            curRecord++;
        }

        return intradayFeatures; 
    }
    
    public double[] createDaily(String API, LocalDate timestamp) {

        List<NonStandardData> dayData = nonStandardService.getStockDataBySymbolAndDateRange(API, timestamp, timestamp); 
        dayData = dayData.stream()
                         .filter(item -> "daily".equalsIgnoreCase(item.getTimetype()))
                         .collect(Collectors.toList()); 
        double[] dailyFeatures = new double[5]; 

        if (dayData.isEmpty()) {
            return dailyFeatures; 
        }

        dailyFeatures[0] = dayData.get(0).getObv();
        dailyFeatures[1] = dayData.get(0).getDaily();  
        dailyFeatures[2] = dayData.get(0).getSmaShort(); 
        dailyFeatures[3] = dayData.get(0).getEmaShort();
        dailyFeatures[4] = dayData.get(0).getAdx();  

        return dailyFeatures;
    }

    public double[] createWeekly(String API, LocalDate startDate, LocalDate endDate) {

        List<NonStandardData> weekData = nonStandardService.getStockDataBySymbolAndDateRange(API, startDate, endDate); 
        List<NonStandardData> onlyWeekData = weekData.stream()
                                                     .filter(item -> "weekly".equalsIgnoreCase(item.getTimetype()))
                                                     .collect(Collectors.toList()); 
        List<NonStandardData> monthlyData = weekData.stream()
                                                    .filter(item -> "monthly".equalsIgnoreCase(item.getTimetype()))
                                                    .collect(Collectors.toList());
        double[] weeklyFeatures = new double[10]; 

        if (onlyWeekData.isEmpty()) {
            return weeklyFeatures;
        }

        weeklyFeatures[0] = onlyWeekData.get(0).getSmaLong(); 
        weeklyFeatures[1] = onlyWeekData.get(0).getEmaLong();
        weeklyFeatures[2] = onlyWeekData.get(0).getMacd();
        weeklyFeatures[3] = onlyWeekData.get(0).getMacdSignal();
        weeklyFeatures[4] = onlyWeekData.get(0).getMacdHist();

 
        LocalDate lastMonthDate = endDate.minusMonths(1); 

        List<NonStandardData> lastMonthlyData = nonStandardService.getStockDataBySymbolAndDateRange(API, lastMonthDate, endDate); 
        lastMonthlyData = lastMonthlyData.stream()
                                         .filter(item -> "monthly".equalsIgnoreCase(item.getTimetype()))
                                         .collect(Collectors.toList());
        if (monthlyData.isEmpty() || lastMonthlyData.isEmpty()) {
            weeklyFeatures[5] = 0.0;
        } else {
            double currentCPI = monthlyData.get(0).getCpi();
            double lastCPI = lastMonthlyData.get(0).getCpi(); 
            double averageCPI = currentCPI * (monthlyData.get(0).getTimestamp().getDayOfMonth() / 15.0) 
                                + lastCPI * (30 - monthlyData.get(0).getTimestamp().getDayOfMonth() / 60.0); 
            weeklyFeatures[5] = averageCPI;
        }
        
        List<NonStandardData> quarterlyData; 
        if (endDate.getMonthValue() <= 3) {

            quarterlyData = nonStandardService.getStockDataBySymbolAndDateRange(API, 
                                                                                LocalDate.of(endDate.getYear(), 
                                                                                             1, 
                                                                                             1), 
                                                                                endDate);
            quarterlyData = quarterlyData.stream()
                                         .filter(item -> "quarterly".equalsIgnoreCase(item.getTimetype()))
                                         .collect(Collectors.toList()); 
        } else if (endDate.getMonthValue() <= 6) {

            quarterlyData = nonStandardService.getStockDataBySymbolAndDateRange(API, 
                                                                                LocalDate.of(endDate.getYear(), 
                                                                                             4, 
                                                                                             1), 
                                                                                endDate);
            quarterlyData = quarterlyData.stream()
                                         .filter(item -> "quarterly".equalsIgnoreCase(item.getTimetype()))
                                         .collect(Collectors.toList()); 
        } else if (endDate.getMonthValue() <= 9) {

            quarterlyData = nonStandardService.getStockDataBySymbolAndDateRange(API, 
                                                                                LocalDate.of(endDate.getYear(), 
                                                                                             7, 
                                                                                             1), 
                                                                                endDate);
            quarterlyData = quarterlyData.stream()
                                         .filter(item -> "quarterly".equalsIgnoreCase(item.getTimetype()))
                                         .collect(Collectors.toList()); 
        } else {

            quarterlyData = nonStandardService.getStockDataBySymbolAndDateRange(API, 
                                                                                LocalDate.of(endDate.getYear(), 
                                                                                             10, 
                                                                                             1), 
                                                                                endDate);
            quarterlyData = quarterlyData.stream()
                                         .filter(item -> "quarterly".equalsIgnoreCase(item.getTimetype()))
                                         .collect(Collectors.toList()); 
        }

        if (quarterlyData.isEmpty()) {
            weeklyFeatures[6] = 0.0;
        } else {
            weeklyFeatures[6] = quarterlyData.get(0).getGdp();
        }

        return weeklyFeatures; 
        
    }

}

