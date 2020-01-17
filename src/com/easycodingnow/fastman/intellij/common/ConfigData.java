package com.easycodingnow.fastman.intellij.common;

/**
 * @author lihao
 */
public class ConfigData {
    private String path;

    private Request recentRequest;


    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Request getRecentRequest() {
        return recentRequest;
    }

    public void setRecentRequest(Request recentRequest) {
        this.recentRequest = recentRequest;
    }
}
