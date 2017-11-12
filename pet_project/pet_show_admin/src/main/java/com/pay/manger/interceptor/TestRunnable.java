package com.pay.manger.interceptor;

public class TestRunnable implements Runnable {
	private int num = 5;

	@Override
	public void run() {
		for (int i = 0; i < 7; i++) {
			synchronized (this) {
				if (num > 0) {
					try {
						Thread.sleep(1000);
					} catch (Exception e) {

					}
					System.out.println(Thread.currentThread().getName() + "正在卖票" + this.num--);
				}
			}
		}
	}

	public static void main(String[] args) {
		TestRunnable a1 = new TestRunnable();
		new Thread(a1, "窗口1").start();
		new Thread(a1, "窗口2").start();
		new Thread(a1, "窗口3").start();
	}
}
