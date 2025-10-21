package com.elokely.abdalla.stock_analyst_algorithm.data.rawData.indicatorsData;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

public class fetchIndicators {
    private static final String API_KEY = ""; 
    private static final String API_SYMBOL = "AMZN"; 

    public static void main(String[] args) throws Exception {
        GETStoch(12); // Limit of 25/day on Free Plan
        GETRSI(12);
        GETAroon(12);
        GETBBands(12);

    }

    public static void GETStoch(int numRequests) { 

        int curMonth = 1; 
        String monthString = "01"; 
        int yearCounter = 2025; 

        for (int i = 0; i < numRequests; i++) {

            String alphaVantageAPI = "https://www.alphavantage.co/query?function=STOCH&";
            URI alphaVantageURI = java.net.URI.create(alphaVantageAPI + 
                                                      "symbol=" + API_SYMBOL 
                                                      + "&interval=5min&month="
                                                      + yearCounter + "-" + monthString 
                                                      + "&apikey=" + API_KEY 
                                                      + "&datatype=csv"); 
            String fileName = API_SYMBOL + "_STOCH-" + yearCounter + monthString + ".csv"; 

                try { 

                    URL alphaVantageURL = alphaVantageURI.toURL(); 
                    HttpURLConnection getConnection = (HttpURLConnection) alphaVantageURL.openConnection(); 

                    getConnection.setRequestMethod("GET"); 
                    int responseStatus = getConnection.getResponseCode(); 

                    if (responseStatus ==  HttpURLConnection.HTTP_OK) {
                        
                        InputStream inputReader = getConnection.getInputStream();
                        FileOutputStream outputReader = new FileOutputStream(fileName); 

                        byte[] buffer = new byte[8192];
                        int bytesRead;
                        while ((bytesRead = inputReader.read(buffer))!= -1) {
                            outputReader.write(buffer, 0, bytesRead);
                        }
                        inputReader.close(); 
                        outputReader.close(); 

                    } else {
                        throw new Error("Error fetching data from Alpha Vantage!"); 
                    } 
                } catch (IllegalArgumentException | IOException e) {
                    throw new RuntimeException("URL not accessible."); 
                }

            curMonth++; 
            if (curMonth > 9) monthString = "" + curMonth; else monthString = "0" + curMonth;

            if (curMonth > 12) {
                yearCounter--; 
                curMonth %= 12; 
            }
        }
        }

        public static void GETRSI(int numRequests) { 

        int curMonth = 1; 
        String monthString = "01"; 
        int yearCounter = 2025; 

        for (int i = 0; i < numRequests; i++) {

            String alphaVantageAPI = "https://www.alphavantage.co/query?function=RSI&";
            URI alphaVantageURI = java.net.URI.create(alphaVantageAPI + 
                                                      "symbol=" + API_SYMBOL 
                                                      + "&interval=5min&month="
                                                      + yearCounter + "-" + monthString 
                                                      + "&time_period=500&series_type=open&apikey=" 
                                                      + API_KEY + "&datatype=csv"); 
            String fileName = API_SYMBOL + "_RSI-" + yearCounter + monthString + ".csv"; 

                try { 

                    URL alphaVantageURL = alphaVantageURI.toURL(); 
                    HttpURLConnection getConnection = (HttpURLConnection) alphaVantageURL.openConnection(); 

                    getConnection.setRequestMethod("GET"); 
                    int responseStatus = getConnection.getResponseCode(); 

                    if (responseStatus ==  HttpURLConnection.HTTP_OK) {
                        
                        InputStream inputReader = getConnection.getInputStream();
                        FileOutputStream outputReader = new FileOutputStream(fileName); 

                        byte[] buffer = new byte[8192];
                        int bytesRead;
                        while ((bytesRead = inputReader.read(buffer))!= -1) {
                            outputReader.write(buffer, 0, bytesRead);
                        }
                        inputReader.close(); 
                        outputReader.close(); 

                    } else {
                        throw new Error("Error fetching data from Alpha Vantage!"); 
                    } 
                } catch (IllegalArgumentException | IOException e) {
                    throw new RuntimeException("URL not accessible."); 
                }

            curMonth++; 
            if (curMonth > 9) monthString = "" + curMonth; else monthString = "0" + curMonth;

            if (curMonth > 12) {
                yearCounter--; 
                curMonth %= 12; 
            }
        }
        }

