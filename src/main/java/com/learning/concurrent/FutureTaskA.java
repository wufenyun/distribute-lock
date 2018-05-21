package com.learning.concurrent;

import java.util.concurrent.Callable;

public class FutureTaskA implements Callable<String>{

    private String taskName;
    
    public FutureTaskA(String taskName) {
        this.taskName = taskName;
    }
    
    @Override
    public String call() throws Exception {
        System.out.println("task " + taskName + ": is running");
        //Thread.sleep(3000);
        return taskName+System.currentTimeMillis();
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }
}
