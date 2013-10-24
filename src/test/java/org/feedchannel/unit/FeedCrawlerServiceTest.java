package org.feedchannel.unit;

import org.feedchannel.FeedCrawlerService;
import org.junit.Before;
import org.junit.Test;


public class FeedCrawlerServiceTest
{
    FeedCrawlerService feedCrawlerService;

    @Before
    public void setup(){
        feedCrawlerService = new FeedCrawlerService();
    }

	@Test
	public void testStart() {

        feedCrawlerService.start();
		
	}
}
