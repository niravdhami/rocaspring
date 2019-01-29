package com.eny.roca.rocablservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisShardInfo;

@Component
public class RedisDBConfig {

	@Value("${roca.redis.host}")
	private String host;

	@Value("${roca.redis.key}")
	private String key;

	@Value("${roca.redis.database}")
	private String database;

	@Value("${roca.redis.port}")
	private String port;

	@Value("${roca.redis.isUseSSL}")
	private String isUseSSL;

	public Jedis getInstance() {
		JedisShardInfo shardInfo = new JedisShardInfo(host, Integer.parseInt(port), Boolean.valueOf(isUseSSL));
		shardInfo.setPassword(key);
		Jedis jedis = new Jedis(shardInfo);
		return jedis;
	}

	public void add(String key, String value) {
		try (Jedis jedis = getInstance()) {
			jedis.set(key, value);
		}
	}

	public void add(String key, String value, int expiryTime) {
		try (Jedis jedis = getInstance()) {
			jedis.set(key, value);
			jedis.expire(key, expiryTime);
		}
	}

	public String get(String key) {
		String value = null;
		try (Jedis jedis = getInstance()) {
			value = jedis.get(key);
		}
		return value;
	}
}
