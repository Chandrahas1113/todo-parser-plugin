package com.example.todo;

import com.intellij.openapi.fileEditor.OpenFileDescriptor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.search.FilenameIndex;
import com.intellij.psi.search.GlobalSearchScope;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collection;
import java.util.List;

public class TodoPanel extends JPanel {

    private static DefaultTableModel tableModel = new DefaultTableModel(new String[]{"File", "Line", "Comment"}, 0);
    private static JTable table;
    private static Project project;

    public TodoPanel(Project projectRef) {
        project = projectRef;
        setLayout(new BorderLayout());
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Add click listener to open file at line
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.rowAtPoint(e.getPoint());
                if (row >= 0) {
                    String fileName = (String) tableModel.getValueAt(row, 0);
                    int line = (int) tableModel.getValueAt(row, 1);

                    // Find file by name in current project
                    VirtualFile file = findFileByName(fileName);
                    if (file != null) {
                        new OpenFileDescriptor(project, file, line - 1, 0).navigate(true);
                    }
                }
            }
        });
    }

    public static void updateTodos(List<TodoItem> todos) {
        if (tableModel == null) return;
        SwingUtilities.invokeLater(() -> {
            tableModel.setRowCount(0);
            for (TodoItem todo : todos) {
                tableModel.addRow(new Object[]{todo.getFileName(), todo.getLineNumber(), todo.getComment()});
            }
        });
    }

    private VirtualFile findFileByName(String fileName) {
        Collection<VirtualFile> files = FilenameIndex.getVirtualFilesByName(
                project,
                fileName,
                GlobalSearchScope.projectScope(project)
        );

        for (VirtualFile file : files) {
            if (file.getName().equals(fileName)) {
                return file;
            }
        }

        return null;
    }
}
