package com.elokely.abdalla.stock_analyst_algorithm.backend.algorithmicProcessing.models.baselineModels;

import java.nio.DoubleBuffer;

import com.elokely.abdalla.stock_analyst_algorithm.data.LoadNDArray;

// import ai.djl.Application;
import ai.djl.ndarray.NDArray;
import ai.djl.ndarray.NDList;
import ai.djl.ndarray.NDManager;
import ai.djl.translate.Translator;
import ai.djl.translate.TranslatorContext;

public class kNNModel implements Translator<DoubleBuffer, String> {

    private LoadNDArray loadNDArray = new LoadNDArray(); 
    private final NDArray trainingFeatures = loadNDArray.loadFeatures();

    @Override
    public NDList processInput(TranslatorContext ctx, DoubleBuffer input) throws Exception {
        
        NDManager manager = ctx.getNDManager(); 
        NDArray array = manager.from(trainingFeatures);

        return new NDList(array);
    }

    @Override
    public String processOutput(TranslatorContext ctx, NDList list) throws Exception {

        throw new UnsupportedOperationException("Unimplemented method 'processOutput'");
    }
}
