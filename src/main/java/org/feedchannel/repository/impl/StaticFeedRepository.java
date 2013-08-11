package org.feedchannel.repository.impl;


import java.util.ArrayList;
import java.util.List;

import org.feedchannel.repository.FeedItem;
import org.feedchannel.repository.FeedRepository;
import org.feedchannel.repository.NewFeedItemEventListener;
import org.springframework.stereotype.Repository;

@Repository
public class StaticFeedRepository implements FeedRepository
{
	public List<String> getAllFeedUris()
	{
		ArrayList<String> set = new ArrayList<String>()
		{
			private static final long serialVersionUID = 2063672093417021277L;

			{
				add("http://www.ntvmsnbc.com/id/24927412/device/rss/rss.xml");
				add("http://www.mackolik.com/Rss");
			}
		};

		return set;
	}

	public void save()
	{
		// TODO Auto-generated method stub
		
	}

	public void save(FeedItem feedItem)
	{
		// TODO Auto-generated method stub
		
	}

	public void setNewFeedItemEventListener(NewFeedItemEventListener newFeedItemEventListener)
	{
		// TODO Auto-generated method stub
		
	}

	public void reset()
	{
		// TODO Auto-generated method stub
		
	}

}
