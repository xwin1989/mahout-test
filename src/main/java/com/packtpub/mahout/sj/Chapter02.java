package com.packtpub.mahout.sj;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.Text;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by ubuntu on 15-7-21.
 */
public class Chapter02 {
    String fileName = "/home/ubuntu/Data/lastfm/original/artists.txt";
    String outputFileName = "/home/ubuntu/Data/lastfm/sequencesfiles/part-0000";
    String targetFileName = "/home/ubuntu/Data/lastfm/original/artists.csv";

    public static void main(String[] args) throws IOException {
        Chapter02 chapter = new Chapter02();
//        chapter.createSequence();
        chapter.decryptionSequency();
    }

    public void createSequence() throws IOException {
        Path path = new Path(outputFileName);
        if (Files.exists(Paths.get(outputFileName))) {
            Files.delete(Paths.get(outputFileName));
        }
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        Configuration configuration = new Configuration();
        FileSystem fs = FileSystem.get(configuration);
        SequenceFile.Writer writer = new SequenceFile.Writer(fs, configuration, path, LongWritable.class, Text.class);
        String line;

        String[] temp;
        StringBuilder tempValue = new StringBuilder();
        LongWritable key;
        Text value;
        String delimiter = " ";
        long tempKey = 0;

        while ((line = br.readLine()) != null) {
            tempKey++;
            temp = line.split(delimiter);
            key = new LongWritable(tempKey);
            for (int i = 0; i < temp.length; i++) {
                tempValue.append(temp[i]).append(delimiter);
            }
            value = new Text(tempValue.toString());
            tempValue.setLength(0);
            writer.append(key, value);
        }
        writer.close();
        br.close();
        fs.close();
    }

    public void decryptionSequency() throws IOException {
        Path path = new Path(outputFileName);
        FileWriter writer = new FileWriter(targetFileName);
        String newLine = System.getProperty("line.separator");
        PrintWriter pw = new PrintWriter(writer);
        pw.print("key,value" + newLine);

        Configuration configuration = new Configuration();
        FileSystem fs = FileSystem.get(configuration);
        SequenceFile.Reader reader = new SequenceFile.Reader(fs,path,configuration);

        LongWritable key = new LongWritable();
        Text value = new Text();
        while (reader.next(key,value)){
            pw.write(String.format("%s,%s%s",key.toString(),value.toString(),newLine));
        }

        reader.close();
        pw.close();
        writer.close();
    }
}
