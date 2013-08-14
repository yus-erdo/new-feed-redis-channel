package org.feedchannel.repository.impl;

import java.util.List;
import java.util.Map;

import org.feedchannel.JedisKeys;
import org.feedchannel.repository.FeedItem;
import org.feedchannel.repository.FeedRepository;
import org.feedchannel.repository.NewFeedItemEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Repository
public class RedisFeedRepository implements FeedRepository
{
	private static final Logger log = LoggerFactory
			.getLogger(RedisFeedRepository.class);

	@Autowired
	private JedisPool jedisPool;

	private NewFeedItemEventListener newFeedItemEventListener;

	public List<String> getAllFeedUris()
	{
		Jedis jedis = jedisPool.getResource();
		try
		{
			Long len = jedis.llen(JedisKeys.FEED_URI_SET_KEY);
			return jedis.lrange(JedisKeys.FEED_URI_SET_KEY, 0, len);
		}
		finally
		{
			jedisPool.returnResource(jedis);
		}
	}

	public boolean exists(FeedItem feedItem)
	{
		String feedKey = getRedisFeedKey(feedItem);

		Jedis jedis = jedisPool.getResource();
		try
		{
			return jedis.hget(JedisKeys.FEED_HASH, feedKey) != null;
		}
		finally
		{
			jedisPool.returnResource(jedis);
		}
	}

	public void save(FeedItem feedItem)
	{
		Jedis jedis = jedisPool.getResource();
		try
		{
			String feedKey = getRedisFeedKey(feedItem);

			jedis.hset(JedisKeys.FEED_HASH, feedKey, feedItem.toJSON());
			log.info("FeedItem saved. Key = {}", feedKey);

			if (newFeedItemEventListener != null)
			{
				newFeedItemEventListener.onNewFeedItem(feedItem);
				jedis.publish(JedisKeys.NEW_FEED_CHANNEL, feedItem.toJSON());
			}
		}
		finally
		{
			jedisPool.returnResource(jedis);
		}
	}

	private String getRedisFeedKey(FeedItem feedItem)
	{
		return JedisKeys.FEED_KEY + feedItem.getKey();
	}

	public void setNewFeedItemEventListener(
			NewFeedItemEventListener newFeedItemEventListener)
	{
		this.newFeedItemEventListener = newFeedItemEventListener;
	}

	/**
	 * Deletes all feed keys in Redis
	 * 
	 * NOTE: Does not support transactions.
	 * 
	 */
	public void reset()
	{
		final Jedis jedis = jedisPool.getResource();
		try
		{
			for (Map.Entry<String, String> entry : jedis.hgetAll(
					JedisKeys.FEED_HASH).entrySet())
			{
				log.info("HDEL {}", entry.getKey());
				jedis.hdel(JedisKeys.FEED_HASH, entry.getKey());
			}
		}
		finally
		{
			jedisPool.returnResource(jedis);
		}
	}
}
