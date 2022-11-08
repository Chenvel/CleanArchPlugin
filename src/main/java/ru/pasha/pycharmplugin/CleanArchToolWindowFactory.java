package ru.pasha.pycharmplugin;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import org.jetbrains.annotations.NotNull;
import ru.pasha.pycharmplugin.gui.AcyclicDependencies;
import ru.pasha.pycharmplugin.gui.Gui;
import ru.pasha.pycharmplugin.storage.ProjectInfoStorage;

public class CleanArchToolWindowFactory implements ToolWindowFactory {

    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        ProjectInfoStorage.setProject(project);

        Gui gui = new Gui();
        AcyclicDependencies acyclicDependencies = new AcyclicDependencies();

        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
        Content mainSequence = contentFactory.createContent(gui.getContent(), "Main Sequence", true);
        toolWindow.getContentManager().addContent(mainSequence);
        Content content = contentFactory.createContent(acyclicDependencies.getContent(), "Acyclic Dependencies", true);
        toolWindow.getContentManager().addContent(content);
    }
}
