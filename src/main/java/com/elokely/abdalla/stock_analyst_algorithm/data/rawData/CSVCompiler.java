package com.elokely.abdalla.stock_analyst_algorithm.data.rawData;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component 
@Order(1)
public class CSVCompiler {

    private CSVToService csvToService;

    @Value("${feature.preprocess:false}")
    private boolean preprocess;

    public CSVCompiler(CSVToService csvToService) {
        this.csvToService = csvToService;
    }

    public void inputStockData() {

        if (!preprocess) { return; }

        csvToService.generateStockData();
        csvToService.generateNonStandardData();
    }
    
}
