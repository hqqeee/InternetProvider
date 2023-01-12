package com.epam.controller.listener;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.controller.command.admin.DailyWithdrawCommand;

@WebListener
public class SchedulerInitListner implements ServletContextListener{
	private ScheduledThreadPoolExecutor scheduler;
	private final Logger logger = LogManager.getLogger(SchedulerInitListner.class);
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		logger.debug("Scheduler initialization begin.");
		try {
			scheduler = new ScheduledThreadPoolExecutor(1);
			scheduler.scheduleAtFixedRate(new DailyWithdrawCommand(),
				2, 120, TimeUnit.SECONDS);
		} catch (Exception e) {
			logger.fatal("Cannot initialize scheduler.", e);
			System.exit(1);
		}
		logger.debug("Scheduler initialization finished.");
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		scheduler.shutdownNow();
		logger.debug("Scheduler shutted down.");
	}
}
