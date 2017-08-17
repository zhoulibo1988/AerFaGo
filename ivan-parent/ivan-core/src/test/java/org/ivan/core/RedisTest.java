package org.ivan.core;

import org.ivan.core.redis.RedisDBUtil;

public class RedisTest {
	public static void main(String[] args) {
		RedisDBUtil.redisDao.setString("1", "周立波");
		String a=RedisDBUtil.redisDao.getString("1");
		System.out.println(a);
	}
}
