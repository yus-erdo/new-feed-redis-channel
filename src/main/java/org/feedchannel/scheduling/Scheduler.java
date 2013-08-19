package org.feedchannel.scheduling;

public interface Scheduler
{
	void setInterval(int interval);
	
	int getInterval();
	
	void start();
}
