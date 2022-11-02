package ru.pasha.pycharmplugin;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ProjectFileIndex;
import com.intellij.openapi.startup.StartupActivity;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;
import ru.pasha.pycharmplugin.parser.ResultUtils;
import ru.pasha.pycharmplugin.storage.ProjectInfoStorage;

public class Start implements StartupActivity {

    private static final ResultUtils resultUtils = new ResultUtils();

    @Override
    public void runActivity(@NotNull Project project) {
        ProjectInfoStorage.setProject(project);
        VirtualFile rootDir = ProjectFileIndex.getInstance(project).getContentRootForFile(project.getProjectFile());
        resultUtils.parseIAndA(project, rootDir);

        Editor editor = FileEditorManager.getInstance(project).getSelectedTextEditor();
        if (editor != null) {
            VirtualFile newFile = FileDocumentManager.getInstance().getFile(editor.getDocument());
            resultUtils.showNotification(newFile);
        }
    }
}
