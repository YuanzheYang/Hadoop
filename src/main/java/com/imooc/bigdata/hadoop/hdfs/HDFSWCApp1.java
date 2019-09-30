package com.imooc.bigdata.hadoop.hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;



public class HDFSWCApp1 {
    public static void main(String[] args) throws Exception{

        Properties properties = ParamsUtils.getProperties();
        // 1）Read files from hdfs
        Path input = new Path(properties.getProperty(Constants.INPUT_PATH));
        FileSystem fs= FileSystem.get(new URI(properties.getProperty(Constants.HDFS_URI)), new Configuration(), "allenyang");
        RemoteIterator<LocatedFileStatus> iterator = fs.listFiles(input, false);
        //TODO 通过反射创建对象
        Class<?> clazz = Class.forName(properties.getProperty(Constants.MAPPER_CLASS));
        Mapper mapper = (Mapper)clazz.newInstance();
        //Mapper mapper = new WordCountMapper();
        AnalyseContext context = new AnalyseContext();

        while(iterator.hasNext()) {
            LocatedFileStatus file = iterator.next();
            FSDataInputStream in = fs.open(file.getPath());
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            String line = "";
            while((line = reader.readLine()) != null) {
                mapper.map(line, context);
            }
            reader.close();
            in.close();
        }

        Map<Object,Object> contextMap = context.getCacheMap();
        Path output = new Path(properties.getProperty(Constants.OUTPUT_PATH));
        FSDataOutputStream out  = fs.create(new Path(output, new Path(properties.getProperty(Constants.OUTPUT_FILE))));

        //TODO 第三步缓存中的内容输出到out去
        Set<Map.Entry<Object, Object>> entries =  contextMap.entrySet();
        for(Map.Entry<Object, Object> entry : entries) {
            out.write((entry.getKey() + "\t" + entry.getValue() + "\n").getBytes());
        }
        out.close();
        fs.close();
        System.out.println("running success");
    }
}
