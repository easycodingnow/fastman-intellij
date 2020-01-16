package com.easycodingnow.fastman.intellij;

import com.intellij.notification.*;
import com.intellij.openapi.project.Project;

/**
 * @author lihao
 * @since 2019-07-07
 */
public class UiUtil {
    private static NotificationGroup notificationGroup;

    static {
        notificationGroup = new NotificationGroup("Java2Json.NotificationGroup", NotificationDisplayType.BALLOON, true);
    }

    static public void error(String msg, Project project) {
        Notification error = notificationGroup.createNotification(msg, NotificationType.ERROR);
        Notifications.Bus.notify(error, project);
    }

    static public void ok(String msg, Project project) {
        Notification error = notificationGroup.createNotification(msg, NotificationType.INFORMATION);
        Notifications.Bus.notify(error, project);
    }

}
