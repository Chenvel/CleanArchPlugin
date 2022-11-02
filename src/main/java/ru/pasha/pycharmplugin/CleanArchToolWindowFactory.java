package ru.pasha.pycharmplugin;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import org.jetbrains.annotations.NotNull;
import ru.pasha.pycharmplugin.gui.Gui;
import ru.pasha.pycharmplugin.storage.ProjectInfoStorage;

public class CleanArchToolWindowFactory implements ToolWindowFactory {

    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        ProjectInfoStorage.setProject(project);

        Gui gui = new Gui();
        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();

        Content content = contentFactory.createContent(gui.getContent(), "Main Sequence", true);
        toolWindow.getContentManager().addContent(content);
    }
}
