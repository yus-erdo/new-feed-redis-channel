package org.feedchannel.exception;

import java.net.MalformedURLException;

public class CrawlerException extends Exception
{

	public CrawlerException(MalformedURLException e)
	{
		super(e);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 7101279655639222629L;

}
