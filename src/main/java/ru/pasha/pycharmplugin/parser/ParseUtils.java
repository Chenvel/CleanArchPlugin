package ru.pasha.pycharmplugin.parser;

import com.intellij.lang.ASTNode;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.tree.IElementType;
import ru.pasha.pycharmplugin.storage.ProjectInfoStorage;

import static ru.pasha.pycharmplugin.storage.ProjectInfoStorage.getAlreadyScanned;
import static ru.pasha.pycharmplugin.storage.ProjectInfoStorage.getPythonFileNames;

public class ParseUtils {

    private final static ResultUtils resultUtil = new ResultUtils();
    private final static String FROM_IMPORT_STATEMENT = "FROM_IMPORT_STATEMENT";
    private final static IElementType REFERENCE_EXPRESSION = IElementType.find((short) 597);
    private final static IElementType IMPORT_ELEMENT = IElementType.find((short) 564);

    public void packageParser(ASTNode childNode, VirtualFile file) {
        String packageName = childNode.getFirstChildNode().getTreeNext().getTreeNext().getText();
                //findChildByType(REFERENCE_EXPRESSION).getText()

        int i = packageName.lastIndexOf('.');
        String name = packageName.substring(i + 1);

        if (!getAlreadyScanned().contains(name)) {
            if (getPythonFileNames().contains(name)) {
                resultUtil.updateDirectoryOrFileInfo(name, file);
                ProjectInfoStorage.addAlreadyScanned(name);
            } else {
                fileParser(childNode, file);
            }
        }
    }

    public void abstractClassParser(ASTNode childNode, VirtualFile file) {
        ProjectInfoStorage.getPythonFiles().get(file).increaseClasses();
        ASTNode blockWithArguments = childNode.getFirstChildNode().getTreeNext().getTreeNext().getTreeNext();
        //findChildByType(CLASS_ARGUMENT_LIST_TYPE)
        if (blockWithArguments.getFirstChildNode() == null) return;
        ASTNode arguments = blockWithArguments.getFirstChildNode().getTreeNext();
        //findChildByType(REFERENCE_EXPRESSION)

        if (arguments != null) {
            arguments = arguments.getFirstChildNode();

            resultUtil.updateAbsCounter(arguments, file);
        }
    }

    public void fileParser(ASTNode childNode, VirtualFile file) {
        ASTNode importPackage = childNode.getFirstChildNode().getTreeNext().getTreeNext();
        // .findChildByType(IMPORT_ELEMENT)

        if (childNode.getElementType().getDebugName().equals(FROM_IMPORT_STATEMENT)) {
            String name = importPackage.getText();

            resultUtil.updateDirectoryOrFileInfo(name, file);
            ProjectInfoStorage.addAlreadyScanned(name);
        } else {
            String packageName = importPackage.getFirstChildNode().getText();
            int i = packageName.indexOf('.');

            String name = packageName.substring(i + 1);

            if (!getAlreadyScanned().contains(name)) {
                resultUtil.updateDirectoryOrFileInfo(name, file);
                ProjectInfoStorage.addAlreadyScanned(name);
            }
        }
    }


}
