/*
 * Copyright (c) 2019/4/3 3:48:16
 * Created by zhuxj
 */

package org.zipper.helper.quartz;

import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.quartz.impl.matchers.KeyMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zipper.helper.exception.HelperException;

import java.util.List;
import java.util.Map;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * quartz 工具类
 *
 * @author zhuxj
 */
public class QuartzUtils {

    private static final Logger logger = LoggerFactory.getLogger(QuartzUtils.class);

    /**
     * 默认任务组名称
     */
    private static final String JOB_GROUP_NAME = "DEFAULT_JOB_GROUP_NAME";
    /**
     * 默认触发器组名称
     */
    private static final String TRIGGER_GROUP_NAME = "DEFAULT_TRIGGER_GROUP_NAME";

    /**
     * 调度实例
     */
    private static Scheduler scheduler;

    /**
     * 初始化Scheduler 实例
     *
     * @param scheduler {@link Scheduler}
     */
    public static void init(Scheduler scheduler) {

        if (QuartzUtils.scheduler != null) {
            throw HelperException.newException(QuartzError.INIT_ERROR, "实例已初始化，请不要重复初始化");
        }

        try {
            QuartzUtils.scheduler = scheduler;
            QuartzUtils.scheduler.start();
        } catch (SchedulerException e) {
            logger.error("启动调度器失败", e);
            throw HelperException.newException(QuartzError.START_ERROR, e);
        }
    }

    /**
     * 注册全局监听器
     *
     * @param jobListener job监听器
     */
    public static void addJobListener(JobListener jobListener) {

        if (scheduler == null) {
            throw HelperException.newException(QuartzError.INIT_ERROR, "实例未初始化");
        }

        try {
            scheduler.getListenerManager().addJobListener(jobListener);
        } catch (SchedulerException e) {
            logger.error("注册全局监听器失败", e);
            throw HelperException.newException(QuartzError.ADD_LISTENER_ERROR, e);
        }
    }

    /**
     * 为某组任务进行注册监听器
     *
     * @param jobListener job监听器
     * @param jobGroup    job组
     */
    public static void addJobListener(JobListener jobListener, String jobGroup) {

        if (scheduler == null) {
            throw HelperException.newException(QuartzError.INIT_ERROR, "实例未初始化");
        }
        if (jobGroup == null || "".equals(jobGroup)) {
            throw HelperException.newException(QuartzError.PARAMETER_ERROR, "未指定Job Group");
        }

        try {
            GroupMatcher<JobKey> matcher = GroupMatcher.jobGroupEquals(jobGroup);
            scheduler.getListenerManager().addJobListener(jobListener, matcher);
        } catch (SchedulerException e) {
            logger.error(String.format("为组:[%s]添加监听器时失败", jobGroup), e);
            throw HelperException.newException(QuartzError.ADD_LISTENER_ERROR, e);
        }
    }

    /**
     * 为某组任务进行注册监听器
     *
     * @param jobListener job监听器
     * @param jobName     job名称
     * @param jobGroup    job组
     */
    public static void addJobListener(JobListener jobListener, String jobName, String jobGroup) {

        if (scheduler == null) {
            throw HelperException.newException(QuartzError.INIT_ERROR, "实例未初始化");
        }

        if (jobName == null || "".equals(jobName)) {
            throw HelperException.newException(QuartzError.PARAMETER_ERROR, "未指定Job Name");
        }

        if (jobGroup == null || "".equals(jobGroup)) {
            throw HelperException.newException(QuartzError.PARAMETER_ERROR, "未指定Job Group");
        }

        try {
            Matcher<JobKey> matcher = KeyMatcher.keyEquals(new JobKey(jobName, jobGroup));
            scheduler.getListenerManager().addJobListener(jobListener, matcher);
        } catch (SchedulerException e) {
            logger.error(String.format("为任务:[%s],组:[%s]添加监听器时失败", jobName, jobGroup), e);
            throw HelperException.newException(QuartzError.ADD_LISTENER_ERROR, e);
        }

    }

    /**
     * 检查任务是否运行
     *
     * @param jobName  任务名称
     * @param jobGroup 任务组名称
     * @return true ：存在 | false ：不存在
     */
    public static boolean checkExists(String jobName, String jobGroup) {
        if (scheduler == null) {
            throw HelperException.newException(QuartzError.INIT_ERROR, "实例未初始化");
        }

        if (jobName == null || "".equals(jobName)) {
            throw HelperException.newException(QuartzError.PARAMETER_ERROR, "未指定Job Name");
        }

        if (jobGroup == null || "".equals(jobGroup)) {
            throw HelperException.newException(QuartzError.PARAMETER_ERROR, "未指定Job Group");
        }

        try {
            return scheduler.checkExists(JobKey.jobKey(jobName, jobGroup));
        } catch (SchedulerException e) {
            logger.error("检查任务是否存在异常", e);
            throw HelperException.newException(QuartzError.CHECK_EXISTS_ERROR, e);
        }
    }


    /**
     * 添加任务
     * 将任务添加至默认组
     * 将触发器添加至默认组
     * 不鞋带附加数据
     *
     * @param jobName     任务名称
     * @param triggerName 触发器名称
     * @param consumer    消费逻辑
     * @param builder     触发器
     */
    public static void addJob(String jobName, String triggerName,
                              Class<? extends Job> consumer, ScheduleBuilder<? extends Trigger> builder) {
        addJob(jobName, JOB_GROUP_NAME, triggerName, TRIGGER_GROUP_NAME, consumer, builder);
    }

