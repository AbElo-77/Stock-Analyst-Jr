package com.elokely.abdalla.stock_analyst_algorithm.backend.algorithmicProcessing.prePostProcessing;

public class FeatureSamples {
    
    private double[] dailyFeatures; 
    private double[] weeklyFeatures; 
    private double[][] intradayFeatures; 

    public FeatureSamples() {
       
    }

    public FeatureSamples(double[] dailyFeatures, double[] weeklyFeatures, double[][] intradayFeatures) {
        this.dailyFeatures = dailyFeatures; 
        this.weeklyFeatures = weeklyFeatures; 
        this.intradayFeatures = intradayFeatures; 
    }

    public double[] getDailyFeatures() {
        return dailyFeatures;
    }

    public double[] getWeeklyFeatures() {
        return weeklyFeatures;
    }

    public double[][] getIntradayFeatures() {
        return intradayFeatures;
    }

    public void setDailyFeatures(double[] dailyFeatures) {
        this.dailyFeatures = dailyFeatures;
    }

    public void setWeeklyFeatures(double[] weeklyFeatures) {
        this.weeklyFeatures = weeklyFeatures;
    }

    public void setIntradayFeatures(double[][] intradayFeatures) {
        this.intradayFeatures = intradayFeatures;
    }
}
