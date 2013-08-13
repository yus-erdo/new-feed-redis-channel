package org.feedchannel.bgprocess.impl;

import static net.greghaines.jesque.utils.JesqueUtils.entry;
import static net.greghaines.jesque.utils.JesqueUtils.map;

import java.util.Arrays;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import net.greghaines.jesque.Config;
import net.greghaines.jesque.Job;
import net.greghaines.jesque.client.Client;
import net.greghaines.jesque.worker.LoggingWorkerListener;
import net.greghaines.jesque.worker.Worker;
import net.greghaines.jesque.worker.WorkerEvent;
import net.greghaines.jesque.worker.WorkerImpl;
import net.greghaines.jesque.worker.WorkerListener;

import org.feedchannel.bgprocess.BackgroundProcessManager;
import org.feedchannel.crawler.FeedCrawlerTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JesqueBgProcessManager implements BackgroundProcessManager
{
	private static final Logger log = LoggerFactory
			.getLogger(JesqueBgProcessManager.class);

	private static final String FEED_CRAWLER_JOB_QID = "feed_crawler_job_queue";

	// one minute by default
	private int interval = 60 * 1000;

	private Worker worker = null;

	private Client jesqueClient;

	private Queue<FeedUriClassTuple> feedUriCircularQueue = new LinkedBlockingQueue<FeedUriClassTuple>(
			10);

	private WorkerListener listener = new WorkerListener()
	{
		public void onEvent(WorkerEvent event, Worker worker, String queue,
				Job job, Object runner, Object result, Exception ex)
		{
			log.info("onEvent: {} {} {} {} {} {} {} {}", event, worker, queue,
					job, runner, runner, result, ex);

			try
			{
				Thread.sleep(getInterval());
			}
			catch (InterruptedException e)
			{
				log.error("Thread can not sleep.", e);
				return;
			}

			FeedUriClassTuple feedUriClassTuple = feedUriCircularQueue.poll();

			if (feedUriClassTuple != null)
			{
				enqueue(feedUriClassTuple.feedUri, feedUriClassTuple.clazz);
			}
		}
	};

	@Autowired
	@SuppressWarnings("unchecked")
	public JesqueBgProcessManager(final Config jesqueConfig,
			final Client jesqueClient)
	{
		this.jesqueClient = jesqueClient;

		// Start a worker to run jobs from the queue
		worker = new WorkerImpl(jesqueConfig,
				Arrays.asList(FEED_CRAWLER_JOB_QID), map(entry(
						FeedCrawlerTask.class.getCanonicalName(),
						FeedCrawlerTask.class)));

		worker.addListener(LoggingWorkerListener.INSTANCE);

		worker.addListener(listener, WorkerEvent.JOB_SUCCESS,
				WorkerEvent.JOB_FAILURE);

		Thread workerThread = new Thread(worker);

		workerThread.start();

		log.info("Worker thread started for: {}", FEED_CRAWLER_JOB_QID);
	}

	public void enqueue(String feedUri, Class<? extends Runnable> clazz)
	{
		feedUriCircularQueue.add(new FeedUriClassTuple(feedUri, clazz));

		// Add a job to the queue
		Job job = new Job(clazz.getCanonicalName(), feedUri);

		jesqueClient.enqueue(FEED_CRAWLER_JOB_QID, job);

		log.info("Enqued: {} {}", feedUri, this);
	}

	/**
	 * @return the interval
	 */
	public int getInterval()
	{
		return interval;
	}

	/**
	 * @param interval
	 *            the interval to set
	 */
	public void setInterval(int interval)
	{
		this.interval = interval;
	}

	private class FeedUriClassTuple
	{
		public String feedUri;
		public Class<? extends Runnable> clazz;

		public FeedUriClassTuple(String feedUri, Class<? extends Runnable> clazz)
		{
			this.feedUri = feedUri;
			this.clazz = clazz;
		}
	}
}
