package com.xjj.cms.base.util.progress;

import java.util.HashMap;
import java.util.Map;

/**
 * ProgressPool.java 进度池，记录多个进度条
 * Created on yeyunfeng 2015-12-29 16:46
 * V1.0.0 yeyunfeng 2015-12-29 16:46 初始版
 * 修改记录：修改内容 作者 修改时间
 * Copyright (c) 2015 海南新境界软件有限公司 版权所有
 */
public class ProgressPool {

    private static ProgressPool pool = null;

    public static ProgressPool getInstance() {
        if (null == pool) {
            pool = new ProgressPool();
        }
        return pool;
    }

    private static Map<String, Progress> progressPool = new HashMap<>();

    public  void add(String key, Progress progress) {
        progressPool.put(key, progress);
    }

    public Progress get(String key) {
        Progress progress = progressPool.get(key);
        return progress;
    }

    public  void remove(String key) {
        progressPool.remove(key);
    }

    public  int getSize() {
        return progressPool.size();
    }


    public static Map<String, Progress> getProgressPool() {
        return progressPool;
    }

    public static void  main(String arg[]){
       Progress progress = new Progress();
       // progress.setName("123456");
        progress.setBytesRead(4324234);
        ProgressPool.getInstance().add("1",progress);
      //  System.out.println(ProgressPool.getInstance().get("1").toString());

        Progress progress1 = new Progress();
        progress1.setBytesRead(11111);
        ProgressPool.getInstance().add("1",progress1);

       // Progress progress1 =  ProgressPool.getInstance().get("1");
      //  progress1.setName("vvvvv");
       System.out.println(ProgressPool.getInstance().getSize());
   }
}
