package com.packtpub.mahout.sj;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.common.LongPrimitiveIterator;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.recommender.CachingRecommender;
import org.apache.mahout.cf.taste.impl.recommender.slopeone.SlopeOneRecommender;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Created by neal.xu on 7/20 0020.
 */
public class Chapter01 {
    static final String inputFile = "/home/ubuntu/Data/ml-1m/ratings.dat";
    static final String outputFile = "/home/ubuntu/Data/ml-1m/ratings.csv";

    public static void main(String[] args) throws IOException, TasteException {
        createCsvRatingsFile();

        File ratingsFile = new File(outputFile);
        DataModel dataModel = new FileDataModel(ratingsFile);
        CachingRecommender recommender = new CachingRecommender(new SlopeOneRecommender(dataModel));
        for(LongPrimitiveIterator it = dataModel.getUserIDs();it.hasNext();){
            long userId = it.nextLong();
            List<RecommendedItem> recommend = recommender.recommend(userId, 10);
            if(recommend.isEmpty()){
                System.out.println(String.format("User id %d , no recommend",userId));
            }else{
                for (RecommendedItem item : recommend) {
                    System.out.println(String.format("User %d : %s",userId,item));
                }
            }

        }
    }


    private static void createCsvRatingsFile() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));

        String line;
        String line2writer;

        String[] temp;
        int i = 0;
        while ((line = reader.readLine()) != null && i < 10000) {
            i++;
            temp = line.split("::");
            line2writer = temp[0] + "," + temp[1];
            writer.write(line2writer);
            writer.newLine();
            writer.flush();
        }
        reader.close();
        writer.close();
    }
}
