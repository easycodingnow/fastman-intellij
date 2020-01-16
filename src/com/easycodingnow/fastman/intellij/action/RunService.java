package com.easycodingnow.fastman.intellij.action;

import com.easycodingnow.fastman.intellij.UiUtil;
import com.easycodingnow.fastman.intellij.common.ConfigData;
import com.easycodingnow.fastman.intellij.common.GlobalConfig;
import com.google.gson.Gson;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.psi.javadoc.PsiDocComment;
import com.intellij.psi.javadoc.PsiDocTag;
import com.intellij.psi.util.PsiTreeUtil;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lihao
 */
public class RunService  extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        Editor editor = e.getDataContext().getData(CommonDataKeys.EDITOR);
        PsiFile psiFile = e.getDataContext().getData(CommonDataKeys.PSI_FILE);
        if (!(psiFile instanceof PsiJavaFile)) {
            UiUtil.error("当前文件不是java类！", null);
            return;
        }
        PsiJavaFile psiJavaFile = (PsiJavaFile) psiFile;
        if (editor == null) {
            UiUtil.error("获取editor为空！", null);
            return;
        }
        Project project = editor.getProject();
        PsiElement referenceAt = psiFile.findElementAt(editor.getCaretModel().getOffset());
        PsiClass selectedClass = (PsiClass) PsiTreeUtil.getContextOfType(referenceAt, new Class[]{PsiClass.class});
        PsiMethod selectedMethod = (PsiMethod) PsiTreeUtil.getContextOfType(referenceAt, new Class[]{PsiMethod.class});
        if (selectedClass == null) {
            UiUtil.error("请选择要运行的类！", project);
            return;
        }

        if (selectedMethod == null) {
            UiUtil.error("请选择要运行的方法！", project);
            return;
        }

        PsiDocComment psiDocComment = selectedMethod.getDocComment();

        Request request = new Request();
        request.setClassName(psiJavaFile.getPackageName() + "." + selectedClass.getName());
        request.setMethodName(selectedMethod.getName());

        if (psiDocComment == null || psiDocComment.getTags().length == 0) {
            UiUtil.error("缺少执行入参@test的注释！", project);
            return;
        }
        for (PsiDocTag psiDocTag:psiDocComment.getTags()) {
            if ("test".equals(psiDocTag.getName())) {
                String requestBody = psiDocTag.getText().substring(5)
                        .replaceAll("\n", "")
                        .replaceAll("\\*", "")
                        .replaceAll("\t", "").trim();
                if (StringUtils.isBlank(requestBody)) {
                    UiUtil.error("测试入参不能为空!！", project);
                    return;
                }
                request.setParams(requestBody);
                break;
            }
        }


        PsiParameterList parameterList = selectedMethod.getParameterList();
        if (parameterList.getParametersCount() == 0) {
            request.setParamTypes(new ArrayList<>());
        } else {
            List<String> methodParamList = new ArrayList<>();
            for (PsiParameter psiParameter:parameterList.getParameters()) {
                String type = psiParameter.getTypeElement().getType().getInternalCanonicalText().split("\\<", 2)[0];
                methodParamList.add(type);
            }
            request.setParamTypes(methodParamList);
        }

        ConfigData configData = GlobalConfig.getConfig();
        if (StringUtils.isEmpty(configData.getPath())) {
            UiUtil.error("请先配置代理path！", project);
            return;
        }
        String url = configData.getPath();
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

    public  static  class Request {

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



}
