package org.feedchannel.repository;

import java.net.URL;
import java.util.Date;

public interface FeedItem
{

	void setTitle(String title);

	String getTitle();

	void setDate(Date date);

	Date getDate();
	
	void setSource(String source);
	
	String getSource();
	
	void setRelatedMediaUrl(URL url);
	
	URL getRelatedMediaUrl();
	
	void setSummary(String summary);
	
	String getSummary();

	void setDescription(String value);
	
	String getDescription();

	String getKey();

	void setKey(String id);
	
	void setLink(URL link);
	
	URL getLink();
	
	String toJSON();

}
