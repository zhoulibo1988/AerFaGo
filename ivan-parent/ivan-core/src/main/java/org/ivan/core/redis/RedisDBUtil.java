package org.ivan.core.redis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;
import org.ivan.entity.utils.ReadPro;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisPubSub;
import redis.clients.jedis.JedisSentinelPool;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;
import redis.clients.jedis.SortingParams;
import redis.clients.jedis.Transaction;
import redis.clients.jedis.Tuple;
import redis.clients.util.Pool;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class RedisDBUtil {
	private static final Logger logger = LoggerFactory.getLogger(RedisDBUtil.class);
	/**
	 * 链接池
	 * 
	 * @author zhoulibo
	 * @date 2016年1月11日
	 */
	private  Pool<Jedis> jedisPool;

	/**
	 * 当前的RedisDao的名字
	 * 
	 * @author zhoulibo
	 * @date 2016年1月11日
	 */
	private String redisDaoName;

	private boolean ALIVE = true;

	private boolean EXCEPTION_FALG = false;

	/**
	 * 默认的RedisDao的名字
	 * 
	 * @author zhoulibo
	 * @date 2016年1月11日
	 */
	public static final String DEFAULT_REDIS_DAO_NAME = ReadPro.getValue("redis_sentinel_masterName", "myPro");

	/**
	 * 默认的Redis哨兵连接地址
	 * 
	 * @author zhoulibo
	 * @date 2016年1月11日
	 */
	public static final String DERALUT_REDIS_DAO_SENTINEL = ReadPro.getValue("redis_sentinel_array");

	/**
	 * 默认的redis单服务器的链接地址
	 * 
	 * @author zhoulibo
	 * @date 2016年1月11日
	 */
	public static final String DEFAULT_REDIS_DAO_HOSTNAME = ReadPro.getValue("redis_host");

	/**
	 * 默认的redis单服务器的端口
	 * 
	 * @author zhoulibo
	 * @date 2016年1月11日
	 */
	public static final Integer DEFAULT_REDIS_DAO_HOST_PORT = ReadPro.getValue("redis_port", 6379);

	/**
	 * 默认的Redis连接数据库编号
	 * 
	 * @author zhoulibo
	 * @date 2016年1月11日
	 */
	public static final Integer DEFALUT_REDIS_DAO_DATABASE = ReadPro.getValue("redis_sentinel_database", 0);

	/**
	 * 默认的Redis连接密码
	 * 
	 * @author zhoulibo
	 * @date 2016年1月11日
	 */
	public static final String DEFAULT_REDIS_DAO_PWD = ReadPro.getValue("redis_password");

	private static Map<String, RedisDBUtil> redisDaoMap = initRedisMap();

	public static RedisDBUtil redisDao;

	/**
	 * 根据一个名字获取指定的RedisDao
	 * 
	 * @author zhoulibo
	 * @date 2016年1月11日
	 * @param redisDaoName
	 * @return
	 * @Exception
	 */
	public static RedisDBUtil getRedisDao(String redisDaoName) {
		if (StringUtils.isNotBlank(redisDaoName)) {
			return redisDao;
		} else {
			return redisDaoMap.get(redisDaoName);
		}
	}

	/**
	 * 新建一个通过哨兵连接的RedisDao
	 * 
	 * @author zhoulibo
	 * @date 2016年1月11日
	 * @param redisDaoName
	 *            redisDao的名字
	 * @param serverName
	 *            服务器的名字
	 * @param sentinelArrayStr
	 *            哨兵的链接地址
	 * @param database
	 *            使用哪个库
	 * @param pwd
	 *            密码
	 * @return
	 * @Exception
	 */
	public static RedisDBUtil addNewRedisDaoBySentinel(String redisDaoName, String serverName, String sentinelArrayStr, Integer database, String pwd) {
		RedisDBUtil newRedisDao = new RedisDBUtil(redisDaoName, serverName, sentinelArrayStr, database, pwd);
		redisDaoMap.put(newRedisDao.getRedisDaoName(), newRedisDao);
		return newRedisDao;
	}

	/**
	 * 新建一个单服务器连接的RedisDao
	 * 
	 * @author zhoulibo
	 * @date 2016年1月11日
	 * @param redisDaoName
	 *            redisDao的名字
	 * @param redisHost
	 *            服务器的连接
	 * @param database
	 *            使用哪个库
	 * @param pwd
	 *            密码
	 * @return
	 * @Exception
	 */
	public static RedisDBUtil addNewRedisDaoByOneHost(String redisDaoName, String redisHost, Integer database, String pwd, Integer port) {
		RedisDBUtil newRedisDao = new RedisDBUtil(redisDaoName, redisHost, database, pwd, port);
		redisDaoMap.put(newRedisDao.getRedisDaoName(), newRedisDao);
		return newRedisDao;
	}

	/**
	 * 初始化RedisDao的容器Map，同时初始化一个连接
	 * 
	 * @author zhoulibo
	 * @date 2016年1月11日
	 * @return
	 * @Exception
	 */
	private static Map<String, RedisDBUtil> initRedisMap() {
		redisDaoMap = new ConcurrentHashMap<String, RedisDBUtil>();
		if (StringUtils.isNotBlank(DERALUT_REDIS_DAO_SENTINEL)) {
			redisDao = new RedisDBUtil(DEFAULT_REDIS_DAO_NAME, DEFAULT_REDIS_DAO_NAME, DERALUT_REDIS_DAO_SENTINEL, DEFALUT_REDIS_DAO_DATABASE, DEFAULT_REDIS_DAO_PWD);// 初始化默认的Redis哨兵连接
		} else if (StringUtils.isNotBlank(DEFAULT_REDIS_DAO_HOSTNAME)) {
			redisDao = new RedisDBUtil(DEFAULT_REDIS_DAO_NAME, DEFAULT_REDIS_DAO_HOSTNAME, DEFALUT_REDIS_DAO_DATABASE, DEFAULT_REDIS_DAO_PWD, DEFAULT_REDIS_DAO_HOST_PORT);
		}
		redisDaoMap.put(redisDao.getRedisDaoName(), redisDao);
		return redisDaoMap;
	}

	/**
	 * 初始化一个使用哨兵连接的Redis
	 */
	private RedisDBUtil(String redisDaoName, String serverName, String sentinelArrayStr, Integer database, String pwd) {
		setRedisDaoName(redisDaoName);
		setJedisPool(getPoolBySentinel(serverName, sentinelArrayStr, database, pwd));
	}

	/**
	 * 初始化一个普通的Redis连接
	 * 
	 * @author zhoulibo
	 * @date 2016年1月11日
	 */
	private RedisDBUtil(String redisDaoName, String redisHost, Integer database, String pwd, Integer port) {
		setRedisDaoName(redisDaoName);
		setJedisPool(getPoolByOneHost(redisHost, database, pwd, port));
	}

	/**
	 * 得到Redis的连接配置
	 * 
	 * @author zhoulibo
	 * @date 2016年1月11日
	 * @return
	 * @Exception
	 */
	private static JedisPoolConfig getConfig() {
		JedisPoolConfig config = new JedisPoolConfig();
		// 控制一个pool可分配多少个jedis实例，通过pool.getResource()来获取；
		// 如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
		config.setMaxTotal(ReadPro.getValue("redis.maxTotal", 10000));
		// 控制一个pool最多有多少个状态为idle(空闲的)的jedis实例。
		config.setMaxIdle(ReadPro.getValue("redis.maxIdle", 10000));
		// 表示当borrow(引入)一个jedis实例时，最大的等待时间，如果超过等待时间，则直接抛出JedisConnectionException；
		config.setMaxWaitMillis(ReadPro.getValue("redis.maxWait", 1000 * 5));
		// 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
		config.setTestOnBorrow(ReadPro.getValue("redis.testOnBorrow", true));
		// 在return一个jedis实例时，是否提前进行validate操作
		config.setTestOnReturn(ReadPro.getValue("redis.testOnReturn", true));
		return config;
	}

	private Pool<Jedis> getPoolBySentinel(String serverName, String sentinelArrayStr, Integer database, String pwd) {
		Pool<Jedis> pool = null;
		if (pool == null) {
			JedisPoolConfig config = getConfig();
			Set<String> sentinels = new HashSet<String>();
			List<JSONObject> sentinelList = JSONArray.parseArray(sentinelArrayStr, JSONObject.class);
			for (JSONObject sentinel : sentinelList) {
				sentinels.add(new HostAndPort(sentinel.getString("address"), sentinel.getIntValue("port")).toString());
			}
			pool = new JedisSentinelPool(serverName, sentinels, config, 100000, pwd, database);
		}
		return pool;
	}

	private Pool<Jedis> getPoolByOneHost(String redisHost, Integer database, String pwd, Integer port) {
		JedisPoolConfig config = getConfig();
		Pool<Jedis> pool = new JedisPool(config, redisHost, port, 100000, pwd, database);
		return pool;
	}

	public class MonitorThread extends Thread {

		public void run() {
			int sleepTime = 30000;
			int baseSleepTime = 1000;
			while (true) {
				try {
					// 30秒执行监听
					int n = sleepTime / baseSleepTime;
					for (int i = 0; i < n; i++) {
						if (EXCEPTION_FALG) {// 检查到异常，立即进行检测处理
							break;
						}
						Thread.sleep(baseSleepTime);
					}
					// 连续做3次连接获取
					int errorTimes = 0;
					for (int i = 0; i < 3; i++) {
						try {
							Jedis jedis = jedisPool.getResource();
							if (jedis == null) {
								errorTimes++;
								continue;
							}
							returnConnection(jedis);
							break;
						} catch (Exception e) {
							errorTimes++;
							continue;
						}
					}
					if (errorTimes == 3) {// 3次全部出错，表示服务器出现问题
						ALIVE = false;
						logger.error("redis[" + redisDaoName + "] 服务器连接不上！ ！ ！");
						// 修改休眠时间为5秒，尽快恢复服务
						sleepTime = 10000;
						sleep(sleepTime);
					} else {
						if (ALIVE == false) {
							ALIVE = true;
							// 修改休眠时间为30秒，尽快恢复服务
							sleepTime = 30000;
							logger.error("redis[" + redisDaoName + "] 服务器恢复正常！ ！ ！");
						}
						EXCEPTION_FALG = false;
						Jedis jedis = jedisPool.getResource();
						// LogUtil.fileInfoLog("redis[" + serverName +
						// "] 当前记录数：" + jedis.dbSize());
						returnConnection(jedis);
					}
				} catch (Exception e) {
				}
			}
		}
	}

	/**
	 * 设置连接池
	 * 
	 * @param JedisPool
	 */
	public void setJedisPool(Pool<Jedis> JedisPool) {
		this.jedisPool = JedisPool;
		// 启动监听线程
		new MonitorThread().start();
	}

	/**
	 * 获取连接池
	 * 
	 * @return 数据源
	 */
	public Pool<Jedis> getJedisPool() {
		return jedisPool;
	}

	public String getRedisDaoName() {
		return redisDaoName;
	}

	public void setRedisDaoName(String redisDaoName) {
		this.redisDaoName = redisDaoName;
	}

	/**
	 * 判断服务器是否活动
	 * 
	 * @return
	 */
	public boolean isServerAlive() {
		return ALIVE;
	}

	/**
	 * 获取连接
	 * 
	 * @return
	 */
	public Jedis getConnection() {
		Jedis jedis = null;
		int c = 5;
		while (c > 0 && jedis == null) {
			try {
				if (ALIVE) {// 当前状态为活跃才获取连接，否则直接返回null
					jedis = jedisPool.getResource();
				}
			} catch (Exception e) {
				e.printStackTrace();
				EXCEPTION_FALG = true;
			}
			c--;
		}
		return jedis;
	}

	/**
	 * 关闭数据库连接
	 * 
	 * @param jedis
	 */
	public void returnConnection(Jedis jedis) {
		if (null != jedis) {
			try {
				jedisPool.returnResource(jedis);
			} catch (Exception e) {
				jedisPool.returnBrokenResource(jedis);
			}
		}
	}

	/**
	 * 关闭错误连接
	 * 
	 * @param jedis
	 */
	public void returnBorkenConnection(Jedis jedis) {
		if (null != jedis) {
			jedisPool.returnBrokenResource(jedis);
		}
	}

	/**
	 * 设置key-value失效时间，字符串类型key
	 * 
	 * @param key
	 * @param seconds
	 * @return
	 */
	public long expire(String key, int seconds) {
		Jedis conn = getConnection();
		if (conn == null) {
			return -1;
		}
		try {
			long result = conn.expire(key, seconds);
			returnConnection(conn);
			return result;
		} catch (Exception e) {
			returnBorkenConnection(conn);
			throw new RuntimeException(e);
		}
	}

	// public Long del(String key) {
	// Jedis conn = getConnection();
	// if (conn == null) {
	// return -1l;
	// }
	// try {
	// long result = conn.del(key);
	// returnConnection(conn);
	// return result;
	// } catch (Exception e) {
	// returnBorkenConnection(conn);
	// }
	// return -1l;
	// }

	public Set<String> keys(String key) {
		Jedis conn = getConnection();
		if (conn == null) {
			return null;
		}
		try {
			Set<String> result = conn.keys(key);
			returnConnection(conn);
			return result;
		} catch (Exception e) {
			returnBorkenConnection(conn);
			throw new RuntimeException(e);
		}
	}

	public Set<String> hkeys(String region) {
		Jedis conn = getConnection();
		if (conn == null) {
			return null;
		}
		try {
			Set<String> result = conn.hkeys(region);
			returnConnection(conn);
			return result;
		} catch (Exception e) {
			returnBorkenConnection(conn);
			throw new RuntimeException(e);
		}
	}

	public Long del(String... key) {
		Jedis conn = getConnection();
		if (conn == null) {
			return -1l;
		}
		try {
			long result = conn.del(key);
			returnConnection(conn);
			return result;
		} catch (Exception e) {
			returnBorkenConnection(conn);
			throw new RuntimeException(e);
		}
	}

	public Long del(byte[] key) {
		Jedis conn = getConnection();
		if (conn == null) {
			return -1l;
		}
		try {
			long result = conn.del(key);
			returnConnection(conn);
			return result;
		} catch (Exception e) {
			returnBorkenConnection(conn);
			throw new RuntimeException(e);
		}
	}

	/**
	 * 设置key-value失效时间，字节类型key
	 * 
	 * @param key
	 * @param seconds
	 * @return
	 */
	public long expire(byte[] key, int seconds) {
		Jedis conn = getConnection();
		if (conn == null) {
			return -1;
		}
		try {
			long result = conn.expire(key, seconds);
			returnConnection(conn);
			return result;
		} catch (Exception e) {
			returnBorkenConnection(conn);
			throw new RuntimeException(e);
		}
	}

	/**
	 * 检查key是否存在
	 * 
	 * @param key
	 * @return 返回操作后的值
	 */
	public boolean checkKeyExisted(byte[] key) {
		Jedis conn = getConnection();
		if (conn == null) {
			return false;
		}
		boolean result = false;
		try {
			result = conn.exists(key);
			returnConnection(conn);
			return result;
		} catch (Exception e) {
			returnBorkenConnection(conn);
			throw new RuntimeException(e);
		}
	}

	/**
	 * 加1操作
	 * 
	 * @param key
	 * @return 返回操作后的值
	 */
	public long increase(String key) {
		return increase(key, 1);
	}

	/**
	 * 加操作，指定加的量
	 * 
	 * @param key
	 * @param num
	 * @return 返回操作后的值
	 */
	public long increase(String key, int num) {
		Jedis conn = getConnection();
		if (conn == null) {
			return -1;
		}
		try {
			long result = conn.incrBy(key, num);
			returnConnection(conn);
			return result;
		} catch (Exception e) {
			returnBorkenConnection(conn);
			throw new RuntimeException(e);
		}
	}

	/**
	 * 加1操作
	 * 
	 * @param key
	 * @return 返回操作后的值
	 */
	public long increase(byte[] key) {
		return increase(key, 1);
	}

	/**
	 * 加操作，指定加的量
	 * 
	 * @param key
	 * @param num
	 * @return
	 */
	public long increase(byte[] key, int num) {
		Jedis conn = getConnection();
		if (conn == null) {
			return -1;
		}
		try {
			long result = conn.incrBy(key, num);
			returnConnection(conn);
			return result;
		} catch (Exception e) {
			returnBorkenConnection(conn);
			throw new RuntimeException(e);
		}
	}

	/**
	 * 减1操作
	 * 
	 * @param key
	 * @return 返回操作后的值
	 */
	public long decrease(String key) {
		return decrease(key, 1);
	}

	/**
	 * 减操作，指定减的值
	 * 
	 * @param key
	 * @param num
	 * @return 返回操作后的值
	 */
	public long decrease(String key, int num) {
		Jedis conn = getConnection();
		if (conn == null) {
			return -1;
		}
		try {
			long result = conn.decrBy(key, num);
			returnConnection(conn);
			return result;
		} catch (Exception e) {
			returnBorkenConnection(conn);
			throw new RuntimeException(e);
		}
	}

	/**
	 * 减1操作
	 * 
	 * @param key
	 * @return 返回操作后的值
	 */
	public long decrease(byte[] key) {
		return decrease(key, 1);
	}

	/**
	 * 减操作，指定减的值
	 * 
	 * @param key
	 * @param num
	 * @return 返回操作后的值
	 */
	public long decrease(byte[] key, int num) {
		Jedis conn = getConnection();
		if (conn == null) {
			return -1;
		}
		try {
			long result = conn.decrBy(key, num);
			returnConnection(conn);
			return result;
		} catch (Exception e) {
			returnBorkenConnection(conn);
			throw new RuntimeException(e);
		}
	}

	/**
	 * 删除记录
	 * 
	 * @param key
	 * @return
	 */
	public long delete(byte[] key) {
		Jedis conn = getConnection();
		if (conn == null) {
			return -1;
		}
		try {
			long result = conn.del(key);
			returnConnection(conn);
			return result;
		} catch (Exception e) {
			returnBorkenConnection(conn);
			throw new RuntimeException(e);
		}
	}

	/**
	 * 删除记录
	 * 
	 * @param key
	 * @return
	 */
	public long delete(String key) {
		Jedis conn = getConnection();
		if (conn == null) {
			return -1;
		}
		try {
			long result = conn.del(key);
			returnConnection(conn);
			return result;
		} catch (Exception e) {
			returnBorkenConnection(conn);
			throw new RuntimeException(e);
		}
	}

	/**
	 * 设置key-value项，字节类型
	 * 
	 * @param key
	 *            设置key
	 * @param value
	 *            设置值
	 * @param exp
	 *            设置这个值在多久之后过期
	 */
	public void set(byte[] key, byte[] value, int exp) {
		Jedis conn = getConnection();
		if (conn == null) {
			return;
		}
		try {
			if (exp > 0) {
				conn.setex(key, exp, value);
			} else {
				conn.set(key, value);
			}
			returnConnection(conn);
		} catch (Exception e) {
			returnBorkenConnection(conn);
			throw new RuntimeException(e);
		}
	}

	/**
	 * 获取key value
	 * 
	 * @param key
	 * @return
	 */
	public byte[] get(byte[] key) {
		Jedis conn = getConnection();
		if (conn == null) {
			return null;
		}
		try {
			byte[] data = conn.get(key);
			returnConnection(conn);
			return data;
		} catch (Exception e) {
			returnBorkenConnection(conn);
			throw new RuntimeException(e);
		}
	}

	public String get(String key) {
		Jedis conn = getConnection();
		if (conn == null) {
			return null;
		}
		try {
			String data = conn.get(key);
			returnConnection(conn);
			return data;
		} catch (Exception e) {
			returnBorkenConnection(conn);
			throw new RuntimeException(e);
		}
	}

	public boolean setnx(String key, String value) {
		Jedis conn = getConnection();
		if (conn == null) {
			return false;
		}
		try {
			Long data = conn.setnx(key, value);
			returnConnection(conn);
			if (data == 1l) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			returnBorkenConnection(conn);
			throw new RuntimeException(e);
		}
	}

	public String getset(String key, String value) {
		Jedis conn = getConnection();
		if (conn == null) {
			return null;
		}
		try {
			String data = conn.getSet(key, value);
			returnConnection(conn);
			return data;
		} catch (Exception e) {
			returnBorkenConnection(conn);
			throw new RuntimeException(e);
		}
	}

	/**
	 * 获取多个键值
	 * 
	 * @param keys
	 * @return
	 */
	public List<String> mget(List<String> keys) {
		Jedis conn = getConnection();
		if (conn == null) {
			return null;
		}
		List<String> list = null;
		try {
			list = conn.mget(keys.toArray(new String[0]));
			returnConnection(conn);
			return list;
		} catch (Exception e) {
			returnBorkenConnection(conn);
			throw new RuntimeException(e);
		}
	}

	/**
	 * 设置字符串类型缓存项
	 * 
	 * @param key
	 * @param value
	 */
	public void setString(String key, String value) {
		setString(key, value, -1);
	}

	/**
	 * 存储字符串类型缓存项，加入失效时间，单位为秒
	 * 
	 * @param key
	 * @param value
	 * @param exp
	 */
	public void setString(String key, String value, int exp) {
		Jedis conn = getConnection();
		if (conn == null) {
			return;
		}
		try {
			if (exp > 0) {
				conn.setex(key, exp, value);
			} else {
				conn.set(key, value);
			}
			returnConnection(conn);
		} catch (Exception e) {
			returnBorkenConnection(conn);
			throw new RuntimeException(e);
		}
	}

	/**
	 * 获取字符串类型
	 * 
	 * @param key
	 * @return
	 */
	public String getString(String key) {
		Jedis conn = getConnection();
		if (conn == null) {
			return null;
		}
		try {
			String value = conn.get(key);
			returnConnection(conn);
			return value;
		} catch (Exception e) {
			returnBorkenConnection(conn);
			throw new RuntimeException(e);
		}
	}

	/**
	 * 从左边添加到list
	 * 
	 * @param listKey
	 * @param value
	 */
	public void addToListLeft(byte[] listKey, byte[] value) {
		Jedis conn = getConnection();
		if (conn == null) {
			return;
		}
		try {
			conn.lpush(listKey, value);
			returnConnection(conn);
		} catch (Exception e) {
			returnBorkenConnection(conn);
			throw new RuntimeException(e);
		}
	}

	public Long addToListLeft(String listKey, String value) {
		Jedis conn = getConnection();
		if (conn == null) {
			return null;
		}
		try {
			Long l = conn.lpush(listKey, value);
			returnConnection(conn);
			return l;
		} catch (Exception e) {
			returnBorkenConnection(conn);
			throw new RuntimeException(e);
		}
	}

	/**
	 * 从右边添加到list
	 * 
	 * @param listKey
	 * @param value
	 */
	public void addToListRight(byte[] listKey, byte[] value) {
		Jedis conn = getConnection();
		if (conn == null) {
			return;
		}
		try {
			conn.rpush(listKey, value);
			returnConnection(conn);
		} catch (Exception e) {
			returnBorkenConnection(conn);
			throw new RuntimeException(e);
		}
	}

	public int addToListRight(String listKey, String value) {
		Jedis conn = getConnection();
		if (conn == null) {
			return 0;
		}
		try {
			Long l = conn.rpush(listKey, value);
			returnConnection(conn);
			return l.intValue();
		} catch (Exception e) {
			returnBorkenConnection(conn);
			throw new RuntimeException(e);
		}
	}

	/**
	 * 从左边移除一个对象，并返回该对象
	 * 
	 * @param listKey
	 * @return
	 */
	public byte[] popFromListLeft(byte[] listKey) {
		Jedis conn = getConnection();
		if (conn == null) {
			return null;
		}
		try {
			byte[] data = conn.lpop(listKey);
			returnConnection(conn);
			return data;
		} catch (Exception e) {
			returnBorkenConnection(conn);
			throw new RuntimeException(e);
		}
	}

	public String popFromListLeft(String listKey) {
		Jedis conn = getConnection();
		if (conn == null) {
			return null;
		}
		try {
			String data = conn.lpop(listKey);
			returnConnection(conn);
			return data;
		} catch (Exception e) {
			returnBorkenConnection(conn);
			throw new RuntimeException(e);
		}
	}

	/**
	 * 从右边移除一个对象，并返回该对象
	 * 
	 * @param listKey
	 * @return
	 */
	public byte[] popFromListRight(byte[] listKey) {
		Jedis conn = getConnection();
		if (conn == null) {
			return null;
		}
		try {
			byte[] data = conn.rpop(listKey);
			returnConnection(conn);
			return data;
		} catch (Exception e) {
			returnBorkenConnection(conn);
			throw new RuntimeException(e);
		}
	}

	/**
	 * 获取列表长度
	 * 
	 * @param listKey
	 * @return
	 */
	public int getLengthOfList(byte[] listKey) {
		Jedis conn = getConnection();
		if (conn == null) {
			return 0;
		}
		try {
			int length = conn.llen(listKey).intValue();
			returnConnection(conn);
			return length;
		} catch (Exception e) {
			returnBorkenConnection(conn);
			throw new RuntimeException(e);
		}
	}

	public int getLengthOfList(String listKey) {
		Jedis conn = getConnection();
		if (conn == null) {
			return 0;
		}
		try {
			int length = conn.llen(listKey).intValue();
			returnConnection(conn);
			return length;
		} catch (Exception e) {
			returnBorkenConnection(conn);
			throw new RuntimeException(e);
		}
	}

	/**
	 * 获取list某一范围的段
	 * 
	 * @param listKey
	 * @param start
	 * @param size
	 * @return
	 */
	public List<byte[]> getListRange(byte[] listKey, int start, int size) {
		Jedis conn = getConnection();
		if (conn == null) {
			return null;
		}
		try {
			List<byte[]> data = conn.lrange(listKey, start, start + size - 1);
			returnConnection(conn);
			return data;
		} catch (Exception e) {
			returnBorkenConnection(conn);
			throw new RuntimeException(e);
		}
	}

	/**
	 * 获取Map结构所有数据
	 * 
	 * @param mapKey
	 * @return
	 */
	public Map<byte[], byte[]> getMapAll(byte[] mapKey) {
		Jedis conn = getConnection();
		if (conn == null) {
			return null;
		}
		try {
			Map<byte[], byte[]> data = conn.hgetAll(mapKey);
			returnConnection(conn);
			return data;
		} catch (Exception e) {
			returnBorkenConnection(conn);
			throw new RuntimeException(e);
		}
	}

	/**
	 * 获取Map结构所有数据(key为String)
	 * 
	 * @param mapKey
	 * @return
	 */
	public Map<String, String> getStringMapAll(String mapKey) {
		Jedis conn = getConnection();
		if (conn == null) {
			return null;
		}
		try {
			Map<String, String> result = conn.hgetAll(mapKey);
			returnConnection(conn);
			return result;
		} catch (Exception e) {
			returnBorkenConnection(conn);
			throw new RuntimeException(e);
		}
	}

	/**
	 * 添加到Map结构
	 * 
	 * @param mapKey
	 * @param field
	 * @param value
	 */
	public void putToMap(byte[] mapKey, byte[] field, byte[] value) {
		Jedis conn = getConnection();
		if (conn == null) {
			return;
		}
		try {
			conn.hset(mapKey, field, value);
			returnConnection(conn);
		} catch (Exception e) {
			returnBorkenConnection(conn);
			throw new RuntimeException(e);
		}
	}

	// /**
	// * 添加到Map结构
	// * @param key
	// * @param map
	// */
	// public void putToMap(String key, Map<String, String> map) {
	// Jedis conn = getConnection();
	// if (conn == null) {
	// return;
	// }
	// try {
	// conn.hmset(key, map);
	// returnConnection(conn);
	// } catch (Exception e) {
	// returnBorkenConnection(conn);
	// throw new RuntimeException(e);
	// }
	// }

	/**
	 * 添加到Map结构(key为String)
	 * 
	 * @param mapKey
	 * @param field
	 * @param value
	 */
	public void putStringToMap(String mapKey, String field, String value) {
		Jedis conn = getConnection();
		if (conn == null) {
			return;
		}
		try {
			conn.hset(mapKey, field, value);
			returnConnection(conn);
		} catch (Exception e) {
			returnBorkenConnection(conn);
			throw new RuntimeException(e);
		}
	}

	/**
	 * 批量设置到hash数据结果，采用byte类型存储
	 * 
	 * @param mapKey
	 * @param data
	 */
	public void hmset(byte[] mapKey, Map<byte[], byte[]> data) {
		Jedis conn = getConnection();
		if (conn == null) {
			return;
		}
		try {
			conn.hmset(mapKey, data);
			returnConnection(conn);
		} catch (Exception e) {
			returnBorkenConnection(conn);
			throw new RuntimeException(e);
		}
	}

	/**
	 * 添加到Map结构（key为String）
	 * 
	 * @param mapKey
	 * @param data
	 * @return
	 */
	public String hmset(String mapKey, Map<String, String> data) {
		Jedis conn = getConnection();
		if (conn == null) {
			return null;
		}
		try {
			String result = conn.hmset(mapKey, data);
			returnConnection(conn);
			return result;
		} catch (Exception e) {
			returnBorkenConnection(conn);
			throw new RuntimeException(e);
		}
	}

	/**
	 * 批量添加到sorted set队列，字符串类型
	 * 
	 * @param keyList
	 *            key集合
	 * @param valueMap
	 */
	public List<String> hmsetList(Set<String> keyList, Map<String, Map<String, String>> valueMap) {
		Jedis conn = getConnection();
		if (conn == null) {
			return null;
		}
		try {
			Pipeline p = conn.pipelined();
			List<Response<String>> reponseList = new ArrayList<Response<String>>();
			List<String> resultList = new ArrayList<String>();
			for (String key : keyList) {
				reponseList.add(p.hmset(key, valueMap.get(key)));
			}
			p.sync();
			for (Response<String> response : reponseList) {
				resultList.add(response.get());
			}
			returnConnection(conn);
			return resultList;
		} catch (Exception e) {
			returnBorkenConnection(conn);
			throw new RuntimeException(e);
		}
	}

	/**
	 * 从Map结构中获取数据
	 * 
	 * @param mapKey
	 * @param field
	 * @return
	 */
	public byte[] getFromMap(byte[] mapKey, byte[] field) {
		Jedis conn = getConnection();
		if (conn == null) {
			return null;
		}
		try {
			byte[] data = conn.hget(mapKey, field);
			returnConnection(conn);
			return data;
		} catch (Exception e) {
			returnBorkenConnection(conn);
			throw new RuntimeException(e);
		}
	}

	/**
	 * 从map中移除记录
	 * 
	 * @param mapKey
	 * @param field
	 */
	public void removeFromMap(byte[] mapKey, byte[] field) {
		Jedis conn = getConnection();
		if (conn == null) {
			return;
		}
		try {
			conn.hdel(mapKey, field);
			returnConnection(conn);
		} catch (Exception e) {
			returnBorkenConnection(conn);
			throw new RuntimeException(e);
		}
	}

	/**
	 * 添加到sorted set队列，字符串类型
	 * 
	 * @param setKey
	 * @param value
	 * @param score
	 */
	public void addToSortedSet(String setKey, String value, double score) {
		Jedis conn = getConnection();
		if (conn == null) {
			return;
		}
		try {
			conn.zadd(setKey, score, value);
			returnConnection(conn);
		} catch (Exception e) {
			returnBorkenConnection(conn);
			throw new RuntimeException(e);
		}
	}

	/**
	 * 添加到sorted set队列，字节类型
	 * 
	 * @param setKey
	 * @param value
	 * @param score
	 */
	public void addToSortedSet(byte[] setKey, byte[] value, double score) {
		Jedis conn = getConnection();
		if (conn == null) {
			return;
		}
		try {
			conn.zadd(setKey, score, value);
			returnConnection(conn);
		} catch (Exception e) {
			returnBorkenConnection(conn);
			throw new RuntimeException(e);
		}
	}

	/**
	 * 批量添加到sorted set队列，字符串类型
	 * 
	 * @param setKey
	 * @param valueMap
	 */
	public void addToSortedSet(String setKey, Map<String, Double> valueMap) {
		Jedis conn = getConnection();
		if (conn == null) {
			return;
		}
		try {
			conn.zadd(setKey, valueMap);
			returnConnection(conn);
		} catch (Exception e) {
			returnBorkenConnection(conn);
			throw new RuntimeException(e);
		}
	}

	/**
	 * 批量添加到sorted set队列，字符串类型
	 * 
	 * @param keyList
	 *            key集合
	 * @param valueMap
	 */
	public List<Long> addToSortedSet(Set<String> keyList, Map<String, Map<String, Double>> valueMap) {
		Jedis conn = getConnection();
		if (conn == null) {
			return null;
		}
		try {
			Pipeline p = conn.pipelined();
			List<Response<Long>> reponseList = new ArrayList<Response<Long>>();
			List<Long> resultList = new ArrayList<Long>();
			for (String key : keyList) {
				reponseList.add(p.zadd(key, valueMap.get(key)));
			}
			p.sync();
			for (Response<Long> response : reponseList) {
				resultList.add(response.get());
			}
			returnConnection(conn);
			return resultList;
		} catch (Exception e) {
			returnBorkenConnection(conn);
			throw new RuntimeException(e);
		}
	}

	public List<Long> addToSortedSet(String key, List<Map<String, Double>> valueMap) {
		Jedis conn = getConnection();
		if (conn == null) {
			return null;
		}
		try {
			Pipeline p = conn.pipelined();
			List<Response<Long>> reponseList = new ArrayList<Response<Long>>();
			List<Long> resultList = new ArrayList<Long>();
			for (Map<String, Double> map : valueMap) {
				reponseList.add(p.zadd(key, map));
			}
			p.sync();
			for (Response<Long> response : reponseList) {
				resultList.add(response.get());
			}
			returnConnection(conn);
			return resultList;
		} catch (Exception e) {
			returnBorkenConnection(conn);
			throw new RuntimeException(e);
		}
	}

	/**
	 * 批量添加到sorted set队列，字节类型
	 * 
	 * @param setKey
	 * @param valueMap
	 */
	public void addToSortedSet(byte[] setKey, Map<byte[], Double> valueMap) {
		Jedis conn = getConnection();
		if (conn == null) {
			return;
		}
		try {
			conn.zadd(setKey, valueMap);
			returnConnection(conn);
		} catch (Exception e) {
			returnBorkenConnection(conn);
			throw new RuntimeException(e);
		}
	}

	/**
	 * 从sorted set中获取一定范围的段，按score从低到高
	 * 
	 * @param sortKey
	 * @param start
	 * @param size
	 * @return
	 */
	public Set<String> getSortedSetRange(String sortKey, int start, int size) {
		Jedis conn = getConnection();
		if (conn == null) {
			return null;
		}
		try {
			Set<String> result = conn.zrange(sortKey, start, start + size - 1);
			returnConnection(conn);
			return result;
		} catch (Exception e) {
			returnBorkenConnection(conn);
			throw new RuntimeException(e);
		}
	}

	/**
	 * 从sorted set中获取一定范围的段，按score从高到低
	 * 
	 * @param sortKey
	 * @param start
	 * @param size
	 * @return
	 */
	public Set<String> getSortedSetRangeReverse(String sortKey, int start, int size) {
		Jedis conn = getConnection();
		if (conn == null) {
			return null;
		}
		try {
			Set<String> result = conn.zrevrange(sortKey, start, start + size - 1);
			returnConnection(conn);
			return result;
		} catch (Exception e) {
			returnBorkenConnection(conn);
			throw new RuntimeException(e);
		}
	}

	/**
	 * 根据排序分数从sorted set中获取一定范围的段
	 * 
	 * @param sortKey
	 * @param start
	 * @param size
	 * @return
	 */
	public Set<String> getZrangeByScore(String sortKey, String min, String max) {
		Jedis conn = getConnection();
		if (conn == null) {
			return null;
		}
		try {
			Set<String> result = conn.zrangeByScore(sortKey, min, max);
			returnConnection(conn);
			return result;
		} catch (Exception e) {
			returnBorkenConnection(conn);
			throw new RuntimeException(e);
		}
	}

	/**
	 * 
	 * @version: v1.0
	 * @author: dengkaichu
	 * @method: getZrangeByScore
	 * @description: 根据排序分数从sorted set中获取一定范围的段
	 * @dateTime: 2015-11-8 下午6:29:31
	 * @editor:
	 * @editDate:
	 * @remark:
	 * @param sortKey
	 *            key
	 * @param min
	 *            最小值
	 * @param max
	 *            最大值
	 * @param offset
	 *            开始位置
	 * @param count
	 *            数量
	 * @return
	 */
	public Set<String> getZrangeByScore(String sortKey, String min, String max, int offset, int count) {
		Jedis conn = getConnection();
		if (conn == null) {
			return null;
		}
		try {
			Set<String> result = conn.zrangeByScore(sortKey, min, max, offset, count);
			returnConnection(conn);
			return result;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			returnBorkenConnection(conn);
		}
	}

	public Long zcount(String sortKey, String min, String max) {
		Jedis conn = getConnection();
		if (conn == null) {
			return null;
		}
		try {
			Long result = conn.zcount(sortKey, min, max);
			returnConnection(conn);
			return result;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			returnBorkenConnection(conn);
		}
	}

	/**
	 * 从sorted set中获取一定范围的段，字节类型，按score从低到高
	 * 
	 * @param sortKey
	 * @param start
	 * @param size
	 * @return
	 */
	public Set<byte[]> getSortedSetRange(byte[] sortKey, int start, int size) {
		Jedis conn = getConnection();
		if (conn == null) {
			return null;
		}
		try {
			Set<byte[]> result = conn.zrange(sortKey, start, start + size - 1);
			returnConnection(conn);
			return result;
		} catch (Exception e) {
			returnBorkenConnection(conn);
			throw new RuntimeException(e);
		}
	}

	/**
	 * 从sorted set中获取一定范围的段，字节类型，按score从高到低
	 * 
	 * @param sortKey
	 * @param start
	 * @param size
	 * @return
	 */
	public Set<byte[]> getSortedSetRangeReverse(byte[] sortKey, int start, int size) {
		Jedis conn = getConnection();
		if (conn == null) {
			return null;
		}
		try {
			Set<byte[]> result = conn.zrevrange(sortKey, start, start + size - 1);
			returnConnection(conn);
			return result;
		} catch (Exception e) {
			returnBorkenConnection(conn);
			throw new RuntimeException(e);
		}
	}

	public Double zincrby(String key, double score, String member) {
		Jedis conn = getConnection();
		try {
			double result = conn.zincrby(key, score, member);
			returnConnection(conn);
			return result;
		} catch (Exception e) {
			returnBorkenConnection(conn);
			throw new RuntimeException(e);
		}
	}

	public Long zrem(String key, String... member) {
		Jedis conn = getConnection();
		try {
			Long result = conn.zrem(key, member);
			returnConnection(conn);
			return result;
		} catch (Exception e) {
			returnBorkenConnection(conn);
			throw new RuntimeException(e);
		}
	}

	/**
	 * 根据score从sorted set中移除记录
	 * 
	 * @param keySet
	 * @param score
	 */
	public long removeFromSortedSetByScore(String keySet, double score) {
		Jedis conn = getConnection();
		if (conn == null) {
			return 0;
		}
		try {
			long cnt = conn.zremrangeByScore(keySet, score, score);
			returnConnection(conn);
			return cnt;
		} catch (Exception e) {
			returnBorkenConnection(conn);
			throw new RuntimeException(e);
		}
	}

	/**
	 * 根据score从sorted set中移除记录
	 * 
	 * @param keySet
	 * @param score
	 * @return
	 */
	public long removeFromSortedSetByScore(byte[] keySet, double score) {
		Jedis conn = getConnection();
		if (conn == null) {
			return 0;
		}
		try {
			long cnt = conn.zremrangeByScore(keySet, score, score);
			returnConnection(conn);
			return cnt;
		} catch (Exception e) {
			returnBorkenConnection(conn);
			throw new RuntimeException(e);
		}
	}

	/**
	 * 从hash中取出一些指定的field的值
	 * 
	 * @author zhoulibo
	 * @date 2014-11-26
	 * @param key
	 * @param fields
	 * @return
	 */
	public Map<String, String> hmGet(String key, String[] fields) {
		Jedis conn = getConnection();
		if (conn == null) {
			return null;
		}
		try {
			List<String> values = conn.hmget(key, fields);
			Map<String, String> map = new HashMap<String, String>();
			int i = 0;
			for (String keyStr : fields) {
				map.put(keyStr, values.get(i));
				i++;
			}
			returnConnection(conn);
			return map;
		} catch (Exception e) {
			returnBorkenConnection(conn);
			throw new RuntimeException(e);
		}
	}

	/**
	 * 返回集合 key 中的所有成员
	 * 
	 * @author cyl
	 * @date 2015年3月23日 10:05:04
	 * @param key
	 * @param fields
	 * @return
	 */
	public Set<String> sMembers(String key) {
		Jedis conn = getConnection();
		if (conn == null) {
			return null;
		}
		try {
			Set<String> set = conn.smembers(key);
			returnConnection(conn);
			return set;
		} catch (Exception e) {
			returnBorkenConnection(conn);
			throw new RuntimeException(e);
		}
	}

	/**
	 * 查询多个key中相同的field
	 * 
	 * @author zhoulibo
	 * @date 2014-12-11
	 * @param keys
	 * @param fieldList
	 * @return
	 */
	public List<Map<String, String>> hmGetByList(List<String> keys, String[] fields) {
		Jedis conn = getConnection();
		if (conn == null) {
			return null;
		}
		try {
			Pipeline p = conn.pipelined();
			List<Response<List<String>>> rsponseList = new ArrayList<Response<List<String>>>();
			for (String key : keys) {
				rsponseList.add(p.hmget(key, fields));
			}
			p.sync();
			List<Map<String, String>> resultList = new ArrayList<Map<String, String>>();
			Map<String, String> map = null;
			for (Response<List<String>> response : rsponseList) {
				map = new HashMap<String, String>();
				int i = 0;
				for (String field : fields) {
					map.put(field, response.get().get(i));
					i++;
				}
				resultList.add(map);
			}
			returnConnection(conn);
			return resultList;
		} catch (Exception e) {
			returnBorkenConnection(conn);
			throw new RuntimeException(e);
		}
	}

	/**
	 * @author zhoulibo
	 * @Title: hmGetByList
	 * @Description: 从多个不同的hash中取不同的值
	 * @param keys
	 *            hash的key的集合
	 * @param fieldList
	 *            每个hash中要取出的值
	 * @return
	 */
	public List<Map<String, String>> hmsGetByList(List<String> keys, List<String[]> fieldList) {
		Jedis conn = getConnection();
		if (conn == null) {
			return null;
		}
		try {
			Pipeline p = conn.pipelined();
			List<Response<List<String>>> rsponseList = new ArrayList<Response<List<String>>>();
			int x = 0;
			for (String key : keys) {// 循环从每一个hash中取值
				rsponseList.add(p.hmget(key, fieldList.get(x)));
				x++;
			}
			p.sync();
			List<Map<String, String>> resultList = new ArrayList<Map<String, String>>();
			Map<String, String> map = null;
			x = 0;
			for (Response<List<String>> response : rsponseList) {// 组装数据
				String[] fields = fieldList.get(x);
				map = new HashMap<String, String>();
				int i = 0;
				for (String field : fields) {
					map.put(field, response.get().get(i));
					i++;
				}
				resultList.add(map);
				x++;
			}
			returnConnection(conn);
			return resultList;
		} catch (Exception e) {
			returnBorkenConnection(conn);
			throw new RuntimeException(e);
		}
	}

	/**
	 * 得到hash中指定的field的值
	 * 
	 * @author zhoulibo
	 * @date 2014-11-26
	 * @param key
	 * @param field
	 * @return
	 */
	public String hGet(String key, String field) {
		Jedis conn = getConnection();
		if (conn == null) {
			return null;
		}
		try {
			String value = conn.hget(key, field);
			returnConnection(conn);
			return value;
		} catch (Exception e) {
			returnBorkenConnection(conn);
			throw new RuntimeException(e);
		}
	}

	/**
	 * 从多个hash中取出同一个field的值
	 * 
	 * @author zhoulibo
	 * @date 2014-12-9
	 * @param keyList
	 * @param field
	 * @return
	 */
	public List<String> hGetByKeyList(List<String> keyList, String field) {
		Jedis conn = getConnection();
		if (conn == null) {
			return null;
		}
		try {
			Pipeline p = conn.pipelined();
			List<Response<String>> reponseList = new ArrayList<Response<String>>();
			List<String> resultList = new ArrayList<String>();
			for (String key : keyList) {
				reponseList.add(p.hget(key, field));
			}
			p.sync();
			for (Response<String> response : reponseList) {
				resultList.add(response.get());
			}
			returnConnection(conn);
			return resultList;
		} catch (Exception e) {
			returnBorkenConnection(conn);
			throw new RuntimeException(e);
		}
	}

	public Long hincrBy(String key, String field, long value) {
		Jedis conn = getConnection();
		if (conn == null) {
			return null;
		}
		try {
			Long result = conn.hincrBy(key, field, value);
			returnConnection(conn);
			return result;
		} catch (Exception e) {
			returnBorkenConnection(conn);
			throw new RuntimeException(e);
		}
	}

	/**
	 * 向一个has中插入一个field
	 * 
	 * @author zhoulibo
	 * @date 2014-11-26
	 * @param key
	 * @param field
	 * @param value
	 * @return
	 */
	public Long hSet(String key, String field, String value) {
		Jedis conn = getConnection();
		if (conn == null) {
			return null;
		}
		try {
			Long result = conn.hset(key, field, value);
			returnConnection(conn);
			return result;
		} catch (Exception e) {
			returnBorkenConnection(conn);
			throw new RuntimeException(e);
		}
	}

	public Long hdel(String key, String... fields) {
		Jedis conn = getConnection();
		if (conn == null) {
			return null;
		}
		try {
			Long result = conn.hdel(key, fields);
			returnConnection(conn);
			return result;
		} catch (Exception e) {
			returnBorkenConnection(conn);
			throw new RuntimeException(e);
		}
	}

	/**
	 * 去的一个hash的所有数据
	 * 
	 * @author zhoulibo
	 * @date 2014-11-26
	 * @param key
	 * @return
	 */
	public Map<String, String> hGetAll(String key) {
		Jedis conn = getConnection();
		if (conn == null) {
			return null;
		}
		try {
			Map<String, String> result = conn.hgetAll(key);
			returnConnection(conn);
			return result;
		} catch (Exception e) {
			returnBorkenConnection(conn);
			throw new RuntimeException(e);
		}
	}

	/**
	 * 一次性获取多个key的hash，使用pielined
	 * 
	 * @author zhoulibo
	 * @date 2014-11-26
	 * @param keys
	 * @return
	 */
	public List<Map<String, String>> hGetAllByKeyList(List<String> keys) {
		Jedis conn = getConnection();
		if (conn == null) {
			return null;
		}
		try {
			Pipeline pielined = conn.pipelined();
			List<Map<String, String>> resultList = new ArrayList<Map<String, String>>();
			List<Response<Map<String, String>>> responseList = new ArrayList<Response<Map<String, String>>>();
			for (String key : keys) {
				responseList.add(pielined.hgetAll(key));
			}
			pielined.sync();
			for (Response<Map<String, String>> response : responseList) {
				resultList.add(response.get());
			}
			returnConnection(conn);
			return resultList;
		} catch (Exception e) {
			returnBorkenConnection(conn);
			throw new RuntimeException(e);
		}
	}

	/**
	 * 通过Sort从一个list或者是set中取数据
	 * 
	 * @author zhoulibo
	 * @date 2014-11-27
	 * @param key
	 * @param by
	 * @param gets
	 * @return
	 */
	public List<String> getListBySort(String key, SortingParams sp) {
		Jedis conn = getConnection();
		if (conn == null) {
			return null;
		}
		try {
			List<String> result = conn.sort(key, sp);
			returnConnection(conn);
			return result;
		} catch (Exception e) {
			returnBorkenConnection(conn);
			throw new RuntimeException(e);
		}
	}

	public Set<Tuple> zrevrangeByScoreWithScores(String sortKey, int start, int size) {
		Jedis conn = getConnection();
		if (conn == null) {
			return null;
		}
		try {
			Set<Tuple> result = conn.zrevrangeByScoreWithScores(sortKey, start, start + size - 1);
			returnConnection(conn);
			return result;
		} catch (Exception e) {
			returnBorkenConnection(conn);
			throw new RuntimeException(e);
		}
	}

	public Set<Tuple> zrevrangeByScoreWithScores(String sortKey, String max, String min) {
		Jedis conn = getConnection();
		if (conn == null) {
			return null;
		}
		try {
			Set<Tuple> result = conn.zrevrangeByScoreWithScores(sortKey, max, min);
			returnConnection(conn);
			return result;
		} catch (Exception e) {
			returnBorkenConnection(conn);
			throw new RuntimeException(e);
		}
	}

	/**
	 * 在一个hash中的一个hashKey增加指定数字
	 * 
	 * @author zhoulibo
	 * @date 2014-11-29
	 * @param key
	 * @param hashKey
	 * @param num
	 * @return
	 */
	public Long hincrby(String key, String hashKey, Long num) {
		Jedis conn = getConnection();
		if (conn == null) {
			return null;
		}
		try {
			Long l = conn.hincrBy(key, hashKey, num);
			returnConnection(conn);
			return l;
		} catch (Exception e) {
			returnBorkenConnection(conn);
			throw new RuntimeException(e);
		}
	}

	/**
	 * 
	 * @author zhoulibo
	 * @date 2014-12-1
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public Set<String> zrevrange(String key, Long start, Long end) {
		Jedis conn = getConnection();
		if (conn == null) {
			return null;
		}
		try {
			Set<String> result = conn.zrevrange(key, start, end);
			returnConnection(conn);
			return result;
		} catch (Exception e) {
			returnBorkenConnection(conn);
			throw new RuntimeException(e);
		}
	}

	public List<Set<String>> zrevrangeByKeys(List<String> keys, long start, long end) {
		Jedis conn = getConnection();
		if (conn == null) {
			return null;
		}
		try {
			Pipeline p = conn.pipelined();
			List<Response<Set<String>>> list = new ArrayList<Response<Set<String>>>();
			for (String key : keys) {
				list.add(p.zrevrange(key, start, end));
			}
			p.sync();
			List<Set<String>> resultList = new ArrayList<Set<String>>();
			for (Response<Set<String>> response : list) {
				resultList.add(response.get());
			}
			returnConnection(conn);
			return resultList;
		} catch (Exception e) {
			returnBorkenConnection(conn);
			throw new RuntimeException(e);
		}
	}

	/**
	 * 获取sortset的长度
	 * 
	 * @author zhoulibo
	 * @date 2014-12-1
	 * @param key
	 * @return
	 */
	public Long zcard(String key) {
		Jedis conn = getConnection();
		if (conn == null) {
			return null;
		}
		try {
			Long result = conn.zcard(key);
			returnConnection(conn);
			return result;
		} catch (Exception e) {
			returnBorkenConnection(conn);
			throw new RuntimeException(e);
		}
	}

	/**
	 * 获取一个元素在sortset中的值
	 * 
	 * @author zhoulibo
	 * @date 2014-12-1
	 * @param key
	 * @param member
	 * @return
	 */
	public Double zscore(String key, String member) {
		Jedis conn = getConnection();
		if (conn == null) {
			return null;
		}
		try {
			Double result = conn.zscore(key, member);
			returnConnection(conn);
			return result;
		} catch (Exception e) {
			returnBorkenConnection(conn);
			throw new RuntimeException(e);
		}
	}

	/**
	 * 从sortset中取出指定部分的值
	 * 
	 * @author zhoulibo
	 * @date 2014-12-8
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public List<String> zrange(String key, Long start, Long end) {
		Jedis conn = getConnection();
		if (conn == null) {
			return null;
		}
		try {
			List<String> result = new ArrayList<String>(conn.zrange(key, start, end));
			returnConnection(conn);
			return result;
		} catch (Exception e) {
			returnBorkenConnection(conn);
			throw new RuntimeException(e);
		}
	}

	/**
	 * 从list中取出指定数量的值
	 * 
	 * @author zhoulibo
	 * @date 2014-12-8
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public List<String> lrange(String key, Long start, Long end) {
		Jedis conn = getConnection();
		if (conn == null) {
			return null;
		}
		try {
			List<String> result = conn.lrange(key, start, end);
			returnConnection(conn);
			return result;
		} catch (Exception e) {
			returnBorkenConnection(conn);
			throw new RuntimeException(e);
		}
	}

	/**
	 * @author zhoulibo
	 * @Title: lrangeByKeyList
	 * @Description: 从多个list中取同样长度的数据
	 * @param keys
	 * @param start
	 * @param end
	 * @return
	 */
	public List<List<String>> lrangeByKeyList(List<String> keys, Long start, Long end) {
		Jedis conn = getConnection();
		if (conn == null) {
			return null;
		}
		try {
			Pipeline p = conn.pipelined();
			List<Response<List<String>>> list = new ArrayList<Response<List<String>>>();
			for (String key : keys) {
				list.add(p.lrange(key, start, end));
			}
			p.sync();
			List<List<String>> resultList = new ArrayList<List<String>>();
			for (Response<List<String>> response : list) {
				resultList.add(response.get());
			}
			returnConnection(conn);
			return resultList;
		} catch (Exception e) {
			returnBorkenConnection(conn);
			throw new RuntimeException(e);
		}
	}

	public String lpop(String key) {
		Jedis conn = getConnection();
		if (conn == null) {
			return null;
		}
		try {
			String result = conn.lpop(key);
			returnConnection(conn);
			return result;
		} catch (Exception e) {
			returnBorkenConnection(conn);
			throw new RuntimeException(e);
		}
	}

	public Long lpush(String key, String... strings) {
		Jedis conn = getConnection();
		if (conn == null) {
			return null;
		}
		try {
			long result = conn.lpush(key, strings);
			returnConnection(conn);
			return result;
		} catch (Exception e) {
			returnBorkenConnection(conn);
			throw new RuntimeException(e);
		}
	}

	public String ltrim(String key, Long start, Long end) {
		Jedis conn = getConnection();
		if (conn == null) {
			return null;
		}
		try {
			String result = conn.ltrim(key, start, end);
			returnConnection(conn);
			return result;
		} catch (Exception e) {
			returnBorkenConnection(conn);
			throw new RuntimeException(e);
		}
	}

	/**
	 * @author zhoulibo
	 * @Title: lgrim
	 * @Description: 将一个list中不在指定范围内的元素全部移除
	 * @param key
	 *            list的key
	 * @param start
	 *            起点
	 * @param end
	 *            终点（注意，重点的元素是不会被移除）
	 * @return
	 */
	public String lgrim(String key, long start, long end) {
		Jedis conn = getConnection();
		if (conn == null) {
			return null;
		}
		try {
			String result = conn.ltrim(key, start, end);
			returnConnection(conn);
			return result;
		} catch (Exception e) {
			returnBorkenConnection(conn);
			throw new RuntimeException(e);
		}
	}

	public String lindex(String key, long index) {
		Jedis conn = getConnection();
		if (conn == null) {
			return null;
		}
		try {
			String result = conn.lindex(key, index);
			returnConnection(conn);
			return result;
		} catch (Exception e) {
			returnBorkenConnection(conn);
			throw new RuntimeException(e);
		}
	}

	public Long rpush(String key, String... strings) {
		Jedis conn = getConnection();
		if (conn == null) {
			return null;
		}
		try {
			long result = conn.rpush(key, strings);
			returnConnection(conn);
			return result;
		} catch (Exception e) {
			returnBorkenConnection(conn);
			throw new RuntimeException(e);
		}
	}

	public Long lrem(String key, long count, String value) {
		Jedis conn = getConnection();
		if (conn == null) {
			return null;
		}
		try {
			Long result = conn.lrem(key, count, value);
			returnConnection(conn);
			return result;
		} catch (Exception e) {
			returnBorkenConnection(conn);
			throw new RuntimeException(e);
		}
	}

	/**
	 * 将List中的一个元素移动到List的左侧开头
	 * 
	 * @author zhoulibo
	 * @date 2015年12月22日
	 * @param key
	 * @param count
	 * @param value
	 * @Exception
	 */
	public void lMoveToL(String key, long count, String value) {
		Jedis conn = getConnection();
		if (conn == null) {
			return;
		}
		try {
			conn.watch(key);
			Transaction t = conn.multi();
			t.lrem(key, count, value);
			t.lpush(key, value);
			t.exec();
			conn.unwatch();
			returnConnection(conn);
			return;
		} catch (Exception e) {
			returnBorkenConnection(conn);
			throw new RuntimeException(e);
		}
	}

	/**
	 * 查找一个sortset中多个member的sorce
	 * 
	 * @author zhoulibo
	 * @param <T>
	 * @date 2014-12-8
	 * @param key
	 * @param members
	 * @return
	 */
	public <T> List<Double> zscoreByList(String key, List<T> members) {
		Jedis conn = getConnection();
		if (conn == null) {
			return null;
		}
		try {
			Pipeline p = conn.pipelined();
			List<Response<Double>> list = new ArrayList<Response<Double>>();
			for (Object member : members) {
				list.add(p.zscore(key, member.toString()));
			}
			p.sync();
			List<Double> resultList = new ArrayList<Double>();
			for (Response<Double> response : list) {
				resultList.add(response.get());
			}
			returnConnection(conn);
			return resultList;
		} catch (Exception e) {
			returnBorkenConnection(conn);
			throw new RuntimeException(e);
		}
	}

	/**
	 * 批量更新sortset中的顺序
	 * 
	 * @author zhoulibo
	 * @date 2014-12-8
	 * @param key
	 * @param scoreAndMember
	 * @return
	 */
	public <T> List<Long> zaddByList(String key, Map<T, Double> scoreAndMember) {
		Jedis conn = getConnection();
		if (conn == null) {
			return null;
		}
		try {
			Pipeline p = conn.pipelined();
			List<Response<Long>> list = new ArrayList<Response<Long>>();
			for (T member : scoreAndMember.keySet()) {
				list.add(p.zadd(key, scoreAndMember.get(member), member.toString()));
			}
			p.sync();
			List<Long> resultList = new ArrayList<Long>();
			for (Response<Long> response : list) {
				resultList.add(response.get());
			}
			returnConnection(conn);
			return resultList;
		} catch (Exception e) {
			returnBorkenConnection(conn);
			throw new RuntimeException(e);
		}
	}

	/**
	 * 查询一个member在sortset中的排序（从大到小）
	 * 
	 * @author zhoulibo
	 * @date 2015-1-9
	 * @param key
	 * @param member
	 * @return
	 */
	public Long zrank(String key, String member) {
		Jedis conn = getConnection();
		if (conn == null) {
			return null;
		}
		try {
			Long rank = conn.zrank(key, member);
			returnConnection(conn);
			return rank;
		} catch (Exception e) {
			returnBorkenConnection(conn);
			throw new RuntimeException(e);
		}
	}

	public Long zrevrank(String key, String member) {
		Jedis conn = getConnection();
		if (conn == null) {
			return null;
		}
		try {
			Long rank = conn.zrevrank(key, member);
			returnConnection(conn);
			return rank;
		} catch (Exception e) {
			returnBorkenConnection(conn);
			throw new RuntimeException(e);
		}
	}

	public Long zadd(String key, Double score, String member) {
		Jedis conn = getConnection();
		if (conn == null) {
			return null;
		}
		try {
			Long rank = conn.zadd(key, score, member);
			returnConnection(conn);
			return rank;
		} catch (Exception e) {
			returnBorkenConnection(conn);
			throw new RuntimeException(e);
		}
	}

	public boolean sismember(String key, String member) {
		Jedis conn = getConnection();
		if (conn == null) {
			return false;
		}
		try {
			boolean bool = conn.sismember(key, member);
			returnConnection(conn);
			return bool;
		} catch (Exception e) {
			returnBorkenConnection(conn);
			throw new RuntimeException(e);
		}
	}

	/**
	 * 根据List查询在sortset中的排名
	 * 
	 * @author zhoulibo
	 * @date 2015-1-17
	 * @param key
	 * @param members
	 * @return
	 */
	public List<Long> zrankByList(String key, List<String> members) {
		Jedis conn = getConnection();
		if (conn == null) {
			return null;
		}
		try {
			Pipeline p = conn.pipelined();
			List<Response<Long>> rList = new ArrayList<Response<Long>>();
			for (String member : members) {
				rList.add(p.zrank(key, member));
			}
			p.sync();
			List<Long> rankList = new ArrayList<Long>();
			for (Response<Long> r : rList) {
				rankList.add(r.get());
			}
			returnConnection(conn);
			return rankList;
		} catch (Exception e) {
			returnBorkenConnection(conn);
			throw new RuntimeException(e);
		}
	}

	/**
	 * 根据List查询在sortset中的排名
	 * 
	 * @author zhoulibo
	 * @date 2015-1-17
	 * @param key
	 * @param members
	 * @return
	 */
	public List<Long> zrevrankByList(String key, List<String> members) {
		Jedis conn = getConnection();
		if (conn == null) {
			return null;
		}
		try {
			Pipeline p = conn.pipelined();
			List<Response<Long>> rList = new ArrayList<Response<Long>>();
			for (String member : members) {
				rList.add(p.zrevrank(key, member));
			}
			p.sync();
			List<Long> rankList = new ArrayList<Long>();
			for (Response<Long> r : rList) {
				rankList.add(r.get());
			}
			returnConnection(conn);
			return rankList;
		} catch (Exception e) {
			returnBorkenConnection(conn);
			throw new RuntimeException(e);
		}
	}

	/**
	 * 查询一个list的长度
	 * 
	 * @author zhoulibo
	 * @date 2015-2-4
	 * @param key
	 * @return
	 */
	public Long llen(String key) {
		Jedis conn = getConnection();
		if (conn == null) {
			return null;
		}
		try {
			Long length = conn.llen(key);
			returnConnection(conn);
			return length;
		} catch (Exception e) {
			returnBorkenConnection(conn);
			throw new RuntimeException(e);
		}
	}

	public Long sadd(String key, String... values) {
		Jedis conn = getConnection();
		if (conn == null) {
			return null;
		}
		try {
			Long length = conn.sadd(key, values);
			returnConnection(conn);
			return length;
		} catch (Exception e) {
			returnBorkenConnection(conn);
			throw new RuntimeException(e);
		}
	}

	public List<Long> sadd(List<String> keys, String... values) {
		Jedis conn = getConnection();
		if (conn == null) {
			return null;
		}
		try {
			Pipeline p = conn.pipelined();
			List<Response<Long>> rList = new ArrayList<Response<Long>>();
			for (String key : keys) {
				rList.add(p.sadd(key, values));
			}
			p.sync();
			List<Long> rankList = new ArrayList<Long>();
			for (Response<Long> r : rList) {
				rankList.add(r.get());
			}
			returnConnection(conn);
			return rankList;
		} catch (Exception e) {
			returnBorkenConnection(conn);
			throw new RuntimeException(e);
		}
	}

	public Long scard(String key) {
		Jedis conn = getConnection();
		if (conn == null) {
			return null;
		}
		try {
			Long length = conn.scard(key);
			return length;
		} catch (Exception e) {
			returnBorkenConnection(conn);
			throw new RuntimeException(e);
		}
	}

	/**
	 * @author zhoulibo
	 * @Title: srem
	 * @Description: 移除一个redis中的set集合中的一个或者多个元素
	 * @param key
	 * @param values
	 * @return
	 */
	public Long srem(String key, String... values) {
		Jedis conn = getConnection();
		if (conn == null) {
			return null;
		}
		try {
			Long length = conn.srem(key, values);
			returnConnection(conn);
			return length;
		} catch (Exception e) {
			returnBorkenConnection(conn);
			throw new RuntimeException(e);
		}
	}

	/**
	 * 根据分数段查询总记录数
	 * 
	 * @author sunzhenguo
	 * @param keys
	 * @param scoreStart
	 * @param scoreEnd
	 *            使用管道
	 * @return
	 */
	public List<Long> zCountByKeysAndScore(List<String> keys, Double scoreStart, Double scoreEnd) {
		Jedis conn = getConnection();
		if (conn == null) {
			return null;
		}
		try {
			Pipeline p = conn.pipelined();
			List<Response<Long>> list = new ArrayList<Response<Long>>();
			for (String key : keys) {
				list.add(p.zcount(key, scoreStart, scoreEnd));
			}
			p.sync();
			List<Long> resultList = new ArrayList<Long>();
			for (Response<Long> response : list) {
				resultList.add(response.get());
			}
			returnConnection(conn);
			return resultList;
		} catch (Exception e) {
			returnBorkenConnection(conn);
			throw new RuntimeException(e);
		}
	}

	/**
	 * @author sunzhenguo 得到merber集合
	 * @param sortKey
	 * @param max
	 * @param min
	 * @return
	 */
	public Set<String> zByScoreWithScores(String sortKey, Double max, Double min) {
		Jedis conn = getConnection();
		if (conn == null) {
			return null;
		}
		try {
			Set<String> result = conn.zrangeByScore(sortKey, max + "", min + "");
			returnConnection(conn);
			return result;
		} catch (Exception e) {
			returnBorkenConnection(conn);
			throw new RuntimeException(e);
		}
	}

	public static interface Redis<T> {
		T run(Jedis jedis);
	}

	public static interface RedisPipeline<T> {
		void run(Pipeline pipeline, List<Response<T>> responses);

	}

	public static <T> T execute(Redis<T> redis) {
		Jedis jedis = redisDao.getConnection();
		try {
			T result = redis.run(jedis);
			redisDao.returnConnection(jedis);
			return result;
		} catch (Exception e) {
			// logger.error(e.getMessage(),e);
			redisDao.returnBorkenConnection(jedis);
			throw new RuntimeException(e);
		}
	}

	/**
	 * 发布
	 * 
	 * @param channel
	 * @param message
	 * @return
	 */
	public Long publish(String channel, String message) {
		Jedis conn = getConnection();
		if (conn == null) {
			return null;
		}
		try {
			Long result = conn.publish(channel, message);
			returnConnection(conn);
			return result;
		} catch (Exception e) {
			returnBorkenConnection(conn);
			throw new RuntimeException(e);
		}
	}

	public void subscribe(JedisPubSub jedisPubSub, String... channels) {
		Jedis conn = getConnection();
		if (conn == null) {
			return;
		}
		try {
			conn.subscribe(jedisPubSub, channels);
			returnConnection(conn);
		} catch (Exception e) {
			returnBorkenConnection(conn);
			throw new RuntimeException(e);
		}
	}
}
