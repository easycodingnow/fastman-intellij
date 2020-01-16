package com.easycodingnow.fastman.intellij.common;

import com.google.gson.Gson;
import com.intellij.ide.util.PropertiesComponent;
import org.apache.commons.lang3.StringUtils;

public class GlobalConfig {

    public static String CONFIG_KEY = "Fastman";


    public static ConfigData getConfig() {
        String v = PropertiesComponent.getInstance().getValue(CONFIG_KEY);
        if (StringUtils.isNotEmpty(v)) {
            return new Gson().fromJson(v, ConfigData.class);
        }
        return new ConfigData();

    }

}
