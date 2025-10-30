package com.elokely.abdalla.stock_analyst_algorithm.data.rawData.coreData;

import java.io.FileReader;
import java.io.BufferedReader; 

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

// Derive: Volume (VWAP, VSMA, OBV), D/E Ratio

public class FetchCoreData {
    private static final String API_KEY = ""; 
    private static final String API_SYMBOL = "AMZN"; 

    public static void main(String[] args) throws Exception {
        GETIntraday(12);
        GETDaily(); 

        int curMonth = 10; 
        String monthString = "10"; 
        int yearCounter = 2025; 

        FileReader currFile = new FileReader("src\\main\\java\\com\\elokely\\abdalla\\stock_analyst_algorithm\\data\\rawData\\coreData\\AMZN_INTRADAY-" + yearCounter + monthString + ".csv"); 
        BufferedReader currReader = new BufferedReader(currFile);

        // Getting Volume Calculations Based on Data
        while (currReader != null) {

            String line; 
            while ((line = currReader.readLine()) != null) {

                // Process Information
                String[] columns = line.split(","); 
                System.out.println(columns[5]); 
            }

            currReader.close(); 
            curMonth--; 
            if (curMonth > 9) monthString = "" + curMonth; else monthString = "0" + curMonth;

            if (curMonth == 0) {
                yearCounter--; 
                curMonth = 12;
                monthString = "12";  
            }

            try { 
                currFile = new FileReader("src\\main\\java\\com\\elokely\\abdalla\\stock_analyst_algorithm\\data\\rawData\\coreData\\AMZN_INTRADAY-" + yearCounter + monthString + ".csv"); 
                currReader = new BufferedReader(currFile); }
            catch (IOException e) {
                break; 
            }
        }
        

    }

    public static void GETIntraday(int numRequests) { 

        int curMonth = 1; 
        String monthString = "01"; 
        int yearCounter = 2024; 

        for (int i = 0; i < numRequests; i++) {

            String alphaVantageAPI = "https://www.alphavantage.co/query?function=TIME_SERIES_INTRADAY&";
            URI alphaVantageURI = java.net.URI.create(alphaVantageAPI + 
                                                      "symbol=" + API_SYMBOL 
                                                      + "&interval=5min&month="
                                                      + yearCounter + "-" + monthString 
                                                      + "&outputsize=full&apikey=" 
                                                      + API_KEY + "&datatype=csv"); 
            String fileName = API_SYMBOL + "_INTRADAY-" + yearCounter + monthString + ".csv"; 

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

    public static void GETDaily() { 


        String alphaVantageAPI = "https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&";
        URI alphaVantageURI = java.net.URI.create(alphaVantageAPI + 
                                                      "symbol=" + API_SYMBOL 
                                                      + "&outputsize=full&apikey=" 
                                                      + API_KEY + "&datatype=csv"); 
        String fileName = API_SYMBOL + "_DAILY-" + ".csv"; 

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
    }
}
