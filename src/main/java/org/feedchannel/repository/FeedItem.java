package org.feedchannel.repository;

import java.net.URL;
import java.util.Date;

public interface FeedItem
{

	Date getReceiveDate();
	
	Date getPublishDate();
	
	Date getUpdateDate();

	String getDescription();

	String getKey();

	URL getLink();
	
	URL getRelatedMediaUrl();
	
	String getSource();
	
	String getSummary();
	
	String getTitle();
	
	void setReceiveDate(Date date);
	
	void setPublishDate(Date date);
	
	void setUpdateDate(Date date);
	
	void setDescription(String value);

	void setKey(String id);
	
	void setLink(URL link);

	void setRelatedMediaUrl(URL url);

	void setSource(String source);
	
	void setSummary(String summary);
	
	void setTitle(String title);
	
	String toJSON();

}
