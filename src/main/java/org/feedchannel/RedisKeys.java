package org.feedchannel;

public interface RedisKeys
{
	String NAMESPACE = "feed_crawler";

	String FEED_URI_TO_CLASS_MAP_KEY = NAMESPACE + ":feed_uri_to_class_map";

	String FEED_HASH = NAMESPACE + ":feed_hash";

	String FEED_KEY = FEED_HASH + ":key:";

	String NEW_FEED_CHANNEL = NAMESPACE + ":feed_channel";

	String FEED_URI_LIST = NAMESPACE + ":feed_uri_list";
}
