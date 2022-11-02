package ru.pasha.pycharmplugin;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.DumbService;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ProjectFileIndex;
import org.jetbrains.annotations.NotNull;
import ru.pasha.pycharmplugin.parser.ResultUtils;
import ru.pasha.pycharmplugin.storage.ProjectInfoStorage;

public class InvokePluginAction extends AnAction {

    private final ResultUtils resultUtils = new ResultUtils();

    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        ProjectInfoStorage.clear();
        Project project = ProjectInfoStorage.getProject();

        DumbService.getInstance(project).runWhenSmart(() -> {

            resultUtils.parseIAndA(
                    project, ProjectFileIndex.getInstance(project).getContentRootForFile(project.getProjectFile())
            );
        });
    }
}
