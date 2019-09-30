package com.imooc.bigdata.hadoop.hdfs;

import java.util.HashMap;
import java.util.Map;

public class AnalyseContext {
    private Map<Object,Object>  cacheMap = new HashMap<>();

    public Map<Object, Object> getCacheMap(){
        return cacheMap;
    }
    /*
    * writing data to cache
    * */
    public void write(Object key,Object value) {
        cacheMap.put(key, value);
    }

    /*
    * 从缓存中获取值
    * */
    public Object get(Object key) {
        return cacheMap.get(key);
    }
}