    /**
     * 添加任务
     * 不携带附加数据
     *
     * @param jobName      任务名称
     * @param jobGroup     任务组
     * @param triggerName  触发器名称
     * @param triggerGroup 触发器组
     * @param consumer     消费逻辑
     * @param builder      触发器
     */
    public static void addJob(String jobName, String jobGroup, String triggerName, String triggerGroup,
                              Class<? extends Job> consumer, ScheduleBuilder<? extends Trigger> builder) {
        addJob(jobName, jobGroup, triggerName, triggerGroup, consumer, builder, null);
    }

    /**
     * 添加任务
     *
     * @param jobName      任务名称
     * @param jobGroup     任务组
     * @param triggerName  触发器名称
     * @param triggerGroup 触发器组
     * @param consumer     消费逻辑
     * @param builder      触发器
     * @param data         附加数据
     */
    public static void addJob(String jobName, String jobGroup, String triggerName, String triggerGroup,
                              Class<? extends Job> consumer, ScheduleBuilder<? extends Trigger> builder,
                              Map<String, Object> data) {
        if (scheduler == null) {
            throw HelperException.newException(QuartzError.INIT_ERROR, "实例未初始化");
        }

        if (jobName == null || "".equals(jobName)) {
            throw HelperException.newException(QuartzError.PARAMETER_ERROR, "未指定Job Name");
        }

        if (jobGroup == null || "".equals(jobGroup)) {
            throw HelperException.newException(QuartzError.PARAMETER_ERROR, "未指定Job Group");
        }

        if (triggerName == null || "".equals(triggerName)) {
            throw HelperException.newException(QuartzError.PARAMETER_ERROR, "未指定Trigger Name");
        }

        if (triggerGroup == null || "".equals(triggerGroup)) {
            throw HelperException.newException(QuartzError.PARAMETER_ERROR, "未指定Trigger Group");
        }

        try {
            JobDetail job = newJob(consumer)
                    .withIdentity(jobName, jobGroup)
                    .build();

            Trigger trigger = newTrigger()
                    .withIdentity(triggerName, triggerGroup)
                    .withSchedule(builder)
                    .build();

            if (data != null) {
                data.forEach((k, v) -> job.getJobDataMap().put(k, v));
            }

            scheduler.scheduleJob(job, trigger);
        } catch (SchedulerException e) {
            logger.error(String.format("添加任务:[%s]失败", jobName), e);
            throw HelperException.newException(QuartzError.ADD_JOB_ERROR, e);
        }
    }

    /**
     * 停止任务
     *
     * @param jobName 任务名称
     * @return true ｜ false
     */
    public static boolean stopJob(String jobName) {
        return stopJob(jobName, JOB_GROUP_NAME);
    }

    /**
     * 停止任务
     *
     * @param jobName  任务名称
     * @param jobGroup 任务组
     * @return true ｜ false
     */
    public static boolean stopJob(String jobName, String jobGroup) {

        if (scheduler == null) {
            throw HelperException.newException(QuartzError.INIT_ERROR, "实例未初始化");
        }

        if (jobName == null || "".equals(jobName)) {
            throw HelperException.newException(QuartzError.PARAMETER_ERROR, "未指定Job Name");
        }

        if (jobGroup == null || "".equals(jobGroup)) {
            throw HelperException.newException(QuartzError.PARAMETER_ERROR, "未指定Job Name");
        }
        try {
            boolean result = scheduler.deleteJob(JobKey.jobKey(jobName, jobGroup));
            logger.info(String.format("结束任务:[%s],返回:[%s]", jobName, result));
            return result;
        } catch (SchedulerException e) {
            logger.error(String.format("停止任务:[%s]异常", jobName), e);
            throw HelperException.newException(QuartzError.STOP_JOB_ERROR, e);
        }
    }

    /**
     * 尝试中断任务
     *
     * @param jobName 任务名称
     */
    public static boolean interruptJob(String jobName) {
        return interruptJob(jobName, JOB_GROUP_NAME);
    }

    /**
     * 尝试中断任务
     *
     * @param jobName  任务名称
     * @param jobGroup 任务组
     */
    public static boolean interruptJob(String jobName, String jobGroup) {

        if (scheduler == null) {
            throw HelperException.newException(QuartzError.INIT_ERROR, "实例未初始化");
        }

        if (jobName == null || "".equals(jobName)) {
            throw HelperException.newException(QuartzError.PARAMETER_ERROR, "未指定Job Name");
        }

        if (jobGroup == null || "".equals(jobGroup)) {
            throw HelperException.newException(QuartzError.PARAMETER_ERROR, "未指定Job Name");
        }

        try {
            boolean result = scheduler.interrupt(JobKey.jobKey(jobName, jobGroup));
            logger.info(String.format("中断任务:[%s],返回:[%s]", jobName, jobGroup), result);
            return result;
        } catch (SchedulerException e) {
            logger.error(String.format("中断任务:[%s]异常", jobName), e);
            throw HelperException.newException(QuartzError.INTERRUPT_JOB_ERROR, e);
        }

    }

    /**
     * 停止所有任务
     */
    public static void stopAllJob() {
        try {
            List<JobExecutionContext> jobCtx = scheduler.getCurrentlyExecutingJobs();

            jobCtx.forEach(ctx -> {
                interruptJob(ctx.getJobDetail().getKey().getName());
                stopJob(ctx.getJobDetail().getKey().getName());
            });
        } catch (SchedulerException e) {
            logger.error("停止所有任务时异常", e);
            throw HelperException.newException(QuartzError.STOP_JOB_ERROR, e);
        }
    }


}
