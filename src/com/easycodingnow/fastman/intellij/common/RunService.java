package com.easycodingnow.fastman.intellij.common;

import com.easycodingnow.fastman.intellij.UiUtil;
import com.google.gson.Gson;
import com.intellij.openapi.project.Project;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;

/**
 * @author lihao
 */
public class RunService {


    public static void postAgent(String url, Request request, Project project) {
        new Thread(() -> {
            PostMethod postMethod = new PostMethod(url);
            HttpClient client = new HttpClient();
            try {
                client.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
                postMethod.setRequestEntity(new StringRequestEntity(new Gson().toJson(request), "application/json", "UTF-8"));
                client.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
                int status = client.executeMethod(postMethod);
                if (status == HttpStatus.SC_OK) {
                    UiUtil.ok("请求成功! 返回值: " + postMethod.getResponseBodyAsString(), project);
                } else {
                    UiUtil.error("执行失败！http错误码: " + status, project);
                }
            } catch (Exception ex) {
                UiUtil.error("执行失败: " + ex.getMessage(), project);
            } finally {
                postMethod.releaseConnection();
            }
        }).start();
    }
}
