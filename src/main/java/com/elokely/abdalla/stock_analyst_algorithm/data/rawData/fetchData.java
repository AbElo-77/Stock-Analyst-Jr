package com.elokely.abdalla.stock_analyst_algorithm.data.rawData;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

public class fetchData {
    private static final String API_KEY = "My API Key :)"; 
    private static final String API_SYMBOL = "AMZN"; 

    public static void main(String[] args) throws Exception {

        
    }

    public static void GETRequest(int numRequests) { 

        String alphaVantageAPI = "https://www.alphavantage.co/query?function=TIME_SERIES_INTRADAY";
        URI alphaVantageURI = java.net.URI.create(alphaVantageAPI + "symbol=" + API_SYMBOL + "&interval=5min&month=2009-01&outputsize=full&apikey=" + API_KEY);    

            try { 

                URL alphaVantageURL = alphaVantageURI.toURL(); 
                HttpURLConnection getConnection = (HttpURLConnection) alphaVantageURL.openConnection(); 

                getConnection.setRequestMethod("GET"); 
                int responseStatus = getConnection.getResponseCode(); 

                if (responseStatus ==  HttpURLConnection.HTTP_OK) {
                    
                    BufferedReader inputReader = new BufferedReader(new InputStreamReader(getConnection.getInputStream()));
                    StringBuffer response = new StringBuffer(); 

                    String inputLine = inputReader.readLine(); 
                    while (inputReader.readLine() != null) {
                        response.append(inputLine); 
                        inputLine = inputReader.readLine(); 
                    }
                    inputReader.close(); 

                    // Handle Data
                } else {
                    throw new Error("Error fetching data from Alpha Vantage!"); 
                } 
            } catch (IllegalArgumentException | IOException e) {
                throw new RuntimeException("URL not accessible."); 
            }
}
}
