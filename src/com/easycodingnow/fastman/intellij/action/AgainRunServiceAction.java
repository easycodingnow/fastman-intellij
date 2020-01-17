package com.easycodingnow.fastman.intellij.action;

import com.easycodingnow.fastman.intellij.UiUtil;
import com.easycodingnow.fastman.intellij.common.ConfigData;
import com.easycodingnow.fastman.intellij.common.GlobalConfig;
import com.easycodingnow.fastman.intellij.common.RunService;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

/**
 * @author lihao
 */
public class AgainRunServiceAction extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        Editor editor = e.getDataContext().getData(CommonDataKeys.EDITOR);
        if (editor == null) {
            return;
        }
        Project project = editor.getProject();
        ConfigData configData = GlobalConfig.getConfig();
        if (StringUtils.isEmpty(configData.getPath())) {
            UiUtil.error("请先配置代理path！", project);
            return;
        }
        String url = configData.getPath();

        if (configData.getRecentRequest() == null) {
            UiUtil.error("未找到最近运行的方法！", project);
            return;
        }

        RunService.postAgent(url, configData.getRecentRequest(), project);

    }



}