        public static void GETAroon(int numRequests) { 

        int curMonth = 1; 
        String monthString = "01"; 
        int yearCounter = 2025; 

        for (int i = 0; i < numRequests; i++) {

            String alphaVantageAPI = "https://www.alphavantage.co/query?function=AROON&";
            URI alphaVantageURI = java.net.URI.create(alphaVantageAPI + 
                                                      "symbol=" + API_SYMBOL 
                                                      + "&interval=5min&month="
                                                      + yearCounter + "-" + monthString 
                                                      + "&time_period=500&apikey=" + API_KEY 
                                                      + "&datatype=csv"); 
            String fileName = API_SYMBOL + "_AROON-" + yearCounter + monthString + ".csv"; 

                try { 

                    URL alphaVantageURL = alphaVantageURI.toURL(); 
                    HttpURLConnection getConnection = (HttpURLConnection) alphaVantageURL.openConnection(); 

                    getConnection.setRequestMethod("GET"); 
                    int responseStatus = getConnection.getResponseCode(); 

                    if (responseStatus ==  HttpURLConnection.HTTP_OK) {
                        
                        InputStream inputReader = getConnection.getInputStream();
                        FileOutputStream outputReader = new FileOutputStream(fileName); 

                        byte[] buffer = new byte[8192];
                        int bytesRead;
                        while ((bytesRead = inputReader.read(buffer))!= -1) {
                            outputReader.write(buffer, 0, bytesRead);
                        }
                        inputReader.close(); 
                        outputReader.close(); 

                    } else {
                        throw new Error("Error fetching data from Alpha Vantage!"); 
                    } 
                } catch (IllegalArgumentException | IOException e) {
                    throw new RuntimeException("URL not accessible."); 
                }

            curMonth++; 
            if (curMonth > 9) monthString = "" + curMonth; else monthString = "0" + curMonth;

            if (curMonth > 12) {
                yearCounter--; 
                curMonth %= 12; 
            }
        }
        }

        public static void GETBBands(int numRequests) { 

        int curMonth = 1; 
        String monthString = "01"; 
        int yearCounter = 2025; 

        for (int i = 0; i < numRequests; i++) {

            String alphaVantageAPI = "https://www.alphavantage.co/query?function=BBANDS&";
            URI alphaVantageURI = java.net.URI.create(alphaVantageAPI + 
                                                      "symbol=" + API_SYMBOL 
                                                      + "&interval=5min&month="
                                                      + yearCounter + "-" + monthString 
                                                      + "&time_period=500&series_type=open&apikey=" 
                                                      + API_KEY + "&datatype=csv"); 
            String fileName = API_SYMBOL + "_BBANDS-" + yearCounter + monthString + ".csv"; 

                try { 

                    URL alphaVantageURL = alphaVantageURI.toURL(); 
                    HttpURLConnection getConnection = (HttpURLConnection) alphaVantageURL.openConnection(); 

                    getConnection.setRequestMethod("GET"); 
                    int responseStatus = getConnection.getResponseCode(); 

                    if (responseStatus ==  HttpURLConnection.HTTP_OK) {
                        
                        InputStream inputReader = getConnection.getInputStream();
                        FileOutputStream outputReader = new FileOutputStream(fileName); 

                        byte[] buffer = new byte[8192];
                        int bytesRead;
                        while ((bytesRead = inputReader.read(buffer))!= -1) {
                            outputReader.write(buffer, 0, bytesRead);
                        }
                        inputReader.close(); 
                        outputReader.close(); 

                    } else {
                        throw new Error("Error fetching data from Alpha Vantage!"); 
                    } 
                } catch (IllegalArgumentException | IOException e) {
                    throw new RuntimeException("URL not accessible."); 
                }

            curMonth++; 
            if (curMonth > 9) monthString = "" + curMonth; else monthString = "0" + curMonth;

            if (curMonth > 12) {
                yearCounter--; 
                curMonth %= 12; 
            }
        }
        }
}
