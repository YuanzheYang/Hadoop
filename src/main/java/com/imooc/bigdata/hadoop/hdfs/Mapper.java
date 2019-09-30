package com.imooc.bigdata.hadoop.hdfs;

/**
 *
 */
public interface Mapper {
    /*
    * @param line
    * @param context/cache
    * */
    public void map(String line, AnalyseContext context);
}
