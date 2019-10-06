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
