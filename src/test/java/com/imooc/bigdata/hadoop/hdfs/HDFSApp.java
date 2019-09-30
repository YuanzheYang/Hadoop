package com.imooc.bigdata.hadoop.hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.util.Progressable;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.net.URI;

/*
*       使用Java API操作HDFS系统
* */
public class HDFSApp {

    public static final String HDFS_PATH = "hdfs://localhost:8020";
    FileSystem fileSystem = null;
    Configuration configuration = null;

    @Before
    public void setUp() throws Exception{
        System.out.println("--------------Set Up---------------");
        configuration = new Configuration();
        /*
        *   构造一个访问指定HDFS系统的客户端对象
        *   第一个参数：HDFS的URI
        *   第二个参数：客户端 指定 的配置参数
        *   第三个参数： 客户端身份：用户名
        *
        * */
        fileSystem = FileSystem.get(new URI(HDFS_PATH), configuration,"allenyang");
    }

    /*
    *   创建HDFS文件夹
    *
    * */
    @Test
    public void mkdir() throws Exception{
        fileSystem.mkdirs(new Path("/hdfsapi/test"));
    }
    @Test
    public void test() throws  Exception{
        FSDataInputStream in = fileSystem.open(new Path("/cdh_version.properties"));
        IOUtils.copyBytes(in, System.out,1024);
    }

    @Test
    public void create() throws Exception{
        FSDataOutputStream out = fileSystem.create(new Path("/hdfsapi/test/a.txt"));
        out.writeUTF("hellp allen");
        out.flush();
        out.close();
    }
    @Test
    public void  rename() throws Exception{
        Path oldPath = new Path("/hdfsapi/test/a.txt");
        Path newPath = new Path("/hdfsapi/test/b.txt");
        boolean result =  fileSystem.rename(oldPath, newPath);
        System.out.println(result);
    }
    /*
    *   拷贝本地文件到HDFS系统
    * */
    @Test
    public void copyFromLocalFile() throws Exception{
        Path src =  new Path("/Users/allenyang/hello.txt");
        Path dst =  new Path("/hdfsapi/test/");
        fileSystem.copyFromLocalFile(src, dst);

    }
    @Test
    public void copyFromLocalBigFile() throws Exception{
        InputStream in =  new BufferedInputStream(new FileInputStream(new File("/Users/allenyang/Desktop/apache-jmeter-5.1.1.tgz")));
        FSDataOutputStream out  = fileSystem.create(new Path("/hdfsapi/test/jmeter.tgz"), new Progressable() {
            @Override
            public void progress() {
                System.out.print(".");
            }
        });
        IOUtils.copyBytes(in, out, 4096);
    }


    /*
    *   拷贝到本地
    * */
    @Test
    public void copyToLocalFile() throws Exception{
        Path src = new Path("/hdfsapi/test/hello.txt");
        Path dst = new Path("/Users/allenyang/Documents/CV");
        fileSystem.copyToLocalFile(src, dst);

    }
    /*
    * 查看目标文件下所有文件
    * */
    @Test
    public void listFiles() throws Exception{
        FileStatus[] statuses = fileSystem.listStatus(new Path("/hdfsapi/test"));
        for(FileStatus status :  statuses) {
            String isDir = status.isDirectory() ? "dir" :" file";
            String permission = status.getPermission().toString();
            short replication = status.getReplication();
            long length = status.getLen();
            String path = status.getPath().toString();

            System.out.println(isDir + "\t" + permission +"\t" + replication + "\t" + length + "\t" + path);
        }
    }
    /*递归查看目标文件下的所有文件*/
    @Test
    public void listFilesRecursive() throws Exception{
        RemoteIterator<LocatedFileStatus> files = fileSystem.listFiles(new Path("/hdfsapi/test"),true);
        while(files.hasNext()) {
            LocatedFileStatus file = files.next();
            String isDir = file.isDirectory() ? "dir" :" file";
            String permission = file.getPermission().toString();
            short replication = file.getReplication();
            long length = file.getLen();
            String path = file.getPath().toString();

            System.out.println(isDir + "\t" + permission +"\t" + replication + "\t" + length + "\t" + path);
        }
    }
    @Test
    public void getFileBlockLocations() throws Exception{
        FileStatus fileStatus = fileSystem.getFileStatus(new Path("/hdfsapi/test/jmeter.tgz"));
        BlockLocation[]  blocks = fileSystem.getFileBlockLocations(fileStatus, 0, fileStatus.getLen());
        for(BlockLocation block : blocks) {
            for(String name : block.getNames()) {
                System.out.println(name  + ":" + block.getOffset()+ ":" + block.getLength());
            }
        }
    }

    @Test
    public void delete() throws Exception{
        boolean result = fileSystem.delete(new Path("/hdfsapi/test/jmeter.tgz"), true);
        System.out.println(result);
    }
    @After
    public void tearDown(){
        configuration =  null;
        fileSystem = null;
        System.out.println("-------------tearDown------------------");
    }


//    public static void main(String[] args) throws Exception {
//        Configuration configuration = new Configuration();
//        FileSystem fileSystem = FileSystem.get(new URI("hdfs://localhost:8020"), configuration,"allenyang");
//        Path path = new Path("/hdfsapi/test");
//        Boolean result = fileSystem.mkdirs(path);
//        System.out.println(result);
//    }
}
