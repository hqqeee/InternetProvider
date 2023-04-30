package com.epam.controller.listener;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.controller.command.admin.DailyWithdrawCommand;

/**
 * The `SchedulerInitListner` class is a Servlet context listener that
 * initializes a scheduler and schedules a `DailyWithdrawCommand` to run at a
 * fixed rate. The scheduler is shut down when the servlet context is destroyed.
 *
 * @author Hrebenozhko Ruslan
 * @version 1.0
 */
@WebListener
public class SchedulerInitListner implements ServletContextListener {
	/**
	 * A scheduled thread pool executor that manages the scheduling of the
	 * `DailyWithdrawCommand`.
	 */
	private ScheduledThreadPoolExecutor scheduler;
	/**
	 * LOG is the logger for this class.
	 */
	private static final Logger LOG = LogManager.getLogger(SchedulerInitListner.class);

	/**
	 * Initializes the scheduler when the servlet context is initialized.
	 *
	 * @param sce the servlet context event
	 */
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		LOG.debug("Scheduler initialization begin.");
		try {
			scheduler = new ScheduledThreadPoolExecutor(1);
			scheduler.scheduleAtFixedRate(new DailyWithdrawCommand(), 0, 1, TimeUnit.MINUTES);
		} catch (Exception e) {
			LOG.fatal("Cannot initialize scheduler.", e);
			System.exit(1);
		}
		LOG.debug("Scheduler initialization finished.");
	}

	/**
	 * Shuts down the scheduler when the servlet context is destroyed.
	 *
	 * @param sce the servlet context event
	 */
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		scheduler.shutdownNow();
		LOG.debug("Scheduler shutted down.");
	}
}
