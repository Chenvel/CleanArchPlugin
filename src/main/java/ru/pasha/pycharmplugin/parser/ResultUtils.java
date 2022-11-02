package ru.pasha.pycharmplugin.parser;

import com.intellij.lang.ASTNode;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import ru.pasha.pycharmplugin.calculator.Calculator;
import ru.pasha.pycharmplugin.notification.Notifier;
import ru.pasha.pycharmplugin.pojo.Result;
import ru.pasha.pycharmplugin.storage.ProjectInfoStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ResultUtils {

    public void updateDirectoryOrFileInfo(String packageName, VirtualFile file) {
        ProjectInfoStorage.getPythonFiles().keySet().forEach(key -> {
            if (key.getNameWithoutExtension().equals(packageName)) {
                ProjectInfoStorage.getPythonFiles().get(key).increaseFanOut();
                ProjectInfoStorage.getPythonFiles().get(file).increaseFanIn();
            }
        });
    }

    public void updateAbsCounter(ASTNode arguments, VirtualFile file) {
        while (arguments != null) {
            String argName = arguments.getText();
            argName = argName.contains(".") ? argName.substring(0, argName.indexOf('.')) : argName;

            if (argName.equalsIgnoreCase("abc")) {
                ProjectInfoStorage.getPythonFiles().get(file).increaseAbs();
                break;
            }

            arguments = arguments.getTreeNext();
        }
    }

    public void showNotification(VirtualFile newFile) {
        List<Result> result = ProjectInfoStorage.getResults().stream()
                .filter(res -> res.getFile().equals(newFile))
                .collect(Collectors.toList());

        if (result.isEmpty()) return;

        float I = result.get(0).getCord().keySet().stream().findFirst().get();
        float A = result.get(0).getCord().values().stream().findFirst().get();

        Notifier.infoNotification(String.format("(I = %.2f, A = %.2f)\n", I, A));
    }

    public void parseIAndA(Project project, VirtualFile rootDir) {
        Parser parser = new ParserImpl();

        Editor textEditor = FileEditorManager.getInstance(project).getSelectedTextEditor();
        if (textEditor != null) {
            ProjectInfoStorage.setOpenedFile(FileDocumentManager.getInstance().getFile(textEditor.getDocument()));
        }

        parser.parseAllFiles(rootDir);

        ProjectInfoStorage.getPythonFiles().keySet()
                .forEach(file -> parser.parsePythonFile(ProjectInfoStorage.getProject(), file));

        Calculator calculator = new Calculator();

        List<Result> results = new ArrayList<>();

        ProjectInfoStorage.getPythonFiles().forEach((file, info) -> results.add(calculator.calculate(file, info)));

        ProjectInfoStorage.setResults(results);
        System.out.println(ProjectInfoStorage.getResults());
    }
}
