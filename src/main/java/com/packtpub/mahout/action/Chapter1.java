package com.packtpub.mahout.action;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by ubuntu on 15-7-22.
 */
public class Chapter1 {
    public static void main(String[] args) throws IOException, TasteException {
        DataModel dataModel = new FileDataModel(new File("/home/ubuntu/Data/action/intro.csv"));

        UserSimilarity similarity = new PearsonCorrelationSimilarity(dataModel);
        UserNeighborhood neighborhood = new NearestNUserNeighborhood(2,similarity,dataModel);

        Recommender recommender = new GenericUserBasedRecommender(dataModel,neighborhood,similarity);

        List<RecommendedItem> recommendedItems = recommender.recommend(1, 1);
        for (RecommendedItem item : recommendedItems) {
            System.out.println(item);
        }


    }
}
