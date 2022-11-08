package ru.pasha.pycharmplugin.gui;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.fileEditor.FileEditorManagerEvent;
import com.intellij.openapi.fileEditor.FileEditorManagerListener;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ProjectFileIndex;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileManager;
import org.jetbrains.annotations.NotNull;
import ru.pasha.pycharmplugin.core.parser.ResultUtils;
import ru.pasha.pycharmplugin.gui.custom_components.PartButtonDirections;
import ru.pasha.pycharmplugin.gui.custom_components.PartRoundedButton;
import ru.pasha.pycharmplugin.gui.custom_components.RoundedButton;
import ru.pasha.pycharmplugin.storage.ProjectInfoStorage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Gui {

    private JPanel controlPanel;
    private JPanel buttonPanel;
    private JPanel reloadPanel;
    private JPanel contextMenu;
    private JFrame cm;
    private JButton reloadButton;
    private JPanel mainPanel;
    private JPanel mainSequencePanel;
    private JPanel mainSequence;
    private JButton zoneOfPain;
    private JButton zoneOfUseless;
    private final List<JButton> points = new ArrayList<>();
    private final static ResultUtils resultUtils = new ResultUtils();

    public Gui() {
        createUIComponents();
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
                repaintPoints();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                reloadButton.setBackground(new Color(100, 103, 105));
            }
        });


        Project project = ProjectInfoStorage.getProject();

        project.getMessageBus().connect(project).subscribe(FileEditorManagerListener.FILE_EDITOR_MANAGER, new FileEditorManagerListener() {
            @Override
            public void selectionChanged(@NotNull FileEditorManagerEvent event) {
            if (event.getNewFile() != null) {
                Editor textEditor = FileEditorManager.getInstance(project).getSelectedTextEditor();
                if (textEditor != null) {
                    ProjectInfoStorage.setOpenedFile(FileDocumentManager.getInstance().getFile(textEditor.getDocument()));
                }


                if (event.getNewFile().getExtension().equals("py")) {
                    ProjectInfoStorage.clear();
                    resultUtils.parseIAndA(project, ProjectFileIndex.getInstance(project).getContentRootForFile(project.getProjectFile()));
                    resultUtils.showNotification(event.getNewFile());
                }

                // No matter zoneOfPane or zoneOfUseless is null
                if (zoneOfPain != null) {
                    repaintPoints();
                }
            } else {
                ProjectInfoStorage.setOpenedFile(null);
            }
            }
        });
    }

    private void createUIComponents() {
        mainPanel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);

                points.forEach(mainSequence::remove);
                createPoints();
                addZoneOfPain();
                addZoneOfUseless();
            }
        });

        mainSequence = new JPanel(null) {
            @Override
            public void paint(Graphics g) {
                super.paint(g);
                Font font = new Font("Roboto", Font.BOLD, 12);
                g.setFont(font);
                g.drawLine(20, 10, 20, getHeight() - 20);
                g.drawLine(20, getHeight() - 20, getWidth() - 20, getHeight() - 20);
                g.drawString("I", getWidth() / 2, getHeight() - 5);
                g.drawString("A", 5, getHeight() / 2);
            }
        };

        mainSequence.setMinimumSize(new Dimension(200, 200));
        mainSequencePanel.add(mainSequence);
    }

    private void createPoints() {
        points.clear();
        ProjectInfoStorage.getResults().forEach(result -> {
            result.getCord().forEach((I, A) -> {
                int height = mainSequence.getHeight() - 30;
                int width = mainSequence.getWidth() - 40;

                JButton point = new RoundedButton("");
                point.setName(result.getFile().getPath());
                point.setForeground(Color.WHITE);
                point.setBackground(Color.WHITE);

                if (
                        ProjectInfoStorage.getOpenedFile() != null &&
                        result.getFile().equals(ProjectInfoStorage.getOpenedFile())
                ) {
                    point.setForeground(new Color(98, 189, 105));
                    point.setBackground(new Color(98, 189, 105));
                }
                int pointX = (int) (width * I) + 20;
                int pointY = (int) (height * (1 - A)) + 10;

                point.setBounds(pointX - 2, pointY - 2, 5, 5);
                point.setBorder(null);
                point.setVisible(true);
                point.addMouseListener(new MouseAdapter() {

                    @Override
                    public void mouseClicked(MouseEvent e) {
                        super.mouseClicked(e);
                    }

                    @Override
                    public void mousePressed(MouseEvent e) {
                        super.mousePressed(e);

                        JPopupMenu menu = new JPopupMenu();
                        try {
                            createContextMenu(point);
                        } catch (URISyntaxException ex) {
                            throw new RuntimeException(ex);
                        }

                        menu.add(contextMenu);
                        mainSequence.add(menu);
                        if (e.isPopupTrigger()) {
                            menu.show(contextMenu, pointX + 5, pointY + 5);
                        } else {
                            FileEditorManager.getInstance(ProjectInfoStorage.getProject()).openFile(result.getFile(), true);
                            menu.remove(contextMenu);
                        }
                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {
                        createPerpendiculars(point);
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        deletePerpendiculars();
                    }
                });

                points.add(point);
                mainSequence.add(point);
            });
        });
    }

    private void createContextMenu(JButton point) throws URISyntaxException {
        List<JButton> pointsUnder = points.stream()
                .filter(p -> p.getX() == point.getX() && p.getY() == point.getY())
                .collect(Collectors.toList());

        contextMenu = new JPanel(new GridLayout(pointsUnder.size(), 1));
        contextMenu.setBackground(new Color(50, 53, 55));

        pointsUnder.forEach(p -> {
            JButton btn = new JButton(p.getName().substring(p.getName().lastIndexOf('/') + 1));
            btn.setSize(100, 25);
            btn.setBorderPainted(false);
            btn.setContentAreaFilled(false);
            btn.setOpaque(true);
            btn.setBackground(new Color(50, 53, 55));
            btn.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    super.mouseEntered(e);
                    btn.setBackground(new Color(80, 83, 85));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    super.mouseExited(e);
                    btn.setBackground(new Color(50, 53, 55));
                }

                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);
                    btn.setBackground(new Color(80, 83, 85));
                }

                @Override
                public void mousePressed(MouseEvent e) {
                    super.mousePressed(e);
                    btn.setBackground(new Color(100, 103, 105));
                    Path path = Paths.get(p.getName());
                    VirtualFile fileToOpen = VirtualFileManager.getInstance().findFileByNioPath(path);
                    FileEditorManager.getInstance(ProjectInfoStorage.getProject()).openFile(fileToOpen, true);
                }
            });
            contextMenu.add(btn);
        });
    }

    private void addZoneOfPain() {
        if (zoneOfPain != null) {
            mainSequence.remove(zoneOfPain);
        }

        zoneOfPain = new PartRoundedButton("", PartButtonDirections.TOP_RIGHT);
        zoneOfPain.setBounds(-mainSequence.getWidth() / 2 + 40, mainSequence.getHeight() / 2 - 5, mainSequence.getWidth() - 40, mainSequence.getHeight() - 30);
        zoneOfPain.setBackground(new Color(43, 0, 0, 102));
        zoneOfPain.setForeground(new Color(0, 0, 0, 0));
        zoneOfPain.setEnabled(false);
        zoneOfPain.setBorder(null);
        mainSequence.add(zoneOfPain);
    }

    private void addZoneOfUseless() {
        if (zoneOfUseless != null) {
            mainSequence.remove(zoneOfUseless);
        }

        zoneOfUseless = new PartRoundedButton("", PartButtonDirections.BOTTOM_LEFT);
        zoneOfUseless.setBounds(mainSequence.getWidth() / 2, -mainSequence.getHeight() / 2 + 20, mainSequence.getWidth() - 40, mainSequence.getHeight() - 23);
        zoneOfUseless.setBackground(new Color(111, 124, 133, 102));
        zoneOfUseless.setForeground(new Color(0, 0, 0, 0));
        zoneOfUseless.setEnabled(false);
        zoneOfUseless.setBorder(null);
        mainSequence.add(zoneOfUseless);
    }

    private void createPerpendiculars(JButton point) {
        Graphics2D g2 = (Graphics2D) mainSequence.getGraphics();
        g2.setColor(Color.WHITE);
        g2.drawLine(point.getX() + 2, point.getY() + 2, point.getX() + 2, mainSequence.getHeight() - 20);
        g2.drawLine(point.getX() + 2, point.getY() + 2, 20, point.getY() + 2);
    }

    private void deletePerpendiculars() {
        mainSequence.repaint();
    }

    public JPanel getContent() {
        return mainPanel;
    }

    public void repaintPoints() {
        points.forEach(mainSequence::remove);
        if (zoneOfPain.getParent() != null) {
            mainSequence.remove(zoneOfPain);
            mainSequence.remove(zoneOfUseless);
        }

        createPoints();
        mainSequence.add(zoneOfPain);
        mainSequence.add(zoneOfUseless);
    }
}
