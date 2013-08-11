package org.feedchannel.crawler;

import org.feedchannel.SpringUtil;
import org.feedchannel.repository.FeedRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class FeedCrawlerTask implements Runnable
{
	private static final Logger log = LoggerFactory
			.getLogger(FeedCrawlerTask.class);
	
	private FeedCrawlerFactory feedCrawlerFactory;
	
	private FeedRepository feedRepository;

	private String feedUri; 

	public FeedCrawlerTask(String feedUri)
	{
		this.feedUri = feedUri;
		feedCrawlerFactory = SpringUtil.getBean(SpringUtil.FEED_CRAWLER_FACTORY, FeedCrawlerFactory.class);
		feedRepository = SpringUtil.getBean(SpringUtil.FEED_REPOSITORY, FeedRepository.class);
	}

	public void run()
	{
		FeedCrawler fc = feedCrawlerFactory.createFeedCrawler(feedUri);
		fc.setFeedRepository(feedRepository);
		
		log.info("About to run for: " + fc.getFeedURI());
		
		fc.run();
		
		log.info("Task finished for {}", fc.getFeedURI());	
	}

}
