package org.feedchannel;


import org.feedchannel.bgprocess.BackgroundProcessManager;
import org.feedchannel.crawler.FeedCrawlerTask;
import org.feedchannel.repository.FeedItem;
import org.feedchannel.repository.FeedRepository;
import org.feedchannel.repository.NewFeedItemEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

/**
 * {@link FeedCrawlerTask} with hard-coded input data.
 */
@Service
public class FeedCrawlerService
{
	private static final Logger log = LoggerFactory
			.getLogger(FeedCrawlerService.class);

	@Autowired
	private FeedRepository feedRepository;

	@Autowired
	private BackgroundProcessManager bgProcessManager;

	public void enqueueFeedUri(String feedUri) {
		bgProcessManager.enqueue(feedUri, FeedCrawlerTask.class);
	}
	
	public void setNewFeedItemEventListener(NewFeedItemEventListener newFeedItemEventListener) {
		feedRepository.setNewFeedItemEventListener(newFeedItemEventListener);
	}
	
	public void reset() {
		feedRepository.reset();
	}

	public static void main(String[] args)
	{
		ApplicationContext ac = new ClassPathXmlApplicationContext(
				"/META-INF/spring/app-context.xml");

		FeedCrawlerService fcs = (FeedCrawlerService) ac
				.getBean("feedCrawlerService");
		
		fcs.setNewFeedItemEventListener(new NewFeedItemEventListener()
		{
			public void onNewFeedItem(FeedItem feedItem)
			{
				log.info(feedItem.toString());
			}
		});
		
		fcs.reset();
		
		fcs.enqueueFeedUri("http://www.ntvmsnbc.com/id/24927412/device/rss/rss.xml");
		
		fcs.enqueueFeedUri("http://www.mackolik.com/Rss");
		
	}

}
