package ru.pasha.pycharmplugin.storage;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import ru.pasha.pycharmplugin.pojo.Info;
import ru.pasha.pycharmplugin.pojo.Result;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProjectInfoStorage {

    private static VirtualFile openedFile;
    private static Project project;
    private static Map<VirtualFile, Info> pythonFiles = new HashMap<>();
    private static Map<String, List<String>> dependedFiles = new HashMap<>();
    private static Map<String, List<String>> acyclicDependedFiles = new HashMap<>();
    private static List<String> pythonFileNames = new ArrayList<>();
    private static final List<String> alreadyScanned = new ArrayList<>();
    private static List<Result> results = new ArrayList<>();

    public static Map<String, List<String>> getAcyclicDependedFiles() {
        return acyclicDependedFiles;
    }

    public static void setAcyclicDependedFiles(Map<String, List<String>> acyclicDependedFiles) {
        ProjectInfoStorage.acyclicDependedFiles = acyclicDependedFiles;
    }

    public static VirtualFile getOpenedFile() {
        return openedFile;
    }

    public static void setOpenedFile(VirtualFile openedFile) {
        ProjectInfoStorage.openedFile = openedFile;
    }

    public static Project getProject() {
        return project;
    }

    public static void setProject(Project project) {
        ProjectInfoStorage.project = project;
    }

    public static Map<VirtualFile, Info> getPythonFiles() {
        return pythonFiles;
    }

    public static void setPythonFiles(Map<VirtualFile, Info> pythonFiles) {
        ProjectInfoStorage.pythonFiles = pythonFiles;
    }

    public static List<String> getAlreadyScanned() {
        return alreadyScanned;
    }

    public static void addAlreadyScanned(String alreadyScanned) {
        ProjectInfoStorage.alreadyScanned.add(alreadyScanned);
    }

    public static List<String> getPythonFileNames() {
        return pythonFileNames;
    }

    public static void setPythonFileNames(List<String> pythonFileNames) {
        ProjectInfoStorage.pythonFileNames = pythonFileNames;
    }

    public static List<Result> getResults() {
        return results;
    }

    public static void setResults(List<Result> results) {
        ProjectInfoStorage.results = results;
    }

    public static Map<String, List<String>> getDependedFiles() {
        return dependedFiles;
    }

    public static void addDependedFile(String file, String dependedFile) {
        if (!ProjectInfoStorage.dependedFiles.containsKey(file)) {
            ProjectInfoStorage.dependedFiles.put(file, new ArrayList<>());
        }

        ProjectInfoStorage.dependedFiles.get(file).add(dependedFile);
    }

    public static void clear() {
        ProjectInfoStorage.acyclicDependedFiles.clear();
        ProjectInfoStorage.dependedFiles.clear();
        ProjectInfoStorage.pythonFileNames.clear();
        ProjectInfoStorage.pythonFiles.clear();
        ProjectInfoStorage.alreadyScanned.clear();
        ProjectInfoStorage.results.clear();
    }
}
