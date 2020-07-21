package org.zipper.helper.data.transport.core.container;

import org.zipper.helper.data.transport.common.collectors.PluginCollector;
import org.zipper.helper.util.json.JsonObject;

public abstract class AbstractContainer {
    protected JsonObject allConfig;
    protected PluginCollector pluginCollector;

    public AbstractContainer(JsonObject allConfig) {
        this.allConfig = allConfig;
    }

    public abstract void start();
}
