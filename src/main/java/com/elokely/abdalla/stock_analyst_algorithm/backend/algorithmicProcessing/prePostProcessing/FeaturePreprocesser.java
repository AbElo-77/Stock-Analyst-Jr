package com.elokely.abdalla.stock_analyst_algorithm.backend.algorithmicProcessing.prePostProcessing;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component 
@Order(1)
public class FeaturePreprocesser {

    private FeatureAssembly featureAssembly;

    @Value("${feature.preprocess}")
    private boolean preprocess;

    public void FeatureAssembly(FeatureAssembly featureAssembly) {

        this.featureAssembly = featureAssembly; 
    }
    
}
