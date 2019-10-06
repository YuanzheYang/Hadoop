self defined class

access.log
    第二个字段： 手机号
        倒数第三个：上行流量
        倒数第二个：下行流量
    需求：统计每个手机号上行流量和、下行流量和、总的流量和（上行流量+ 下行流量）

    Access.java
        手机号、上行流量、下行流量、总流量

    Mapper：       根据手机号进行分组，把该手机号对应的上下行流量加起来
    把手机号作为key，把access作为value写出去

    Reducer: (137262238888,<Access, Access, Access>)

    public class HashPartitioner<K, V> extends Partitioner<K, V> {
        public HashPartitioner() {
        }

        public int getPartition(K key, V value, int numReduceTasks) {
            return (key.hashCode() & 2147483647) % numReduceTasks;
        }
    }
    nunmReduceTasks:作业指定的reducer的个数，决定了reduce作业输出文件的个数
    HashPartitioner 是mapreduce 默认的分区规则
    reducer个数 ： 3
    1 % 3  = 1
    2 % 3 = 2
    3 % 3 = 0

    需求：将统计结果按照手机号的前缀进行区分，并输出到不同的输出文件中去

    13 * => ..
    15 * =>
    other ==>

    Partition决定maptask输出的数据交由哪个reducetask处理
    默认实现：分发的key的hashvalue与reduce task个数取模