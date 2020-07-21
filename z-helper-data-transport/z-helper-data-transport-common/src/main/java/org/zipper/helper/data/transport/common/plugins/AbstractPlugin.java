package org.zipper.helper.data.transport.common.plugins;

import org.zipper.helper.util.json.JsonObject;

public abstract class AbstractPlugin {
    private String pluginName;
    private JsonObject pluginConfig;
    private JsonObject allConfig;

    public AbstractPlugin() {
    }

    public abstract void init();

    public void prepare() {

    }

    public void post() {

    }

    public abstract void destroy();

    public void setPluginConf(JsonObject pluginConf) {
        this.pluginConfig = pluginConf;
    }

    public void setPluginJobConf(JsonObject jobConf) {
        this.allConfig = jobConf;
    }

    public String getPluginName() {
        return pluginName;
    }

    public void setPluginName(String pluginName) {
        this.pluginName = pluginName;
    }

    public JsonObject getPluginConfig() {
        return pluginConfig;
    }

    public void setPluginConfig(JsonObject pluginConfig) {
        this.pluginConfig = pluginConfig;
    }

    public JsonObject getAllConfig() {
        return allConfig;
    }

    public void setAllConfig(JsonObject allConfig) {
        this.allConfig = allConfig;
    }
}
