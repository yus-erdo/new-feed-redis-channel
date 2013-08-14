package org.feedchannel;

import junit.framework.TestCase;

import org.springframework.beans.factory.annotation.Autowired;

public class ExampleServiceTests extends TestCase {

	@Autowired
	private FeedCrawlerService feedCrawlerService;
	
	public void testReadOnce() throws Exception {
		assertNotNull(feedCrawlerService);
	}

}
