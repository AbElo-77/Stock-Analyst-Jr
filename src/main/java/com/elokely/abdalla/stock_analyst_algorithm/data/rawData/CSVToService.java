package com.elokely.abdalla.stock_analyst_algorithm.data.rawData;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.elokely.abdalla.stock_analyst_algorithm.data.SQLData.NonStandardService;
import com.elokely.abdalla.stock_analyst_algorithm.data.SQLData.Controller.Entities.NonStandardData;

import com.elokely.abdalla.stock_analyst_algorithm.data.SQLData.StockDataService;
import com.elokely.abdalla.stock_analyst_algorithm.data.SQLData.Controller.Entities.StockData;

public class CSVToService {

    private final StockDataService stockDataService; 
    private final NonStandardService nonStandardService;

    public CSVToService(StockDataService stockDataService, NonStandardService nonStandardService) {

        this.stockDataService = stockDataService;
        this.nonStandardService = nonStandardService;
    }

    public void generateStockData() {
        LocalDate startDate = LocalDate.of(2000, 1, 1);
        LocalDate endDate = LocalDate.of(2025, 12, 2);

        String symbol = "AMZN";

        for (LocalDate curDate = startDate; !curDate.isAfter(endDate); curDate = curDate.plusMonths(1)) {
            int curMonth = curDate.getMonthValue();
            String monthString = curMonth > 9 ? "" + curMonth : "0" + curMonth;

            LocalDate monthStart = curDate.withDayOfMonth(1);
            LocalDate monthEnd = curDate.withDayOfMonth(curDate.lengthOfMonth());
            LocalDateTime monthStartDateTime = monthStart.atStartOfDay();
            LocalDateTime monthEndDateTime = monthEnd.atTime(23,59,59);

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            Map<LocalDateTime, StockData> existing = new HashMap<>();
            try {
                List<StockData> existingRows = stockDataService.getStockDataBySymbolAndDateRange(symbol, monthStartDateTime, monthEndDateTime);
                if (existingRows != null) {
                    for (StockData r : existingRows) existing.put(r.getTimestamp(), r);
                }
            } catch (Exception e) {
            }

            String intradayPath = "src/main/java/com/elokely/abdalla/stock_analyst_algorithm/data/rawData/coreData/" + symbol + "_INTRADAY-"
                    + curDate.getYear() + monthString + ".csv";
            File intradayFile = new File(intradayPath);
            if (intradayFile.exists()) {
                try (BufferedReader intradayReader = new BufferedReader(new FileReader(intradayFile))) {
                    String line = intradayReader.readLine(); // header
                    while ((line = intradayReader.readLine()) != null) {
                        if (line.trim().isEmpty()) continue;
                        String[] columns = line.split(",");
                        try {
                            LocalDateTime timestamp = LocalDateTime.parse(columns[0], dtf);
                            float open = Float.parseFloat(columns[1]);
                            float high = Float.parseFloat(columns[2]);
                            float low = Float.parseFloat(columns[3]);
                            float close = Float.parseFloat(columns[4]);
                            long volume = Long.parseLong(columns[5]);

                            StockData sd = existing.get(timestamp);
                            if (sd == null) sd = new StockData();
                            sd.setSymbol(symbol);
                            sd.setTimestamp(timestamp);
                            sd.setOpen(open);
                            sd.setHigh(high);
                            sd.setLow(low);
                            sd.setClose(close);
                            sd.setVolume(volume);
                            stockDataService.saveStockData(sd);
                            existing.put(timestamp, sd);
                        } catch (Exception ex) {
                        }
                    }
                } catch (Exception e) {
                }
            }

            try {
                String vwapPathCore = "src/main/java/com/elokely/abdalla/stock_analyst_algorithm/data/rawData/coreData/" + symbol + "_VWAP-" + curDate.getYear() + monthString + ".csv";
                String vwapPathInd = "src/main/java/com/elokely/abdalla/stock_analyst_algorithm/data/rawData/indicatorsData/" + symbol + "_VWAP-" + curDate.getYear() + monthString + ".csv";
                File vwapFile = new File(vwapPathCore);
                if (!vwapFile.exists()) vwapFile = new File(vwapPathInd);
                if (vwapFile.exists()) {
                    try (BufferedReader reader = new BufferedReader(new FileReader(vwapFile))) {
                        String line = reader.readLine(); 

                        while ((line = reader.readLine()) != null) {
                            if (line.trim().isEmpty()) continue;
                            String[] cols = line.split(",");
                            try {
                                LocalDateTime ts = LocalDateTime.parse(cols[0], dtf);
                                double vwap = Double.parseDouble(cols[1]);
                                StockData sd = existing.get(ts);
                                if (sd == null) {
                                    sd = new StockData(); sd.setSymbol(symbol); sd.setTimestamp(ts);
                                }
                                sd.setVwap(vwap);
                                stockDataService.saveStockData(sd);
                                existing.put(ts, sd);
                            } catch (Exception ex) {
                            }
                        }
                    } catch (Exception e) {
                    }
                }
            } catch (Exception e) {}

            try {
                String stochPath = "src/main/java/com/elokely/abdalla/stock_analyst_algorithm/data/rawData/indicatorsData/" + symbol + "_STOCH-" + curDate.getYear() + monthString + ".csv";
                File stochFile = new File(stochPath);
                if (stochFile.exists()) {
                    try (BufferedReader reader = new BufferedReader(new FileReader(stochFile))) {
                        String line = reader.readLine();
                        while ((line = reader.readLine()) != null) {
                            if (line.trim().isEmpty()) continue;
                            String[] cols = line.split(",");
                            try {
                                LocalDateTime ts = LocalDateTime.parse(cols[0], dtf);
                                double stochK = Double.parseDouble(cols[1]);
                                double stochD = Double.parseDouble(cols[2]);
                                StockData sd = existing.get(ts);
                                if (sd == null) { sd = new StockData(); sd.setSymbol(symbol); sd.setTimestamp(ts); }
                                sd.setStochK(stochK);
                                sd.setStochD(stochD);
                                stockDataService.saveStockData(sd);
                                existing.put(ts, sd);
                            } catch (Exception ex) {}
                        }
                    } catch (Exception e) {}
                }
            } catch (Exception e) {}

            try {
                String rsiPath = "src/main/java/com/elokely/abdalla/stock_analyst_algorithm/data/rawData/indicatorsData/" + symbol + "_RSI-" + curDate.getYear() + monthString + ".csv";
                File rsiFile = new File(rsiPath);
                if (rsiFile.exists()) {
                    try (BufferedReader reader = new BufferedReader(new FileReader(rsiFile))) {
                        String line = reader.readLine();
                        while ((line = reader.readLine()) != null) {
                            if (line.trim().isEmpty()) continue;
                            String[] cols = line.split(",");
                            try {
                                LocalDateTime ts = LocalDateTime.parse(cols[0], dtf);
                                double rsi = Double.parseDouble(cols[1]);
                                StockData sd = existing.get(ts);
                                if (sd == null) { sd = new StockData(); sd.setSymbol(symbol); sd.setTimestamp(ts); }
                                sd.setRsi(rsi);
                                stockDataService.saveStockData(sd);
                                existing.put(ts, sd);
                            } catch (Exception ex) {}
                        }
                    } catch (Exception e) {}
                }
            } catch (Exception e) {}

            try {
                String aroonPath = "src/main/java/com/elokely/abdalla/stock_analyst_algorithm/data/rawData/indicatorsData/" + symbol + "_AROON-" + curDate.getYear() + monthString + ".csv";
                File aroonFile = new File(aroonPath);
                if (aroonFile.exists()) {
                    try (BufferedReader reader = new BufferedReader(new FileReader(aroonFile))) {
                        String line = reader.readLine();
                        while ((line = reader.readLine()) != null) {
                            if (line.trim().isEmpty()) continue;
                            String[] cols = line.split(",");
                            try {
                                LocalDateTime ts = LocalDateTime.parse(cols[0], dtf);
                                double aroonUp = Double.parseDouble(cols[1]);
                                double aroonDown = Double.parseDouble(cols[2]);
                                StockData sd = existing.get(ts);
                                if (sd == null) { sd = new StockData(); sd.setSymbol(symbol); sd.setTimestamp(ts); }
                                sd.setAroonUp(aroonUp);
                                sd.setAroonDown(aroonDown);
                                stockDataService.saveStockData(sd);
                                existing.put(ts, sd);
                            } catch (Exception ex) {}
                        }
                    } catch (Exception e) {}
                }
            } catch (Exception e) {}

            try {
                String bbandsPath = "src/main/java/com/elokely/abdalla/stock_analyst_algorithm/data/rawData/indicatorsData/" + symbol + "_BBANDS-" + curDate.getYear() + monthString + ".csv";
                File bbandsFile = new File(bbandsPath);
                if (bbandsFile.exists()) {
                    try (BufferedReader reader = new BufferedReader(new FileReader(bbandsFile))) {
                        String line = reader.readLine();
                        while ((line = reader.readLine()) != null) {
                            if (line.trim().isEmpty()) continue;
                            String[] cols = line.split(",");
                            try {
                                LocalDateTime ts = LocalDateTime.parse(cols[0], dtf);
                                double lower = Double.parseDouble(cols[1]);
                                double mid = Double.parseDouble(cols[2]);
                                double upper = Double.parseDouble(cols[3]);
                                StockData sd = existing.get(ts);
                                if (sd == null) { sd = new StockData(); sd.setSymbol(symbol); sd.setTimestamp(ts); }
                                sd.setBbandsLower(lower);
                                sd.setBbandsMid(mid);
                                sd.setBbandsUpper(upper);
                                stockDataService.saveStockData(sd);
                                existing.put(ts, sd);
                            } catch (Exception ex) {}
                        }
                    } catch (Exception e) {}
                }
            } catch (Exception e) {}

        }
    }

