package com.easycodingnow.fastman.intellij.common;

import com.easycodingnow.fastman.intellij.ui.ConfigUI;
import com.google.gson.Gson;
import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.options.SearchableConfigurable;
import org.apache.commons.lang3.mutable.MutableBoolean;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class Configurable implements SearchableConfigurable {

    private ConfigUI configUi;

    private MutableBoolean modified = new MutableBoolean(false);


    @NotNull
    @Override
    public String getId() {
        return GlobalConfig.CONFIG_KEY;
    }

    @Nls(capitalization = Nls.Capitalization.Title)
    @Override
    public String getDisplayName() {
        return getId();
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        if (null == configUi) {
            configUi = new ConfigUI(modified);
        }
        return configUi.getContentPane();
    }

    @Override
    public boolean isModified() {
        return configUi.getModified().getValue();
    }

    @Override
    public void apply() {
        ConfigData config = new ConfigData();
        config.setPath(configUi.getTextField1().getText());
        PropertiesComponent.getInstance().setValue(GlobalConfig.CONFIG_KEY, new Gson().toJson(config));
        configUi.getModified().setValue(false);
    }
}
