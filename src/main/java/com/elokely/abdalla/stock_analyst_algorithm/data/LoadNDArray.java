package com.elokely.abdalla.stock_analyst_algorithm.data;

import ai.djl.ndarray.NDArray;
import ai.djl.ndarray.NDManager;

public class LoadNDArray {

    public NDArray loadFeatures() {
        NDManager manager = NDManager.newBaseManager();
        return manager.create(new float[]{});
    }
}
