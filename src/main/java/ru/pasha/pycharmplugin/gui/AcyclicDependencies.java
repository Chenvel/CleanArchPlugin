package ru.pasha.pycharmplugin.gui;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ProjectFileIndex;
import ru.pasha.pycharmplugin.core.acyclic.AcyclicSearcher;
import ru.pasha.pycharmplugin.core.parser.ResultUtils;
import ru.pasha.pycharmplugin.storage.ProjectInfoStorage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AcyclicDependencies {
    private JPanel mainPanel;
    private JPanel controlPanel;
    private JPanel buttonPanel;
    private JPanel reloadPanel;
    private JButton reloadButton;
    private JPanel infoPanel;
    private JTextPane textPane;
    private final static AcyclicSearcher acyclicSearcher = new AcyclicSearcher();
    private final static ResultUtils resultUtils = new ResultUtils();

    public AcyclicDependencies() {
        initTextPane();
        reloadButton.setOpaque(true);
        reloadButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                reloadButton.setBackground(new Color(80, 83, 85));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                reloadButton.setBackground(new Color(60, 63, 65));
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                reloadButton.setBackground(new Color(80, 83, 85));
                ProjectInfoStorage.clear();
                Project project = ProjectInfoStorage.getProject();
                resultUtils.parseIAndA(project, ProjectFileIndex.getInstance(project).getContentRootForFile(project.getProjectFile()));
                searchForAcyclicDependencies();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                reloadButton.setBackground(new Color(100, 103, 105));
            }
        });
    }

    private void initTextPane() {
        Font font = new Font("Roboto", Font.PLAIN, 12);
        textPane = new JTextPane();
        textPane.setEnabled(false);
        textPane.setFont(font);
        textPane.setDisabledTextColor(new Color(33, 32, 32));
        textPane.setText("Press reload button");
        infoPanel.add(textPane);
    }

    private void searchForAcyclicDependencies() {
        acyclicSearcher.search();
        StringBuilder builder = new StringBuilder();
        ProjectInfoStorage.getAcyclicDependedFiles().forEach((file, dependedFiles) -> {
            dependedFiles.forEach(dependedFile -> {
                builder.append(String.format("%s.py is cyclically depended on %s.py\n", file, dependedFile));
            });
        });
        if (builder.length() == 0) builder.append("No cyclically depended files found");
        textPane.setText(builder.toString());
        infoPanel.remove(textPane);
        infoPanel.add(textPane);
    }

    public JPanel getContent() {

        return mainPanel;
    }
}
