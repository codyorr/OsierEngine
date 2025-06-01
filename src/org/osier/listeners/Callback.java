package org.osier.listeners;

public interface Callback<T> {
	public void run(T obj);
}
