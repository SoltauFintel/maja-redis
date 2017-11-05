package de.mwvb.maja.redis;

import org.pmw.tinylog.Logger;

import redis.clients.jedis.Jedis;

public class CacheTK<KEY_TYPE> {
	private final Jedis redis;
	private final Translator<KEY_TYPE> translator;
	
	public CacheTK(String host, int port, Translator<KEY_TYPE> translator) {
		redis = new Jedis(host, port);
		this.translator = translator;
	}

	public void put(KEY_TYPE key, String value) {
		try {
			String tkey = translate(key);
			redis.set(tkey, value);
			redis.expire(tkey, getExpirationTimeInSeconds());
		} catch (Exception e) {
			Logger.error(e);
		}
	}
	
	protected int getExpirationTimeInSeconds() {
		return daysToSeconds(10);
	}
	
	protected final int daysToSeconds(int days) {
		return 60 * 60 * 24 * days;
	}
	
	public String get(KEY_TYPE key) {
		try {
			return redis.get(translate(key));
		} catch (Exception e) {
			Logger.error(e);
			return null;
		}
	}
	
	public void remove(KEY_TYPE key) {
		try {
			redis.del(translate(key));
		} catch (Exception e) {
			Logger.error(e);
		}
	}
	
	public Long getDatabaseSize() {
		return redis.dbSize();
	}

	protected String translate(KEY_TYPE key) {
		return translator.translate(key);
	}
}
