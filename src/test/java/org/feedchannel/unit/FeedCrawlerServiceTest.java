package org.feedchannel.unit;

import org.feedchannel.FeedCrawlerService;
import org.junit.Test;


public class FeedCrawlerServiceTest
{
	@Test
	public void testStart() {
		FeedCrawlerService fcs = new FeedCrawlerService();
		
		fcs.start();
		
	}
}
