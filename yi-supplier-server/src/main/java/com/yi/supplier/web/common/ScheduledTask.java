package com.yi.supplier.web.common;
//package com.yi.admin.web.common;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
///**
// * 定时任务
// * 
// * @author xuyh
// *
// */
////@Component
//public class ScheduledTask {
//
//	private static final Logger LOGGER = LoggerFactory.getLogger(ScheduledTask.class);
//
//	private int fixedDelayCount = 1;
//	private int fixedRateCount = 1;
//	private int initialDelayCount = 1;
//	private int cronCount = 1;
//
//	@Scheduled(fixedDelay = 5000) // fixedDelay = 5000表示当前方法执行完毕5000ms后，Spring scheduling会再次调用该方法
//	public void testFixDelay() {
//		LOGGER.info("===fixedDelay: 第{}次执行方法", fixedDelayCount++);
//	}
//
//	@Scheduled(fixedRate = 5000) // fixedRate = 5000表示当前方法开始执行5000ms后，Spring scheduling会再次调用该方法
//	public void testFixedRate() {
//		LOGGER.info("===fixedRate: 第{}次执行方法", fixedRateCount++);
//	}
//
//	@Scheduled(initialDelay = 1000, fixedRate = 5000) // initialDelay = 1000表示延迟1000ms执行第一次任务
//	public void testInitialDelay() {
//		LOGGER.info("===initialDelay: 第{}次执行方法", initialDelayCount++);
//	}
//
//	@Scheduled(cron = "0 0/1 * * * ?") // http://cron.qqe2.com/ cron接受cron表达式，根据cron表达式确定定时规则
//	public void testCron() {
//		LOGGER.info("===initialDelay: 第{}次执行方法", cronCount++);
//	}
//}
