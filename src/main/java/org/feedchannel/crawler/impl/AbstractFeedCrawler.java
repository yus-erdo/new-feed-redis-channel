/**
 * 
 */
package org.feedchannel.crawler.impl;

import java.net.URL;
import java.util.Queue;

import org.feedchannel.JedisKeys;
import org.feedchannel.crawler.FeedCrawler;
import org.feedchannel.repository.FeedItem;
import org.feedchannel.repository.FeedRepository;
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

	protected FeedRepository feedRepository;

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

					String feedKey = getFeedKey(syndEntry);

					if (!feedRepository.exists(feedKey))
					{
						processSyndEntry(syndEntry);
					}
					else
					{
						log.info("Feed item already exists: {}", feedKey);
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
		return JedisKeys.FEED_KEY + syndEntry.getLink()
				+ ":" + syndEntry.getTitle();
	}

	protected abstract void processSyndEntry(SyndEntry syndEntry);

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
	public void setFeedRepository(FeedRepository feedRepository)
	{
		this.feedRepository = feedRepository;
	}

}
