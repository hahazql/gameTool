package com.hahazql.util.helper;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hahazql.util.constants.CommonErrorLogInfo;


/**
 * 提供对{@link ExecutorService}的工具类
 * 
 * 
 */
public class ExecutorUtil {
	/** 默认的{@link ExecutorService}关闭前等待其中的任务结束的时间:5分钟 */
	private static final int DEFAULT_AWAIT_TERMINATE_MINUTES = 5;

	/**
	 * 关闭{@link ExecutorService},在关闭之前最多等待
	 * {@link #DEFAULT_AWAIT_TERMINATE_MINUTES}分钟来满足其中已经开始任务结束
	 * 
	 * @param executorService
	 * @return 返回停止后仍然为被开始执行的任务
	 * @see {@link #shutdownAndAwaitTermination(ExecutorService, long, TimeUnit)}
	 */
	public static List<Runnable> shutdownAndAwaitTermination(final ExecutorService executorService) {
		return shutdownAndAwaitTermination(executorService, DEFAULT_AWAIT_TERMINATE_MINUTES, TimeUnit.MINUTES);
	}

	/**
	 * 关闭{@link ExecutorService},在指定的时间内<code>awaitTermateTimeout</code>等待
	 * <code>executorService</code>中已经开始的任务尽量结束. 此实现参照{@link ExecutorService}
	 * 注释中的 <code>shutdownAndAwaitTermination</code>策略
	 * 
	 * @param executorService
	 *            将要被停止的{@link ExecutorService}
	 * @param awaitTerminateTimeout
	 *            等待时间
	 * @param timeUnit
	 *            awaitTerminate的单位
	 * @return 返回停止后任然未被开始执行的任务
	 */
	public static List<Runnable> shutdownAndAwaitTermination(final ExecutorService executorService,
			final long awaitTerminateTimeout, final TimeUnit timeUnit) {
		List<Runnable> _left = null;
		executorService.shutdown();
		try {
			boolean _terminateResult = executorService.awaitTermination(awaitTerminateTimeout, timeUnit);
			if (!_terminateResult) {
				//再次停止
				_left = executorService.shutdownNow();
				if (_left != null) {
					for (Runnable _o : _left) {
					}
				}
				_terminateResult = executorService.awaitTermination(awaitTerminateTimeout, timeUnit);
			}
		} catch (InterruptedException e) {
			_left = executorService.shutdownNow();
			Thread.currentThread().interrupt();
		}
		return _left;
	}
}
