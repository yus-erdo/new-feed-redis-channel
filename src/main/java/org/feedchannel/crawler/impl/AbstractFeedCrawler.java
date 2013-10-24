/**
 * 
 */
package org.feedchannel.crawler.impl;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Queue;

import org.feedchannel.RedisKeys;
import org.feedchannel.crawler.FeedCrawler;
import org.feedchannel.exception.CrawlerException;
import org.feedchannel.repository.FeedItem;
import org.feedchannel.repository.FeedSourceRepository;
import org.feedchannel.repository.impl.FeedItemImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

/**
 * @author yusufe
 * 
 */
public abstract class AbstractFeedCrawler implements FeedCrawler
{
	private static final Logger log = LoggerFactory
			.getLogger(AbstractFeedCrawler.class);

	private String feedUri;

	protected FeedSourceRepository feedRepository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	public void run()
	{
		try
		{
			XmlReader reader = null;

			try
			{
				reader = new XmlReader(new URL(feedUri));
				SyndFeed feed = new SyndFeedInput().build(reader);

				for (Object syndEntryObj : feed.getEntries())
				{
					SyndEntry syndEntry = (SyndEntry) syndEntryObj;

					FeedItem feedItem = newFeedItemWithKey(syndEntry);

					if (!feedRepository.exists(feedItem))
					{
						processSyndEntry(feedItem, syndEntry);
					}
					else
					{
						log.info("Feed item already exists: {}", feedItem.getKey());
					}
				}

			}
			finally
			{
				if (reader != null)
				{
					reader.close();
				}
			}
		}
		catch (Exception e)
		{
			log.warn("Something wrong with crawler for: " + feedUri, e);
		}
	}

	protected String getFeedKey(SyndEntry syndEntry)
	{
		return RedisKeys.FEED_KEY + syndEntry.getLink();
	}

	protected abstract void processSyndEntry(FeedItem feedItem, SyndEntry syndEntry) throws CrawlerException;
	
	public FeedItem newFeedItemWithKey(SyndEntry syndEntry)
			throws CrawlerException
	{
		
		log.info("newFeedItemWithKey(): create new FeedItem: {}", syndEntry.getLink());
		
		URL url = null;

		try
		{
			url = new URL(syndEntry.getLink());
		}
		catch (MalformedURLException e)
		{
			log.info("Feed doesn't have a valid link: {}", syndEntry.getLink());
			throw new CrawlerException(e);
		}

		FeedItem feedItem = new FeedItemImpl();

		feedItem.setLink(url);

		feedItem.setKey(syndEntry.getLink());
		return feedItem;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see io.nabz.feedcrawler.crawler.FeedCrawler#getFeedURI()
	 */
	public String getFeedURI()
	{
		return feedUri;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see io.nabz.feedcrawler.crawler.FeedCrawler#setFeedURI(java.lang.String)
	 */
	public void setFeedURI(String feedUri)
	{
		this.feedUri = feedUri;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see io.nabz.feedcrawler.crawler.FeedCrawler#getFeedItemQueue()
	 */
	public Queue<FeedItem> getFeedItemQueue()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * io.nabz.feedcrawler.crawler.FeedCrawler#setFeedRepository(io.nabz.feedcrawler
	 * .repository.FeedRepository)
	 */
	public void setFeedRepository(FeedSourceRepository feedRepository)
	{
		this.feedRepository = feedRepository;
	}

}
