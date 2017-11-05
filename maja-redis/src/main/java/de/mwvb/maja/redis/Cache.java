package de.mwvb.maja.redis;

public class Cache extends CacheTK<String> {
	
	public Cache(String host, int port) {
		super(host, port, key -> key);
	}
}
