package ru.pasha.pycharmplugin.notification;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import ru.pasha.pycharmplugin.storage.ProjectInfoStorage;

public class Notifier {

    public static void infoNotification(String content){
        new Notification(
                "ru.pasha.pycharmplugin",
                "CleanArch plugin info",
                content,
                NotificationType.INFORMATION
        ).notify(ProjectInfoStorage.getProject());
    }
}
