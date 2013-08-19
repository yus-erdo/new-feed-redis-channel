package org.feedchannel.scheduling.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledFuture;

import org.feedchannel.crawler.FeedCrawler;
import org.feedchannel.crawler.FeedCrawlerFactory;
import org.feedchannel.repository.FeedRepository;
import org.feedchannel.scheduling.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;

@SuppressWarnings("rawtypes")
@Component("scheduler")
public class SpringScheduling implements Scheduler
{

	@Autowired
	private TaskScheduler springTaskScheduler;

	@Autowired
	private FeedRepository feedRepository;

	@Autowired
	private FeedCrawlerFactory feedCrawlerFactory;

	private List<ScheduledFuture> taskFutureList = new ArrayList<ScheduledFuture>();

	private int interval;

	public void setInterval(int interval)
	{
		this.interval = interval;
	}

	public int getInterval()
	{
		return interval;
	}

	public void start()
	{
		ScheduledFuture sFuture = springTaskScheduler.scheduleWithFixedDelay(
				new FeedUriListPollTask(), 1000);
		taskFutureList.add(sFuture);
	}

	private class FeedUriListPollTask implements Runnable
	{
		public void run()
		{
			String feedUri = null;
			while ((feedUri = feedRepository.popFeedUri()) != null)
			{
				FeedCrawler feedCrawler = feedCrawlerFactory
						.createFeedCrawler(feedUri);
				ScheduledFuture sFuture = springTaskScheduler
						.scheduleWithFixedDelay(feedCrawler, 60 * 1000);
				taskFutureList.add(sFuture);
			}
		}

	}

}
