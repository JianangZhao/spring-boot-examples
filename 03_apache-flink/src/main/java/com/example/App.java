package com.example;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception {
//        batchWordcount();
        streamWordCOunt(args);
    }

    private static void batchWordcount() throws Exception {
        // 创建执行环境
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

        // 从文件中读取数据
        String inputPath = "C:\\Users\\zhaojianang\\ideaPorject\\spring-boot-examples\\03_apache-flink\\src\\main\\resources\\word.txt";
        DataSet<String> inputDataSet = env.readTextFile(inputPath);

        // 对数据集进行处理，按空格分词展开，转换成(word,1)二元组进行统计
        inputDataSet.flatMap(new FlatMapFunction<String, Tuple2<String, Integer>>() {
                    @Override
                    public void flatMap(String value, Collector<Tuple2<String, Integer>> out) throws Exception {
                        for (String word : value.split(" ")) {
                            out.collect(new Tuple2<>(word, 1));
                        }
                    }
                }).groupBy(0) // 按照第一个位置的word分组
                .sum(1) // 将第二个位置上的数据求和
                .print();
    }

    private static void streamWordCOunt(String[] args) throws Exception {
        // 创建流式处理环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // 用parameter tool工具从程序启动参数中提取配置项
        ParameterTool parameterTool = ParameterTool.fromArgs(args);
        // nc -lk 7777
        // --host hadoop102 --port 7777
        String hostname = parameterTool.get("host", "localhost");
        int port = parameterTool.getInt("port", 7777);

        DataStream<String> inputDataSet = env.socketTextStream(hostname, port);

        // 基于数据流进行转换计算
        inputDataSet.flatMap(new FlatMapFunction<String, Tuple2<String, Integer>>() {
                    @Override
                    public void flatMap(String value, Collector<Tuple2<String, Integer>> out) throws Exception {
                        for (String word : value.split(" ")) {
                            out.collect(new Tuple2<>(word, 1));
                        }
                    }
                }).keyBy(0)
                .sum(1)
                .print();

        // 执行任务
        env.execute();
    }
}
