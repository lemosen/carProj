package com.yi.core.task;

import java.util.concurrent.Executors;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

/**
 * 定时任务线程池
 * 
 * @author xuyh
 *
 */
@Configuration
public class SchedulerConfig implements SchedulingConfigurer {

	/**
	 * 
	 */
	@Override
	public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
		// 参数传入一个size为20的线程池
		taskRegistrar.setScheduler(Executors.newScheduledThreadPool(50));
	}

}
