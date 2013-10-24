package org.feedchannel.crawler;


import java.util.Queue;

import org.feedchannel.repository.FeedItem;
import org.feedchannel.repository.FeedSourceRepository;

public interface FeedCrawler extends Runnable {
	
	String getFeedURI();
	
	void setFeedURI(String feedUri);
	
	Queue<FeedItem> getFeedItemQueue();

	void setFeedRepository(FeedSourceRepository feedRepository);

}
