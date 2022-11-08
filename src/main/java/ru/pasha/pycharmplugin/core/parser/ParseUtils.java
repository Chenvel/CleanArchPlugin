package ru.pasha.pycharmplugin.core.parser;

import com.intellij.lang.ASTNode;
import com.intellij.openapi.vfs.VirtualFile;
import ru.pasha.pycharmplugin.storage.ProjectInfoStorage;

import static ru.pasha.pycharmplugin.storage.ProjectInfoStorage.getAlreadyScanned;
import static ru.pasha.pycharmplugin.storage.ProjectInfoStorage.getPythonFileNames;

public class ParseUtils {

    private final static ResultUtils resultUtil = new ResultUtils();
    private final static String FROM_IMPORT_STATEMENT = "FROM_IMPORT_STATEMENT";

    public void packageParser(ASTNode childNode, VirtualFile openedFile) {
        String packageName = childNode.getFirstChildNode().getTreeNext().getTreeNext().getText();

        int i = packageName.lastIndexOf('.');
        String name = packageName.substring(i + 1);

        if (!getAlreadyScanned().contains(name)) {
            if (getPythonFileNames().contains(name)) {
                resultUtil.updateDirectoryOrFileInfo(name, openedFile);
                ProjectInfoStorage.addAlreadyScanned(name);
                String fileName = name.substring(name.lastIndexOf('.') + 1);
                if (ProjectInfoStorage.getPythonFileNames().contains(fileName) && !openedFile.getNameWithoutExtension().equals(fileName)) {
                    ProjectInfoStorage.addDependedFile(openedFile.getNameWithoutExtension(), name);
                }
            } else {
                fileParser(childNode, openedFile);
            }
        }
    }

    public void abstractClassParser(ASTNode childNode, VirtualFile file) {
        ProjectInfoStorage.getPythonFiles().get(file).increaseClasses();
        ASTNode blockWithArguments = childNode.getFirstChildNode().getTreeNext().getTreeNext().getTreeNext();

        if (blockWithArguments.getFirstChildNode() == null) return;
        ASTNode arguments = blockWithArguments.getFirstChildNode().getTreeNext();

        if (arguments != null) {
            arguments = arguments.getFirstChildNode();

            resultUtil.updateAbsCounter(arguments, file);
        }
    }

    public void fileParser(ASTNode childNode, VirtualFile openedFile) {
        ASTNode importPackage = childNode.getFirstChildNode().getTreeNext().getTreeNext();

        if (childNode.getElementType().getDebugName().equals(FROM_IMPORT_STATEMENT)) {
            String name = importPackage.getText();

            if (name.equals(".")) {
                name = importPackage.getTreeNext().getText();
            }

            String fileName = name.substring(name.lastIndexOf('.') + 1);

            resultUtil.updateDirectoryOrFileInfo(name, openedFile);
            ProjectInfoStorage.addAlreadyScanned(name);
            if (ProjectInfoStorage.getPythonFileNames().contains(fileName) && !openedFile.getNameWithoutExtension().equals(fileName)) {
                ProjectInfoStorage.addDependedFile(openedFile.getNameWithoutExtension(), name);
            }
        } else {
            String packageName = importPackage.getFirstChildNode().getText();
            int i = packageName.indexOf('.');

            String name = packageName.substring(i + 1);
            String fileName = name.substring(name.lastIndexOf('.') + 1);

            if (!getAlreadyScanned().contains(name)) {
                resultUtil.updateDirectoryOrFileInfo(name, openedFile);
                ProjectInfoStorage.addAlreadyScanned(name);
                if (ProjectInfoStorage.getPythonFileNames().contains(fileName) && !openedFile.getNameWithoutExtension().equals(fileName)) {
                    ProjectInfoStorage.addDependedFile(openedFile.getNameWithoutExtension(), name);
                }
            }
        }
    }


}
