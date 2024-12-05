package com.example;

import java.util.*;
import java.util.concurrent.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class FutureTaskDemo {
    public static void main(String[] args) {
        //假设我有一个刷题模块，有题目分类和题目，现在我需要找出对应分类的题目是什么
        //list表示在数据库里面有这些分类和题目，这两行代码模拟业务调用数据的操作。
        List<SubjectCategoryBo> subjectCategoryBoList = getSubjectCategoryList();
        List<SubjectBo> subjectBoList = getSubjectList();
        //创建一个线程
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 20, 5,
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(40),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.CallerRunsPolicy());
        //map结果集
        Map<String, List<SubjectBo>> map = new HashMap<>();
        List<FutureTask<Map<String, List<SubjectBo>>>> futureTaskList = new LinkedList<>();
        //遍历分类
        subjectCategoryBoList.forEach( subjectCategoryBo -> {
            //getSubjectBOList表示业务逻辑
            FutureTask<Map<String, List<SubjectBo>>> futureTask = new FutureTask<>(() -> getSubjectBOList(subjectCategoryBo, subjectBoList));
            //休眠是为了打印的时候更好观察，模拟业务执行的时间。
            try{
                Thread.sleep(1000);
            }catch (InterruptedException e){
                throw new RuntimeException(e);
            }
            //futureTaskList线程集合
            futureTaskList.add(futureTask);
            //futureTask任务加入线程池
            threadPoolExecutor.submit(futureTask);
            System.out.println("线程池中线程数目：" + threadPoolExecutor.getPoolSize());
        });
        //遍历futureTaskList，获取结果存入map
        for (FutureTask<Map<String, List<SubjectBo>>> mapFutureTask : futureTaskList) {
            try {
                Map<String, List<SubjectBo>> stringListMap = mapFutureTask.get();
                if (stringListMap.isEmpty()){
                    continue;
                }
                map.putAll(stringListMap);
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        //结果输出s
        map.forEach((k,v) -> {
            System.out.println(k+"的题目有:");
            v.forEach(subjectBo -> {
                System.out.println(subjectBo.getSubjectName());
            });
        });

        threadPoolExecutor.shutdown();
        try {
            threadPoolExecutor.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static Map<String, List<SubjectBo>> getSubjectBOList(SubjectCategoryBo subjectCategoryBo, List<SubjectBo> subjectBoList) {
        Map<String, List<SubjectBo>> map = new HashMap<>();
        List<SubjectBo> subjectBOListAns = new ArrayList<>();
        subjectBoList.forEach(subjectBo -> {
            if (subjectBo.getParentLableId().equals(subjectCategoryBo.getSubjectCategoryId())){
                subjectBOListAns.add(subjectBo);
            }
        });
        map.put(subjectCategoryBo.getSubjectCategoryName(), subjectBOListAns);
        try {
            Thread.sleep(5000);
            System.out.println("线程执行完毕");
        } catch (InterruptedException e){
            throw new RuntimeException(e);
        }
        return map;
    }

    private static List<SubjectBo> getSubjectList() {
        List<SubjectBo> subjectBOList = new ArrayList<>();
        subjectBOList.add(new SubjectBo(1,"讲讲Java的继承"));
        subjectBOList.add(new SubjectBo(1,"讲讲Java的多态"));
        subjectBOList.add(new SubjectBo(2,"讲讲Spring"));
        return subjectBOList;
    }

    private static List<SubjectCategoryBo> getSubjectCategoryList() {
        List<SubjectCategoryBo> subjectCategoryBOList = new ArrayList<>();
        subjectCategoryBOList.add(new SubjectCategoryBo(1,"Java"));
        subjectCategoryBOList.add(new SubjectCategoryBo(2,"Spring"));
        return subjectCategoryBOList;
    }
}