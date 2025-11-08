package com.elokely.abdalla.stock_analyst_algorithm.data.rawData.indicatorsData;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

public class FetchIndicators {
    private static final String API_KEY = ""; 
    private static final String API_SYMBOL = "AMZN"; 

    public static void main(String[] args) throws Exception {
        GETSMA_LongTerm(12);
        GETEMA_LongTerm(12);
        GETSMA_ShortTerm(12);
        GETEMA_ShortTerm(12);

        GETMACD(12);
        GETADX(12);
        GETATR(12);


        GETStoch(12); 
        GETRSI(12);
        GETAroon(12);
        GETBBands(12);

        GETCPI(); 
        GETGDP(); 
    }

        public static void GETSMA_ShortTerm(int numRequests) { 

        int curMonth = 1; 
        String monthString = "01"; 
        int yearCounter = 2025; 

        for (int i = 0; i < numRequests; i++) {

            String alphaVantageAPI = "https://www.alphavantage.co/query?function=SMA&";
            URI alphaVantageURI = java.net.URI.create(alphaVantageAPI + 
                                                      "symbol=" + API_SYMBOL 
                                                      + "&interval=daily&month="
                                                      + yearCounter + "-" + monthString 
                                                      + "&time_period=500&series_type=open&apikey=" 
                                                      + API_KEY + "&datatype=csv"); 
            String fileName = API_SYMBOL + "_SMA-ST" + yearCounter + monthString + ".csv"; 

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

        
        public static void GETEMA_ShortTerm(int numRequests) { 

        int curMonth = 1; 
        String monthString = "01"; 
        int yearCounter = 2025; 

        for (int i = 0; i < numRequests; i++) {

            String alphaVantageAPI = "https://www.alphavantage.co/query?function=EMA&";
            URI alphaVantageURI = java.net.URI.create(alphaVantageAPI + 
                                                      "symbol=" + API_SYMBOL 
                                                      + "&interval=daily&month="
                                                      + yearCounter + "-" + monthString 
                                                      + "&time_period=500&series_type=open&apikey=" 
                                                      + API_KEY + "&datatype=csv"); 
            String fileName = API_SYMBOL + "_EMA-ST" + yearCounter + monthString + ".csv"; 

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

        public static void GETSMA_LongTerm(int numRequests) { 

        int curMonth = 1; 
        String monthString = "01"; 
        int yearCounter = 2025; 

        for (int i = 0; i < numRequests; i++) {

            String alphaVantageAPI = "https://www.alphavantage.co/query?function=SMA&";
            URI alphaVantageURI = java.net.URI.create(alphaVantageAPI + 
                                                      "symbol=" + API_SYMBOL 
                                                      + "&interval=daily&month="
                                                      + yearCounter + "-" + monthString 
                                                      + "&time_period=500&series_type=open&apikey=" 
                                                      + API_KEY + "&datatype=csv"); 
            String fileName = API_SYMBOL + "_SMA-LT" + yearCounter + monthString + ".csv"; 

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
   
        public static void GETEMA_LongTerm(int numRequests) { 

        int curMonth = 1; 
        String monthString = "01"; 
        int yearCounter = 2025; 

        for (int i = 0; i < numRequests; i++) {

            String alphaVantageAPI = "https://www.alphavantage.co/query?function=EMA&";
            URI alphaVantageURI = java.net.URI.create(alphaVantageAPI + 
                                                      "symbol=" + API_SYMBOL 
                                                      + "&interval=weekly&month="
                                                      + yearCounter + "-" + monthString 
                                                      + "&time_period=500&series_type=open&apikey=" 
                                                      + API_KEY + "&datatype=csv"); 
            String fileName = API_SYMBOL + "_EMA-LT" + yearCounter + monthString + ".csv"; 

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

        public static void GETMACD(int numRequests) { 

        int curMonth = 1; 
        String monthString = "01"; 
        int yearCounter = 2025; 

        for (int i = 0; i < numRequests; i++) {

            String alphaVantageAPI = "https://www.alphavantage.co/query?function=MACD&";
            URI alphaVantageURI = java.net.URI.create(alphaVantageAPI + 
                                                      "symbol=" + API_SYMBOL 
                                                      + "&interval=weekly&month="
                                                      + yearCounter + "-" + monthString 
                                                      + "&series_type=open&apikey=" 
                                                      + API_KEY + "&datatype=csv"); 
            String fileName = API_SYMBOL + "_MACD-" + yearCounter + monthString + ".csv"; 

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

        public static void GETADX(int numRequests) { 

        int curMonth = 1; 
        String monthString = "01"; 
        int yearCounter = 2025; 

        for (int i = 0; i < numRequests; i++) {

            String alphaVantageAPI = "https://www.alphavantage.co/query?function=ADX&";
            URI alphaVantageURI = java.net.URI.create(alphaVantageAPI + 
                                                      "symbol=" + API_SYMBOL 
                                                      + "&interval=daily&month="
                                                      + yearCounter + "-" + monthString 
                                                      + "&time_period=500&apikey=" 
                                                      + API_KEY + "&datatype=csv"); 
            String fileName = API_SYMBOL + "_ADX-" + yearCounter + monthString + ".csv"; 

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
        
        public static void GETATR(int numRequests) { 

        int curMonth = 1; 
        String monthString = "01"; 
        int yearCounter = 2025; 

        for (int i = 0; i < numRequests; i++) {

            String alphaVantageAPI = "https://www.alphavantage.co/query?function=ATR&";
            URI alphaVantageURI = java.net.URI.create(alphaVantageAPI + 
                                                      "symbol=" + API_SYMBOL 
                                                      + "&interval=5min&month="
                                                      + yearCounter + "-" + monthString 
                                                      + "&time_period=500&apikey=" 
                                                      + API_KEY + "&datatype=csv"); 
            String fileName = API_SYMBOL + "_ATR-" + yearCounter + monthString + ".csv"; 

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

        public static void GETCPI() { 

            String alphaVantageAPI = "https://www.alphavantage.co/query?function=CPI&";
            URI alphaVantageURI = java.net.URI.create(alphaVantageAPI + 
                                                      "symbol=" + API_SYMBOL 
                                                      + "&interval=monthly"
                                                      + "&apikey=" + API_KEY
                                                      + "&datatype=csv"); 
            String fileName = API_SYMBOL + "_CPI-" + ".csv"; 

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

        public static void GETGDP() { 

            String alphaVantageAPI = "https://www.alphavantage.co/query?function=REAL_GDP&";
            URI alphaVantageURI = java.net.URI.create(alphaVantageAPI + 
                                                      "symbol=" + API_SYMBOL 
                                                      + "&interval=quarterly"
                                                      + "&apikey=" + API_KEY
                                                      + "&datatype=csv"); 
            String fileName = API_SYMBOL + "_GDP-" + ".csv"; 

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
