package com.epam.controller.listener;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.epam.controller.command.admin.DailyWithdrawCommand;

@WebListener
public class SchedulerInitListner implements ServletContextListener{
	private ScheduledThreadPoolExecutor scheduler;
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		System.out.println("Scheduler initialization begin.");
		scheduler = new ScheduledThreadPoolExecutor(1);
		scheduler.scheduleAtFixedRate(new DailyWithdrawCommand(),
				2, 50, TimeUnit.SECONDS);
		
		System.out.println("Scheduler seccessfully initilized");
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		scheduler.shutdownNow();
	}
}
