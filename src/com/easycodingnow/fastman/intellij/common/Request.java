package com.easycodingnow.fastman.intellij.common;

import java.util.List;

/**
 * @author lihao
 * @since 2020-01-17
 */
public class Request {

    private String className;

    private String methodName;

    private String params;

    private List<String> paramTypes;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public List<String> getParamTypes() {
        return paramTypes;
    }

    public void setParamTypes(List<String> paramTypes) {
        this.paramTypes = paramTypes;
    }
}
