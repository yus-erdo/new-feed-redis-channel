package org.feedchannel.crawler;


import java.util.HashMap;
import java.util.Map;

import org.feedchannel.RedisKeys;
import org.feedchannel.crawler.impl.GenericFeedCrawler;
import org.feedchannel.repository.FeedRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Service
public class FeedCrawlerFactory
{
	private static final Logger log = LoggerFactory
			.getLogger(FeedCrawlerFactory.class);

	@Autowired
	private JedisPool jedisPool;
	
	@Autowired
	private FeedRepository feedRepository;

	private Map<String, String> feedUriToClassMap = null;

	public FeedCrawler createFeedCrawler(String feedUri)
	{
		if (feedUri == null)
		{
			throw new IllegalArgumentException("feedUri can not be null.");
		}

		if (feedUriToClassMap == null)
		{
			populateMapping();
		}

		log.info("Looking for FeedCrawler for {}", feedUri);

		FeedCrawler feedCrawler = null;
		String className = feedUriToClassMap.get(feedUri);

		if (className != null)
		{
			try
			{
				Class<?> feedCrawlerClass = Class.forName(className);
				feedCrawler = (FeedCrawler) feedCrawlerClass.newInstance();
			}
			catch (Exception e)
			{
				log.warn("Unable to get FeedCrawler for " + feedUri, e);
			}
		}
		else
		{
			feedCrawler = new GenericFeedCrawler();
		}

		feedCrawler.setFeedURI(feedUri);
		feedCrawler.setFeedRepository(feedRepository);

		log.info("FeedCrawler created: {}", feedCrawler.toString());
		return feedCrawler;
	}

	public String getFeedCrawlerClass(String feedUri)
	{
		if (feedUri == null)
		{
			throw new IllegalArgumentException("feedUri can not be null.");
		}

		if (feedUriToClassMap == null)
		{
			populateMapping();
		}

		log.info("Looking for FeedCrawler for {}", feedUri);

		String className = feedUriToClassMap.get(feedUri);

		if (className == null)
		{
			className = GenericFeedCrawler.class.getCanonicalName();
		}

		log.info("FeedCrawler class name: {}", className);
		return className;
	}

	private void populateMapping()
	{
		log.info("Populating FeedCrawlerFactory feedUri => Class map...");

		feedUriToClassMap = new HashMap<String, String>();

		Jedis jedis = jedisPool.getResource();

		try
		{
			feedUriToClassMap = jedis
					.hgetAll(RedisKeys.FEED_URI_TO_CLASS_MAP_KEY);
		}
		finally
		{
			jedisPool.returnResource(jedis);
		}

	}

}
