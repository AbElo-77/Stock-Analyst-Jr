package com.elokely.abdalla.stock_analyst_algorithm.backend.algorithmicProcessing.prePostProcessing;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.elokely.abdalla.stock_analyst_algorithm.data.SQLData.Controller.Entities.*;
import com.elokely.abdalla.stock_analyst_algorithm.data.SQLData.NonStandardService;
import com.elokely.abdalla.stock_analyst_algorithm.data.SQLData.StockDataService;
import com.elokely.abdalla.stock_analyst_algorithm.backend.algorithmicProcessing.prePostProcessing.FeatureSamples;

public class FeatureAssembly {
    
    private final StockDataService stockDataService; 
    private final NonStandardService nonStandardService; 
    private static ArrayList<FeatureSamples> featureSamples = new ArrayList<>(); 
    private static final String API_SYMBOL = "AMZN";

    public FeatureAssembly(StockDataService stockDataService, NonStandardService nonStandardService) {

        this.stockDataService = stockDataService; 
        this.nonStandardService = nonStandardService; 
    }

    public ArrayList<FeatureSamples> getSamples() {

        return featureSamples;
    }

    public void assembleFeatures() {

        int curMonth = 1; 
        int curDay = 1; 
        int yearCounter = 2025; 
        
        FeatureSamples curSample = new FeatureSamples();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 12; j++) {
                for (int k = 0; k < 30; k++) {

                    LocalDate curTime = LocalDate.of(yearCounter, curMonth, curDay); 
                    LocalDate weekAgo = LocalDate.of(yearCounter, curMonth, curDay - 7); 
                    
                    curSample.setIntradayFeatures(createIntraday(API_SYMBOL, curTime));
                    curSample.setDailyFeatures(createDaily(API_SYMBOL, curTime)); 
                    curSample.setWeeklyFeatures(createWeekly(API_SYMBOL, weekAgo, curTime)); 

                    curDay++; 
                }
            }

            curMonth++;
            if (curMonth == 13) {
                curMonth = 1; 
            }
        }
            yearCounter--; 
        }
    
    public double[][] createIntraday(String API, LocalDate timestamp) {

        List<NonStandardData> intradayData = nonStandardService.getStockDataBySymbolAndDateRange(API, timestamp, timestamp); 
        intradayData = intradayData.stream()
                         .filter(item -> item.getTimetype() == "daily")
                         .collect(Collectors.toList()); 
        double[][] intradayFeatures = new double[5][5];

        return intradayFeatures; 
    }
        

    public double[] createDaily(String API, LocalDate timestamp) {

        List<NonStandardData> dayData = nonStandardService.getStockDataBySymbolAndDateRange(API, timestamp, timestamp); 
        dayData = dayData.stream()
                         .filter(item -> item.getTimetype() == "daily")
                         .collect(Collectors.toList()); 
        double[] dailyFeatures = new double[5]; 

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
                                                     .filter(item -> item.getTimetype() == "weekly")
                                                     .collect(Collectors.toList()); 
        List<NonStandardData> monthlyData = weekData.stream()
                                                    .filter(item -> item.getTimetype() == "monthly")
                                                    .collect(Collectors.toList());
        double[] weeklyFeatures = new double[10]; 

        weeklyFeatures[0] = onlyWeekData.get(0).getSmaLong(); 
        weeklyFeatures[1] = onlyWeekData.get(0).getEmaLong();
        weeklyFeatures[2] = onlyWeekData.get(0).getMacd();
        weeklyFeatures[3] = onlyWeekData.get(0).getMacdSignal();
        weeklyFeatures[4] = onlyWeekData.get(0).getMacdHist();

 
        LocalDate lastMonthDate = endDate.minusMonths(1); 
        List<NonStandardData> lastMonthlyData = nonStandardService.getStockDataBySymbolAndDateRange(API, endDate, lastMonthDate); 
        lastMonthlyData = lastMonthlyData.stream()
                                         .filter(item -> item.getTimetype() == "monthly")
                                         .collect(Collectors.toList());
        double currentCPI = monthlyData.get(0).getCpi();
        double lastCPI = lastMonthlyData.get(0).getCpi(); 
        double averageCPI = currentCPI * (monthlyData.get(0).getTimestamp().getDayOfMonth() / 15) 
                            + lastCPI * (30 - monthlyData.get(0).getTimestamp().getDayOfMonth() / 60); 
        
        weeklyFeatures[5] = averageCPI;
        
        List<NonStandardData> quarterlyData; 
        if (endDate.getDayOfMonth() <= 3) {

            quarterlyData = nonStandardService.getStockDataBySymbolAndDateRange(API, 
                                                                                LocalDate.of(endDate.getYear(), 
                                                                                             1, 
                                                                                             1), 
                                                                                endDate);
            quarterlyData = quarterlyData.stream()
                                         .filter(item -> item.getTimetype() == "quarterly")
                                         .collect(Collectors.toList()); 
        } else if (endDate.getDayOfMonth() <= 6) {

            quarterlyData = nonStandardService.getStockDataBySymbolAndDateRange(API, 
                                                                                LocalDate.of(endDate.getYear(), 
                                                                                             4, 
                                                                                             1), 
                                                                                endDate);
            quarterlyData = quarterlyData.stream()
                                         .filter(item -> item.getTimetype() == "quarterly")
                                         .collect(Collectors.toList()); 
        } else if (endDate.getDayOfMonth() <= 9) {

            quarterlyData = nonStandardService.getStockDataBySymbolAndDateRange(API, 
                                                                                LocalDate.of(endDate.getYear(), 
                                                                                             7, 
                                                                                             1), 
                                                                                endDate);
            quarterlyData = quarterlyData.stream()
                                         .filter(item -> item.getTimetype() == "quarterly")
                                         .collect(Collectors.toList()); 
        } else {

            quarterlyData = nonStandardService.getStockDataBySymbolAndDateRange(API, 
                                                                                LocalDate.of(endDate.getYear(), 
                                                                                             10, 
                                                                                             1), 
                                                                                endDate);
            quarterlyData = quarterlyData.stream()
                                         .filter(item -> item.getTimetype() == "quarterly")
                                         .collect(Collectors.toList()); 
        }

        weeklyFeatures[6] = quarterlyData.get(0).getGdp();  

        return weeklyFeatures; 
        
    }

}
