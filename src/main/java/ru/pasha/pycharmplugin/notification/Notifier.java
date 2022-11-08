package ru.pasha.pycharmplugin.notification;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import ru.pasha.pycharmplugin.storage.ProjectInfoStorage;

public class Notifier {

    public static void infoNotification(String content){
        Notification n = new Notification(
                "ru.pasha.pycharmplugin",
                "CleanArch plugin info",
                content,
                NotificationType.INFORMATION
        );
        n.notify(ProjectInfoStorage.getProject());
        n.hideBalloon();
    }
}
