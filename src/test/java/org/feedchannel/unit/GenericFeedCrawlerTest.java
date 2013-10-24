package org.feedchannel.unit;

import org.feedchannel.crawler.impl.GenericFeedCrawler;
import org.feedchannel.repository.FeedRepository;
import org.junit.Before;

import static org.mockito.Mockito.mock;

/**
 * Created with IntelliJ IDEA.
 * User: yusufe
 * Date: 07/10/2013
 * Time: 22:21
 * To change this template use File | Settings | File Templates.
 */
public class GenericFeedCrawlerTest {

    GenericFeedCrawler genericFeedCrawler;

    @Before
    public void setUp() throws Exception {
        genericFeedCrawler = new GenericFeedCrawler();
    }

    @Before
    public void testProcessSyndEntry() throws Exception {

        FeedRepository feedRepository = mock(FeedRepository.class);
        genericFeedCrawler.setFeedRepository(feedRepository);
    }
}
