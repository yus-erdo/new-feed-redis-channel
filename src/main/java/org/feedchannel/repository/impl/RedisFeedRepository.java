package org.feedchannel.repository.impl;

import java.util.List;
import java.util.Map;

import org.feedchannel.RedisKeys;
import org.feedchannel.repository.FeedItem;
import org.feedchannel.repository.FeedRepository;
import org.feedchannel.repository.NewFeedItemEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Repository("feedRepository")
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
			return jedis.lrange(RedisKeys.FEED_URI_LIST, 0, -1);
		}
		finally
		{
			jedisPool.returnResource(jedis);
		}
	}

	public void pushFeedUri(String... feedUri)
	{
		Jedis jedis = jedisPool.getResource();
		try
		{
			jedis.rpush(RedisKeys.FEED_URI_LIST, feedUri);
		}
		finally
		{
			jedisPool.returnResource(jedis);
		}
	}
	
	public String popFeedUri()
	{
		Jedis jedis = jedisPool.getResource();
		try
		{
			return jedis.lpop(RedisKeys.FEED_URI_LIST);
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
			return jedis.hget(RedisKeys.FEED_HASH, feedKey) != null;
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
			String feedJSON = feedItem.toJSON();

			jedis.hset(RedisKeys.FEED_HASH, feedKey, feedJSON);
			log.info("FeedItem saved. Feed JSON = {}", feedJSON);

			jedis.publish(RedisKeys.NEW_FEED_CHANNEL, feedJSON);

			if (newFeedItemEventListener != null)
			{
				newFeedItemEventListener.onNewFeedItem(feedItem);
			}
		}
		finally
		{
			jedisPool.returnResource(jedis);
		}
	}

	private String getRedisFeedKey(FeedItem feedItem)
	{
		return RedisKeys.FEED_KEY + feedItem.getKey();
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
					RedisKeys.FEED_HASH).entrySet())
			{
				log.info("HDEL {}", entry.getKey());
				jedis.hdel(RedisKeys.FEED_HASH, entry.getKey());
			}
		}
		finally
		{
			jedisPool.returnResource(jedis);
		}
	}
}
