package org.ivan.core.elasticsearch.test;

import java.util.HashSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.ivan.core.elasticsearch.EsUtil;
import org.ivan.core.elasticsearch.Page;
import org.junit.Test;
import com.google.common.collect.Sets;
public class MyTest {
//	@Test
	public void  testlink()throws Exception{
		boolean  isOk=	EsUtil.validate();
		System.out.println(isOk);
		
	}
	//添加文档
//	@Test
	public void testAdd()throws Exception{
		String type="user";
		int id=6;
		User user=new User();
		user.setAddress("深圳");
		user.setAge(25);
		user.setId(6);
		user.setName("6娃");
		user.setSex("女");
		EsUtil.addDoc(type, id, user);
	}
//	@Test
	public void testgetone() throws Exception {
		HashSet<String> set = Sets.newHashSet();
		set.add("sex");
//		set.add("telephone");
		Page page = EsUtil.getDocHighLight("女", "user", set, 1, 10, true);
		System.out.println(page.getRecordList());
	}

	// 并发测试
//	@Test
	public void testget() throws Exception {
		HashSet<String> set = Sets.newHashSet();
		set.add("name");

		ExecutorService pool = Executors.newFixedThreadPool(10);
		for (int i = 0; i < 500; i++) {
			pool.execute(new Runnable() {
				@Override
				public void run() {
					Page page = EsUtil.getDocHighLight("娃", "user", set, 1, 10, true);
					System.err.println("搜索成功条数为:" + page.getRecordCount());
				}
			});
		}
		pool.shutdown();
		while (!pool.awaitTermination(10000, TimeUnit.MILLISECONDS)) {
			System.out.println("执行超时！！！！！！");
			pool.shutdownNow();
		}
	}

//	@Test
	public void testreindex() throws Exception {
		EsUtil.reindex();
	}
//	@Test
	public void addindexName() throws Exception {
		EsUtil.addIndex("zlb");
	}
	@Test
	public void getWithId() throws Exception {
		String info=	EsUtil.getWithId("zhoulibo", "user", "1");
		System.out.println(info);
	}
}
