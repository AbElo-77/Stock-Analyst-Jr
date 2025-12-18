package com.elokely.abdalla.stock_analyst_algorithm.backend.algorithmicProcessing.prePostProcessing;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(2)
public class FeaturePreprocessor implements CommandLineRunner {

    private final FeatureAssembly featureAssembly;

    @Value("${feature.preprocess:false}")
    private boolean preprocess;

    private static final Path OUT =
            Paths.get("data/features_djl_v1.bin");

    public FeaturePreprocessor(FeatureAssembly featureAssembly) {
        this.featureAssembly = featureAssembly;
    }

    @Override
    public void run(String... args) {

        if (!preprocess) { return; }

        featureAssembly.assembleFeatures();
        List<FeatureSamples> samples = featureAssembly.getSamples();

        int size = samples.size();
        int dailyDim = samples.get(0).getDailyFeatures().length;
        int weeklyDim = samples.get(0).getWeeklyFeatures().length;
        int intradayDim = samples.get(0).getIntradayFeatures().length 
                          * samples.get(0).getIntradayFeatures()[0].length;

        try (FileChannel fc = FileChannel.open(
                OUT,
                StandardOpenOption.CREATE,
                StandardOpenOption.WRITE,
                StandardOpenOption.TRUNCATE_EXISTING
        )) {

            ByteBuffer buf = ByteBuffer.allocateDirect(1024 * 1024).order(ByteOrder.LITTLE_ENDIAN);

            buf.putInt(size);
            buf.putInt(dailyDim);
            buf.putInt(weeklyDim);
            buf.putInt(intradayDim);
            reset(fc, buf);

            for (FeatureSamples s : samples) {

                write(buf, s.getDailyFeatures());
                write(buf, s.getWeeklyFeatures());

                for (double[] row : s.getIntradayFeatures()) {
                    write(buf, row);
                }

                reset(fc, buf);
            }

            reset(fc, buf);

        } catch (Exception e) {
        }

        System.exit(0);
    }

    private void write(ByteBuffer buf, double[] arr) {
        for (double v : arr) {
            buf.putDouble(v);
        }
    }

    private void reset(FileChannel fc, ByteBuffer buf) throws IOException {
        buf.flip();
        fc.write(buf);
        buf.clear();
    }
}
