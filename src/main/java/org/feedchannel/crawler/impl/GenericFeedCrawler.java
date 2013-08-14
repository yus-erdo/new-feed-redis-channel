package org.feedchannel.crawler.impl;

import org.feedchannel.exception.CrawlerException;
import org.feedchannel.repository.FeedItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.syndication.feed.synd.SyndEntry;

public class GenericFeedCrawler extends AbstractFeedCrawler
{
	private static final Logger log = LoggerFactory
			.getLogger(GenericFeedCrawler.class);

	protected void processSyndEntry(SyndEntry syndEntry)
			throws CrawlerException
	{
		FeedItem feedItem = newFeedItemWithKey(syndEntry);

		feedItem.setTitle(syndEntry.getTitle());
		feedItem.setDate(syndEntry.getPublishedDate());

		// item.setRelatedMediaUrl(url);
		// item.setSource(source);
		// item.setSummary(summary);
		feedItem.setDescription(syndEntry.getDescription().getValue());

		feedRepository.save(feedItem);
	}
}
