package com.imooc.bigdata.hadoop.hdfs;

public class CaseIgnoreWordCountMapper implements Mapper{
    public void map(String line, AnalyseContext context) {
        String[] words = line.toLowerCase().split("\t");
        for(String word : words) {
            Object value = context.get(word);
            if(value == null) {
                context.write(word, 1);
            }else{
                int v = Integer.parseInt(value.toString());
                context.write(word, v + 1);
            }
        }
    }
}
