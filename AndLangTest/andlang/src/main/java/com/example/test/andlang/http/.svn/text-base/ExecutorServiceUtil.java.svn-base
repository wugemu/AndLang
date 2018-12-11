package com.example.test.andlang.http;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by 1 on 2018/3/1.
 */

public class ExecutorServiceUtil {

    private int NUMBER_OF_CORES= Runtime.getRuntime().availableProcessors();
    private int KEEP_ALIVE_TIME=1;
    private TimeUnit KEEP_ALIVE_TIME_UNIT= TimeUnit.SECONDS;
    private BlockingQueue<Runnable> taskQueue=new LinkedBlockingQueue<Runnable>();
    private ExecutorService executorService;
    private static ExecutorServiceUtil instence;
    private ExecutorServiceUtil() {
        executorService=new ThreadPoolExecutor(NUMBER_OF_CORES,NUMBER_OF_CORES*2,KEEP_ALIVE_TIME,KEEP_ALIVE_TIME_UNIT,taskQueue);
    }

    public static ExecutorServiceUtil getInstence(){
        if(instence==null){
            instence=new ExecutorServiceUtil();
        }
        return instence;
    }
    public void execute(Runnable task){
        if(executorService!=null){
            executorService.execute(task);
        }
    }

    public ExecutorService getExecutorService() {
        return executorService;
    }
}
