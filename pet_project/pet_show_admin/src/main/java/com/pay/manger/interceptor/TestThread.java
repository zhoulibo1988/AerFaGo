package com.pay.manger.interceptor;

public class TestThread extends Thread{
	private int num = 5;
	private String  name;
	public TestThread(String name){
		this.name=name;
	}
	public void run(){
		for (int i = 0; i < 7; i++) {
			if(num>0){
				System.out.println(name+"正在卖票  "+"剩余票数num= " + num--);
			}
		}
	}
	public static void main(String[] args) {
		TestThread h1 = new TestThread("窗口1");
		TestThread h2 = new TestThread("窗口2"); 
		TestThread h3 = new TestThread("窗口3");
		h1.start();
		h2.start();
		h3.start();
	}
}
