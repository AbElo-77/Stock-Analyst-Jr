package com.elokely.abdalla.stock_analyst_algorithm.backend.algorithmicProcessing.models.baselineModels;

import java.nio.DoubleBuffer;
import java.util.Locale;
import java.util.Arrays;
import java.util.Properties;

import com.elokely.abdalla.stock_analyst_algorithm.data.LoadNDArray;

import ai.djl.ndarray.NDArray;
import ai.djl.ndarray.NDList;
import ai.djl.ndarray.NDManager;
import ai.djl.ndarray.types.Shape;
import ai.djl.translate.Translator;
import ai.djl.translate.TranslatorContext;

import smile.classification.RandomForest;
import smile.data.DataFrame;
import smile.data.Tuple;
import smile.data.formula.Formula;

public class RandomForestP implements Translator<DoubleBuffer, String> {

    private final LoadNDArray loadNDArray = new LoadNDArray();
    private final NDArray trainingFeatures = loadNDArray.loadFeatures();

    @Override
    public NDList processInput(TranslatorContext ctx, DoubleBuffer input) throws Exception {

        NDManager manager = ctx.getNDManager();

        NDArray features;
        if (trainingFeatures == null || trainingFeatures.isEmpty()) {
            features = manager.create(new double[]{});
        } else {
            double[] raw = trainingFeatures.toDoubleArray();
            Shape shape = trainingFeatures.getShape();
            features = manager.create(raw, shape);
        }

        input.rewind();
        double[] query = new double[input.remaining()];
        for (int i = 0; i < query.length; i++) {
            query[i] = input.get();
        }

        NDArray queryArr = manager.create(query, new Shape(1, query.length));

        return new NDList(features, queryArr);
    }

    public NDArray generateLabels(NDArray features) {
        NDManager mgr = features.getManager();
        long rows = features.getShape().get(0);
        double[] labels = new double[(int) rows];
        return mgr.create(labels, new Shape(rows));
    }

    @Override
    public String processOutput(TranslatorContext ctx, NDList list) throws Exception {

        if (list == null || list.size() < 2) {
            return "no-data";
        }

        NDArray features = list.get(0);
        NDArray query = list.get(1);

        if (features.isEmpty() || query.isEmpty()) {
            return "no-data";
        }

        long[] shape = features.getShape().getShape();
        int rows = (int) shape[0];
        int cols = (shape.length > 1) ? (int) shape[1] : 1;
        double[] flat = features.toDoubleArray();

        double[][] x = new double[rows][cols + 1];
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                x[r][c] = flat[r * cols + c];
            }
        }

        int[] y = Arrays.stream(generateLabels(features).toDoubleArray()).mapToInt(d -> (int) d).toArray();
        for (int r = 0; r < rows; r++) {
            x[r][cols] = y[r];
        }

        String[] colNames = new String[cols + 1];
        for (int i = 0; i < cols; i++) {
            colNames[i] = "c" + i;
        }
        colNames[cols] = "label";

        DataFrame df = DataFrame.of(x, colNames);
        Formula formula = Formula.lhs("label");
        Properties props = new Properties();
        props.setProperty("ntree", String.valueOf(200));

        RandomForest rf = RandomForest.fit(formula, df, props);

        double[] queryArray = query.toDoubleArray();
        String[] featureColNames = Arrays.copyOf(colNames, cols);
        DataFrame qdf = DataFrame.of(new double[][] { Arrays.copyOf(queryArray, cols) }, featureColNames);
        Tuple qtuple = qdf.get(0);
        int prediction = rf.predict(qtuple);

        return String.format(Locale.ROOT, "prediction:%d", prediction);
    }
}
