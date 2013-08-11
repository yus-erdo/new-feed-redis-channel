package org.feedchannel.repository.initializer;


import java.util.Map;

import org.feedchannel.JedisKeys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Component
public class FeedUriToClassMapInitializer
{
	private static final Logger log = LoggerFactory
			.getLogger(FeedUriToClassMapInitializer.class);

	@Autowired
	private JedisPool jedisPool;

	public void setFeedUriToClassMap(Map<String, String> feedUriToClassMap)
	{
		Jedis jedis = jedisPool.getResource();
		try
		{
			for (Map.Entry<String, String> entry : feedUriToClassMap.entrySet())
			{
				jedis.hset(JedisKeys.FEED_URI_TO_CLASS_MAP_KEY, entry.getKey(),
						entry.getValue());
				log.info("FeedUriToClassMap entry added: {} : {} =>  {}",
						JedisKeys.FEED_URI_TO_CLASS_MAP_KEY, entry.getKey(),
						entry.getValue());
			}
		}
		finally
		{
			jedisPool.returnResource(jedis);
		}
	}
}
