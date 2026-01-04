package com.elokely.abdalla.stock_analyst_algorithm.data;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import ai.djl.ndarray.NDArray;
import ai.djl.ndarray.NDManager;
import ai.djl.ndarray.types.Shape;

public class LoadNDArray {

    private static final Path FEATURES_FILE = Paths.get("data/features_djl_v1.bin");

    public NDArray loadFeatures() {
        NDManager manager = NDManager.newBaseManager();

        if (!FEATURES_FILE.toFile().exists()) {
            return manager.create(new double[]{});
        }

        try (FileChannel fc = FileChannel.open(FEATURES_FILE, StandardOpenOption.READ)) {

            ByteBuffer header = ByteBuffer.allocate(16).order(ByteOrder.LITTLE_ENDIAN);
            int headerRead = fc.read(header);
            if (headerRead != 16) {
                throw new java.io.IOException("Invalid features file header (too short)");
            }
            header.flip();
            int size = header.getInt();
            int dailyDim = header.getInt();
            int weeklyDim = header.getInt();
            int intradayDim = header.getInt();

            if (size <= 0) {
                throw new java.io.IOException("Invalid sample size in features file");
            }

            int dim = dailyDim + weeklyDim + intradayDim;
            double[] data = new double[size * dim];

            ByteBuffer buf = ByteBuffer.allocate(dim * Double.BYTES).order(ByteOrder.LITTLE_ENDIAN);

            for (int i = 0; i < size; i++) {
                buf.clear();
                while (buf.hasRemaining()) {
                    int r = fc.read(buf);
                    if (r == -1) {
                        throw new java.io.IOException("Unexpected EOF while reading features");
                    }
                }
                buf.flip();
                for (int j = 0; j < dim; j++) {
                    data[i * dim + j] = buf.getDouble();
                }
            }

            return manager.create(data, new Shape(size, dim));

        } catch (Exception e) {
            e.printStackTrace();
            return manager.create(new double[]{});
        }
    }
}
