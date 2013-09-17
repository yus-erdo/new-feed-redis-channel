new-feed-redis-channel
======================

A simple yet powerful feed crawler service that fetches feeds and uses Redis to store and publish feeds. So it is as easy to give a feed URI and subscribe to Redis channel to create a -push- feed notification system.

easy to use
======================

getting feeds via callback object:

```java

FeedCrawlerService feedCrawlerService = new FeedCrawlerService();

// set crawler service to use this RSS URI. This RSS URI will be checked every 1 (configurable) minute. 
fcs.pushFeedUri("http://rss.cnn.com/rss/edition.rss");

// set a listener for new feed
feedCrawlerService.setNewFeedItemEventListener(new NewFeedItemEventListener()
{
    /** 
    * This method will be called every time a new feed is fetched.
    * (Same feed can be received by redis channel at the same time)
    */
    public void onNewFeedItem(FeedItem feedItem)
    {
    	log.info(feedItem.toString());
    }
});

// start crawling service
feedCrawlerService.start();

```

getting feeds via redis-cli by subscribing to channel:

(Using Jedis (java Redis client) new feeds can be received by any java application)

```shell
$ redis-cli
redis 127.0.0.1:6379>
redis 127.0.0.1:6379> SUBSCRIBE feed_crawler:feed_channel

# new feeds will appear here when received as simple text

```