    public void generateNonStandardData() {
        LocalDate startDate = LocalDate.of(2000, 1, 1);
        LocalDate endDate = LocalDate.of(2025, 12, 2);

        String symbol = "AMZN";
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        for (LocalDate curDate = startDate; !curDate.isAfter(endDate); curDate = curDate.plusMonths(1)) {
            int curMonth = curDate.getMonthValue();
            String monthString = curMonth > 9 ? "" + curMonth : "0" + curMonth;

            LocalDate monthStart = curDate.withDayOfMonth(1);
            LocalDate monthEnd = curDate.withDayOfMonth(curDate.lengthOfMonth());

            Map<LocalDate, NonStandardData> existing = new HashMap<>();
            try {
                List<NonStandardData> existingRows = nonStandardService.getStockDataBySymbolAndDateRange(symbol, monthStart, monthEnd);
                if (existingRows != null) {
                    for (NonStandardData r : existingRows) existing.put(r.getTimestamp(), r);
                }
            } catch (Exception e) {}

            String[] files = new String[] {"OBV", "VSMA", "SMA_SHORT", "SMA_LONG", "EMA_SHORT", "EMA_LONG", "MACD", "MACD_SIGNAL", "MACD_HIST", "ADX", "ATR", "CPI", "GDP"};
            for (String f : files) {
                String path = "src/main/java/com/elokely/abdalla/stock_analyst_algorithm/data/rawData/indicatorsData/" + symbol + "_" + f + "-" + curDate.getYear() + monthString + ".csv";
                File file = new File(path);
                if (!file.exists()) continue;
                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    String line = reader.readLine();
                    while ((line = reader.readLine()) != null) {
                        if (line.trim().isEmpty()) continue;
                        String[] cols = line.split(",");
                        try {
                            LocalDate ts = LocalDate.parse(cols[0], dateFormatter);
                            double val = Double.parseDouble(cols[1]);
                            NonStandardData ns = existing.get(ts);
                            if (ns == null) { ns = new NonStandardData(); ns.setSymbol(symbol); ns.setTimestamp(ts); }
                            String lower = f.toLowerCase();
                            if ("obv".equalsIgnoreCase(f)) ns.setObv(val);
                            else if ("vsma".equalsIgnoreCase(f)) ns.setVsma(val);
                            else if ("sma_short".equalsIgnoreCase(lower) || "sma_short".equalsIgnoreCase(f)) ns.setSmaShort(val);
                            else if ("sma_long".equalsIgnoreCase(lower) || "sma_long".equalsIgnoreCase(f)) ns.setSmaLong(val);
                            else if ("ema_short".equalsIgnoreCase(lower) || "ema_short".equalsIgnoreCase(f)) ns.setEmaShort(val);
                            else if ("ema_long".equalsIgnoreCase(lower) || "ema_long".equalsIgnoreCase(f)) ns.setEmaLong(val);
                            else if ("macd".equalsIgnoreCase(f)) ns.setMacd(val);
                            else if ("macd_signal".equalsIgnoreCase(lower) || "macd_signal".equalsIgnoreCase(f)) ns.setMacdSignal(val);
                            else if ("macd_hist".equalsIgnoreCase(lower) || "macd_hist".equalsIgnoreCase(f)) ns.setMacdHist(val);
                            else if ("adx".equalsIgnoreCase(f)) ns.setAdx(val);
                            else if ("atr".equalsIgnoreCase(f)) ns.setAtr(val);
                            else if ("cpi".equalsIgnoreCase(f)) { ns.setCpi(val); ns.setTimetype("monthly"); }
                            else if ("gdp".equalsIgnoreCase(f)) { ns.setGdp(val); ns.setTimetype("quarterly"); }
                            if (ns.getTimetype() == null) ns.setTimetype("daily");
                            nonStandardService.saveStockData(ns);
                            existing.put(ts, ns);
                        } catch (Exception ex) {}
                    }
                } catch (Exception e) {}
            }
        }
    }

}
