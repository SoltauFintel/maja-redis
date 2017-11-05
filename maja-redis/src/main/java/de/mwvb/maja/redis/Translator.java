package de.mwvb.maja.redis;

public interface Translator<T> {

	/**
	 * @param key
	 * @return String representation for key
	 */
	String translate(T key);
}
