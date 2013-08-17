package org.feedchannel.crawler.impl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

import org.feedchannel.exception.CrawlerException;
import org.feedchannel.repository.FeedItem;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.syndication.feed.synd.SyndEntry;

public class NTVMSNBCFeedCrawler extends AbstractFeedCrawler
{
	private static final Logger log = LoggerFactory
			.getLogger(NTVMSNBCFeedCrawler.class);

	@Override
	protected void processSyndEntry(FeedItem feedItem, SyndEntry syndEntry)
			throws CrawlerException
	{
		try
		{
			feedItem.setTitle(syndEntry.getTitle());

			feedItem.setPublishDate(syndEntry.getPublishedDate());
			feedItem.setUpdateDate(syndEntry.getUpdatedDate());
			feedItem.setReceiveDate(new Date());
			
			// item.setSource(source);
			// item.setSummary(summary);
			feedItem.setDescription(syndEntry.getDescription().getValue());

			Document doc = null;
			try
			{
				doc = Jsoup.connect(syndEntry.getLink()).get();
			}
			catch (IOException e)
			{
				log.error("Connection problem.", e);
				return;
			}

			Elements photo = doc.select("#linkImgRelatedPhotos img");

			if (photo != null)
			{
				log.info("Photo element: {}", photo.toString());

				try
				{
					feedItem.setRelatedMediaUrl(new URL(photo.attr("src")));
				}
				catch (MalformedURLException e)
				{
					log.error("Link is not valid.", e);
					return;
				}
			}
		}
		finally
		{
			feedRepository.save(feedItem);
		}
	}
}
