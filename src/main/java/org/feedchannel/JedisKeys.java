package org.feedchannel;

public interface JedisKeys
{
	String FEED_CRAWLER = "feed_crawler";

	String FEED_URI_SET_KEY = FEED_CRAWLER + ":feed_uri_set";

	String FEED_URI_TO_CLASS_MAP_KEY = FEED_CRAWLER + ":feed_uri_to_class_map";

	String FEED_HASH = FEED_CRAWLER + ":feed_hash";

	String FEED_KEY = FEED_HASH + ":key:";

	String NEW_FEED_CHANNEL = FEED_CRAWLER + ":new_feed_channel";
}
