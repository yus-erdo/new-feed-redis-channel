package org.feedchannel.crawler.impl;

import java.util.Date;

import org.feedchannel.exception.CrawlerException;
import org.feedchannel.repository.FeedItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.syndication.feed.synd.SyndEntry;

public class GenericFeedCrawler extends AbstractFeedCrawler
{
	private static final Logger log = LoggerFactory
			.getLogger(GenericFeedCrawler.class);

	protected void processSyndEntry(FeedItem feedItem, SyndEntry syndEntry)
			throws CrawlerException
	{
		feedItem.setTitle(syndEntry.getTitle());
		
		feedItem.setPublishDate(syndEntry.getPublishedDate());
		feedItem.setUpdateDate(syndEntry.getUpdatedDate());
		feedItem.setReceiveDate(new Date());

		// item.setRelatedMediaUrl(url);
		// item.setSource(source);
		// item.setSummary(summary);
		feedItem.setDescription(syndEntry.getDescription().getValue());

		feedRepository.save(feedItem);
	}
}
