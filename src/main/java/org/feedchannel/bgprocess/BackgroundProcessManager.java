package org.feedchannel.bgprocess;

public interface BackgroundProcessManager
{
	void enqueue(String feedUri, Class<? extends Runnable> clazz);
	
	void setInterval(int interval);
	
	int getInterval();
}
