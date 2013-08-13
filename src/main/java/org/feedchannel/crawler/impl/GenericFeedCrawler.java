package org.feedchannel.crawler.impl;

import java.net.MalformedURLException;
import java.net.URL;


import org.feedchannel.repository.FeedItem;
import org.feedchannel.repository.impl.FeedItemImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.syndication.feed.synd.SyndEntry;

public class GenericFeedCrawler extends AbstractFeedCrawler
{
	private static final Logger log = LoggerFactory
			.getLogger(GenericFeedCrawler.class);

	protected void processSyndEntry(SyndEntry syndEntry)
	{
		String feedKey = syndEntry.getLink() + ":" + syndEntry.getTitle();

		if (feedRepository.exists(feedKey))
		{
			log.info("Feed item already exists: {}", feedKey);
			return;
		}
		
		FeedItem item = new FeedItemImpl();
		
		item.setKey(feedKey);

		item.setTitle(syndEntry.getTitle());
		item.setDate(syndEntry.getPublishedDate());
		
		try
		{
			item.setLink(new URL(syndEntry.getLink()));
		}
		catch (MalformedURLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// item.setRelatedMediaUrl(url);
		// item.setSource(source);
		// item.setSummary(summary);
		item.setDescription(syndEntry.getDescription().getValue());

		feedRepository.save(item);
	}
}
