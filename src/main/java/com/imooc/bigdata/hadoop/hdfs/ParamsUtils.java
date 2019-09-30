package com.imooc.bigdata.hadoop.hdfs;

import java.io.IOException;
import java.util.Properties;

/*
* read the property file
* */
public class ParamsUtils {
    private static Properties properties = new Properties();
    static {
        try {
            properties.load(ParamsUtils.class.getClassLoader().getResourceAsStream("wc.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static Properties getProperties() throws Exception{
        return properties;
    }

    public static void main(String[] args) {

    }
}
