package org.zipper.helper.data.transport.core;

import cn.hutool.core.io.FileUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zipper.helper.data.transport.common.columns.ColumnCast;
import org.zipper.helper.data.transport.common.commons.CoreConstant;
import org.zipper.helper.data.transport.common.commons.VMInfo;
import org.zipper.helper.data.transport.core.container.AbstractContainer;
import org.zipper.helper.data.transport.core.container.JobContainer;
import org.zipper.helper.data.transport.core.utils.LoadUtil;
import org.zipper.helper.util.json.JsonObject;

import java.io.IOException;
import java.util.Set;

public class Engine {

    private static final Logger logger = LoggerFactory.getLogger(Engine.class);
    private final JsonObject allConfig;

    public Engine(JsonObject allConfig) {
        this.allConfig = allConfig;
    }

    /**
     * 交换入口
     */
    public void entry() throws IOException {
        String jobId = allConfig.getString(CoreConstant.JOB_ID, "undefined");
        allConfig.set(CoreConstant.JOB_ID, jobId);


        Thread.currentThread().setName("job-" + jobId);
        //打印vmInfo
        VMInfo vmInfo = VMInfo.getVmInfo();
        if (vmInfo != null) {
            logger.info(vmInfo.toString());
        }

        logger.info("\n" + Engine.filterJobConfiguration(allConfig) + "\n");

        logger.debug(allConfig.toJSON());

        start();
    }

    // 注意屏蔽敏感信息
    public static String filterJobConfiguration(final JsonObject configuration) {
        JsonObject jobConfWithSetting = configuration.getJsonObject("job").clone();

        JsonObject jobContent = jobConfWithSetting.getJsonObject("content");

        filterSensitiveConfiguration(jobContent);

        jobConfWithSetting.set("content", jobContent);

        return jobConfWithSetting.beautify();
    }

    public static void filterSensitiveConfiguration(JsonObject configuration) {
        Set<String> keys = configuration.getKeys();
        for (final String key : keys) {
            boolean isSensitive = StringUtils.endsWithIgnoreCase(key, "password")
                    || StringUtils.endsWithIgnoreCase(key, "accessKey");
            if (isSensitive && configuration.get(key) instanceof String) {
                configuration.set(key, configuration.getString(key).replaceAll(".", "*"));
            }
        }
    }

    public void start() throws IOException {

        ColumnCast.bind(allConfig);

        LoadUtil.bind();

        AbstractContainer container = new JobContainer(allConfig);

        container.start();

    }

    public static void main(String[] args) {

        try {
            String a = FileUtil.readString("/Users/zhuxj/IdeaProjects/z-dtx/allConfig.json", "utf-8");

            Engine engine = new Engine(JsonObject.from(a));
            engine.entry();
        } catch (Throwable e) {
            logger.error(e.getMessage(), e);
        }

    }
}
