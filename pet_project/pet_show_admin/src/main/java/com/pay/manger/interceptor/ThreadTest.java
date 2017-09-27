package com.pay.manger.interceptor;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ThreadTest {
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		ScheduledExecutorService executorService = Executors.newScheduledThreadPool(2);
		MyCallable callable = new MyCallable(2);
		executorService.schedule(callable, 10, TimeUnit.SECONDS);
		executorService.shutdown();
	}
}

class MyCallable implements Callable<Integer> {
	private int num;

	public MyCallable(int num) {
		this.num = num;
	}

	@Override
	public Integer call() throws Exception {
		System.out.println(Thread.currentThread().getName() + " is running");
		return num * 2;
	}
}
