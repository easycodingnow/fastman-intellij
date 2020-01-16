package com.easycodingnow.fastman.intellij.common;

/**
 * @author lihao
 */
public class ConfigData {
    private String path;

    private String dubboHost;

    private String dubboGroup;


    public String getDubboGroup() {
        return dubboGroup;
    }

    public void setDubboGroup(String dubboGroup) {
        this.dubboGroup = dubboGroup;
    }

    public String getDubboHost() {
        return dubboHost;
    }

    public void setDubboHost(String dubboHost) {
        this.dubboHost = dubboHost;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
