package ru.pasha.pycharmplugin.core.parser;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;

public interface Parser {

    void parsePythonFile(Project project, VirtualFile file);
    void parseAllFiles(VirtualFile projectDir);
}
