	package org.feedchannel.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.feedchannel.FeedCrawlerService;
import org.feedchannel.repository.FeedItem;
import org.feedchannel.repository.FeedSourceRepository;
import org.feedchannel.repository.impl.FeedItemImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class ExampleConfigurationTests
{

	@Autowired
	private FeedCrawlerService feedCrawlerService;

	@Autowired
	private FeedSourceRepository redisFeedRepository;

	@Test
	public void testWiring() throws Exception 
	{
		assertNotNull(feedCrawlerService);

		assertNotNull(redisFeedRepository);
	}

	@Test
	public void testSimpleProperties() throws Exception
	{
		FeedItem feedItem = new FeedItemImpl();
		
		feedItem.setKey("feed_key");
		
		assertEquals("feed_key", feedItem.getKey());
		
		redisFeedRepository.reset();
		
		assertFalse(redisFeedRepository.exists(feedItem));
		
		redisFeedRepository.save(feedItem);
		
		assertTrue(redisFeedRepository.exists(feedItem));

	}

}
