package org.feedchannel.integration;

import junit.framework.TestCase;

import org.feedchannel.FeedCrawlerService;
import org.springframework.beans.factory.annotation.Autowired;

public class ExampleServiceTests extends TestCase {

	@Autowired
	private FeedCrawlerService feedCrawlerService;
	
	public void testReadOnce() throws Exception {
		assertNotNull(feedCrawlerService);
	}

}
