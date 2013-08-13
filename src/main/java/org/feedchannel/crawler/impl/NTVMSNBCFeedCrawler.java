package org.feedchannel.crawler.impl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.feedchannel.repository.FeedItem;
import org.feedchannel.repository.impl.FeedItemImpl;
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
	protected void processSyndEntry(SyndEntry syndEntry)
	{
		String feedKey = syndEntry.getLink() + ":" + syndEntry.getTitle();

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
			log.error("Link is not valid.", e);
			return;
		}

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
				item.setRelatedMediaUrl(new URL(photo.attr("src")));
			}
			catch (MalformedURLException e)
			{
				log.error("Link is not valid.", e);
				return;
			}
		}

		// item.setSource(source);
		// item.setSummary(summary);
		item.setDescription(syndEntry.getDescription().getValue());

		feedRepository.save(item);
	}
}
