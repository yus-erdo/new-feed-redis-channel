package org.feedchannel;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringUtil implements ApplicationContextAware
{
	private static ApplicationContext context;
	
	public static final String FEED_CRAWLER_FACTORY = "feedCrawlerFactory";
	
	public static final String FEED_REPOSITORY = "feedRepository";
	
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException
	{
		context = applicationContext;
	}
	
	public static <T> T getBean(String beanName, Class<T> type)
	{
		return context.getBean(beanName, type);
	}

}
