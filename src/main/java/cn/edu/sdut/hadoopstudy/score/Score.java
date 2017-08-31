/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.edu.sdut.hadoopstudy.score;

import java.io.IOException;

import java.util.Iterator;

import java.util.StringTokenizer;
import org.apache.hadoop.conf.Configuration;

import org.apache.hadoop.fs.Path;

import org.apache.hadoop.io.IntWritable;

import org.apache.hadoop.io.LongWritable;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.TextInputFormat;
import org.apache.hadoop.mapred.TextOutputFormat;
import org.apache.hadoop.mapreduce.InputFormat;
import org.apache.hadoop.mapreduce.Job;

import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.OutputFormat;

import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

/**
 *
 * @author gaoziqiang
 */
public class Score {

    private Configuration getConf() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public static class Map extends
            Mapper<LongWritable, Text, Text, IntWritable> {

        // 实现map函数
        public void map(LongWritable key, Text value, Context context)
                throws IOException, InterruptedException {

            // 将输入的纯文本文件的数据转化成String
            String line = value.toString();

            // 将输入的数据首先按行进行分割
            StringTokenizer tokenizerArticle = new StringTokenizer(line, "\n");

            // 分别对每一行进行处理
            while (tokenizerArticle.hasMoreElements()) {

                // 每行按空格划分
                StringTokenizer tokenizerLine = new StringTokenizer(tokenizerArticle.nextToken());

                String strName = tokenizerLine.nextToken();// 学生姓名部分

                String strScore = tokenizerLine.nextToken();// 成绩部分

                Text name = new Text(strName);

                int scoreInt = Integer.parseInt(strScore);

                // 输出姓名和成绩
                context.write(name, new IntWritable(scoreInt));

            }

        }

    }

    public static class Reduce extends
            Reducer<Text, IntWritable, Text, IntWritable> {

        // 实现reduce函数
        public void reduce(Text key, Iterable<IntWritable> values,
                Context context) throws IOException, InterruptedException {

            int sum = 0;

            int count = 0;

            Iterator<IntWritable> iterator = values.iterator();

            while (iterator.hasNext()) {

                sum += iterator.next().get();// 计算总分

                count++;// 统计总的科目数

            }

            int average = (int) sum / count;// 计算平均成绩

            context.write(key, new IntWritable(average));

        }

    }

    public int run(String[] args) throws Exception {
        Job job = new Job(getConf());
        job.setJarByClass(Score.class);
        job.setJobName("score");
        
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        
        job.setMapperClass(Map.class);
        job.setCombinerClass(Reduce.class);
        job.setReducerClass(Reduce.class);
        
        job.setInputFormatClass(InputFormat.class);
        job.setOutputFormatClass(OutputFormat.class);
        
        FileInputFormat.setInputPaths(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        
        boolean success = job.waitForCompletion(true);
        
        return success ? 0 : 1;
    }
    
    public static void main(String[] args) throws Exception {
        int set = ToolRunner.run((Tool) new Score(), args);
        System.exit(set);
    }

}
