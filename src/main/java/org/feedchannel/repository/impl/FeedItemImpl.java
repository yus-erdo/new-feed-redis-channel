/**
 * 
 */
package org.feedchannel.repository.impl;


import java.io.IOException;
import java.net.URL;
import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.feedchannel.repository.FeedItem;

/**
 * @author yusufe
 * 
 */
public class FeedItemImpl implements FeedItem
{
	private Date date;

	private String description;

	private String id;

	private URL relatedMediaUrl;

	private String source;

	private String summary;

	private String title;

	private URL link;

	/*
	 * (non-Javadoc)
	 * 
	 * @see io.nabz.feedcrawler.repository.FeedItem#getDate()
	 */
	public Date getDate()
	{
		return date;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see io.nabz.feedcrawler.repository.FeedItem#getDescription()
	 */
	public String getDescription()
	{
		return description;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see io.nabz.feedcrawler.repository.FeedItem#getId()
	 */
	public String getKey()
	{
		return id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see io.nabz.feedcrawler.repository.FeedItem#getRelatedMediaUrl()
	 */
	public URL getRelatedMediaUrl()
	{
		return relatedMediaUrl;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see io.nabz.feedcrawler.repository.FeedItem#getSource()
	 */
	public String getSource()
	{
		return source;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see io.nabz.feedcrawler.repository.FeedItem#getSummary()
	 */
	public String getSummary()
	{
		return summary;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see io.nabz.feedcrawler.repository.FeedItem#getTitle()
	 */
	public String getTitle()
	{
		return title;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see io.nabz.feedcrawler.repository.FeedItem#setDate(java.util.Date)
	 */
	public void setDate(Date date)
	{
		this.date = date;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * io.nabz.feedcrawler.repository.FeedItem#setDescription(java.lang.String)
	 */
	public void setDescription(String value)
	{
		this.description = value;
	}

	/*
	 * (non-Javadoc)
	 * @see io.nabz.feedcrawler.repository.FeedItem#setKey(java.lang.String)
	 */
	public void setKey(String id)
	{
		this.id = id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * io.nabz.feedcrawler.repository.FeedItem#setRelatedMediaUrl(java.net.URL)
	 */
	public void setRelatedMediaUrl(URL relatedMediaUrl)
	{
		this.relatedMediaUrl = relatedMediaUrl;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see io.nabz.feedcrawler.repository.FeedItem#setSource(java.lang.String)
	 */
	public void setSource(String source)
	{
		this.source = source;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see io.nabz.feedcrawler.repository.FeedItem#setSummary(java.lang.String)
	 */
	public void setSummary(String summary)
	{
		this.summary = summary;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see io.nabz.feedcrawler.repository.FeedItem#setTitle(java.lang.String)
	 */
	public void setTitle(String title)
	{
		this.title = title;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see io.nabz.feedcrawler.repository.FeedItem#setLink(java.net.URL)
	 */
	public void setLink(URL link)
	{
		this.link = link;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see io.nabz.feedcrawler.repository.FeedItem#getLink()
	 */
	public URL getLink()
	{
		return link;
	}

	public String toJSON()
	{
		ObjectMapper om = new ObjectMapper();

		try
		{
			return om.writeValueAsString(this);
		}
		catch (JsonGenerationException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (JsonMappingException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public String toString()
	{
		return new ToStringBuilder(this).append(title).append(link)
				.append(date).build();
	}
}
