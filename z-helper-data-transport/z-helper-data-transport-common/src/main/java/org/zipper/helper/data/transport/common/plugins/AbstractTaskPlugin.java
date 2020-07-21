package org.zipper.helper.data.transport.common.plugins;


import org.zipper.helper.data.transport.common.collectors.TaskPluginCollector;

public abstract class AbstractTaskPlugin extends AbstractPlugin {

    private TaskPluginCollector taskPluginCollector;

    public TaskPluginCollector getTaskPluginCollector() {
        return taskPluginCollector;
    }

    public void setTaskPluginCollector(TaskPluginCollector taskPluginCollector) {
        this.taskPluginCollector = taskPluginCollector;
    }
}