package com.elokely.abdalla.stock_analyst_algorithm.backend.algorithmicProcessing.models.baselineModels;

import java.nio.DoubleBuffer;
import java.util.Locale;

import com.elokely.abdalla.stock_analyst_algorithm.data.LoadNDArray;

import ai.djl.ndarray.NDArray;
import ai.djl.ndarray.NDList;
import ai.djl.ndarray.NDManager;
import ai.djl.ndarray.types.Shape;
import ai.djl.translate.Translator;
import ai.djl.translate.TranslatorContext;

public class kNNModel implements Translator<DoubleBuffer, String> {

    private final LoadNDArray loadNDArray = new LoadNDArray();
    private final NDArray trainingFeatures = loadNDArray.loadFeatures();
    private int k = 1;

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

    public void setK(int k) {
        if (k < 1) {
            this.k = 1;
        } else {
            this.k = k;
        }
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

        long[] fshape = features.getShape().getShape();
        long numCols = fshape.length >= 2 ? fshape[1] : -1;
        long qcols = query.getShape().get(1);
        if (numCols <= 0 || qcols != numCols) {
            return String.format(Locale.ROOT, "dim-mismatch:features=%d,query=%d", numCols, qcols);
        }

        NDArray diff = features.sub(query);
        NDArray dists = diff.pow(2).sum(new int[] {1}).sqrt();

        NDArray sortedIdx = dists.argSort();
        long[] idxs = sortedIdx.toLongArray();

        int kk = Math.min(this.k, idxs.length);
        StringBuilder sbIdx = new StringBuilder();
        StringBuilder sbDist = new StringBuilder();
        for (int i = 0; i < kk; i++) {
            if (i > 0) {
                sbIdx.append(',');
                sbDist.append(',');
            }
            long idx = idxs[i];
            double dist = dists.getDouble((int) idx);
            sbIdx.append(idx);
            sbDist.append(String.format(Locale.ROOT, "%.6f", dist));
        }

        return String.format(Locale.ROOT, "nearestIndices=%s;distances=%s", sbIdx.toString(), sbDist.toString());
    }
}
