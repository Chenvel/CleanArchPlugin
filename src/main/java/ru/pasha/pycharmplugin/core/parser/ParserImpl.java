package ru.pasha.pycharmplugin.core.parser;

import com.intellij.lang.ASTNode;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import org.jetbrains.annotations.NotNull;
import ru.pasha.pycharmplugin.pojo.Info;
import ru.pasha.pycharmplugin.storage.ProjectInfoStorage;

import java.util.*;

public class ParserImpl implements Parser {

    private final static String FROM_IMPORT_STATEMENT = "FROM_IMPORT_STATEMENT";
    private final static String IMPORT_STATEMENT = "IMPORT_STATEMENT";
    private final static String CLASS_STATEMENT = "CLASS_DECLARATION";
    private final List<String> EXCLUDED_ELEMENTS = List.of(".idea", "venv", "__pycache__", "__init__");
    private final ParseUtils parseUtils = new ParseUtils();

    @Override
    public void parsePythonFile(@NotNull Project project, @NotNull VirtualFile file) {
        PsiFile psiFile = PsiManager.getInstance(project).findFile(file);
        ASTNode childNode = psiFile.getNode().getFirstChildNode();

        while (childNode != null) {
            String statementName = childNode.getElementType().getDebugName();

            if (statementName.equals(FROM_IMPORT_STATEMENT)) {
                parseUtils.packageParser(childNode, file);
            } else if (statementName.equals(IMPORT_STATEMENT)) {
                parseUtils.fileParser(childNode, file);
            }

            if (statementName.equals(CLASS_STATEMENT)) {
                parseUtils.abstractClassParser(childNode, file);
            }

            childNode = childNode.getTreeNext();
        }
    }

    @Override
    public void parseAllFiles(VirtualFile projectDir) {
        Map<VirtualFile, Info> pythonFile = new HashMap<>();
        List<String> pythonFileNames = new ArrayList<>();

        // bfs

        Queue<VirtualFile> files = new LinkedList<>();
        files.add(projectDir);
        while (!files.isEmpty()) {
            VirtualFile file = files.poll();

            for (VirtualFile virtualFile : file.getChildren()) {
                if (!EXCLUDED_ELEMENTS.contains(virtualFile.getNameWithoutExtension())) {
                    files.add(virtualFile);
                }
            }

            if (Objects.equals(file.getExtension(), "py")) {
                pythonFile.putIfAbsent(file, new Info());
                pythonFileNames.add(file.getNameWithoutExtension());
            }
        }

        ProjectInfoStorage.setPythonFiles(pythonFile);
        ProjectInfoStorage.setPythonFileNames(pythonFileNames);
    }
}
