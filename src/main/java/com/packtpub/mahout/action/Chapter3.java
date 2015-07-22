package com.packtpub.mahout.action;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.IRStatistics;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.eval.RecommenderEvaluator;
import org.apache.mahout.cf.taste.eval.RecommenderIRStatsEvaluator;
import org.apache.mahout.cf.taste.impl.eval.AverageAbsoluteDifferenceRecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.eval.GenericRecommenderIRStatsEvaluator;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.apache.mahout.common.RandomUtils;

import java.io.File;
import java.io.IOException;

/**
 * Created by ubuntu on 15-7-22.
 */
public class Chapter3 {
    public static void main(String[] args) throws IOException, TasteException {
        RandomUtils.useTestSeed();
        DataModel model = new FileDataModel(new File("/home/ubuntu/Data/action/intro.csv"));
        RecommenderIRStatsEvaluator evaluator = new GenericRecommenderIRStatsEvaluator();
        RecommenderBuilder builder = new RecommenderBuilder() {
            public Recommender buildRecommender(DataModel dataModel) throws TasteException {
                UserSimilarity similarity = new PearsonCorrelationSimilarity(dataModel);
                UserNeighborhood neighborhood = new NearestNUserNeighborhood(2, similarity, dataModel);
                return new GenericUserBasedRecommender(dataModel, neighborhood, similarity);
            }
        };
        IRStatistics statistics = evaluator.evaluate(builder,null,model,null,2,
                GenericRecommenderIRStatsEvaluator.CHOOSE_THRESHOLD,1.0);
        System.out.println(statistics.getPrecision());
        System.out.println(statistics.getRecall());
    }
}
