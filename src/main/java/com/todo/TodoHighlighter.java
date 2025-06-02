package com.todo;

import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.EditorFactory;
import com.intellij.openapi.editor.event.DocumentEvent;
import com.intellij.openapi.editor.event.DocumentListener;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.fileEditor.FileEditorManagerEvent;
import com.intellij.openapi.fileEditor.FileEditorManagerListener;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class TodoHighlighter implements FileEditorManagerListener {

    private static Project currentProject;
    private static VirtualFile currentFile;

    @Override
    public void selectionChanged(@NotNull FileEditorManagerEvent event) {
        currentProject = event.getManager().getProject();
        currentFile = event.getNewFile();

        if (currentFile != null && currentFile.getName().endsWith(".java")) {
            updateTodoListForFile(currentFile);
        } else {
            TodoPanel.updateTodos(new ArrayList<>()); // Clear TODO list
        }
    }

    public static void registerListeners(Project project) {
        EditorFactory.getInstance().getEventMulticaster().addDocumentListener(new DocumentListener() {
            @Override
            public void documentChanged(@NotNull DocumentEvent event) {
                if (currentFile == null || currentProject == null) return;

                Document doc = event.getDocument();
                VirtualFile changedFile = FileDocumentManager.getInstance().getFile(doc);
                if (changedFile != null && changedFile.equals(currentFile)) {
                    updateTodoListForFile(currentFile);
                }
            }
        }, project);

        project.getMessageBus().connect().subscribe(FileEditorManagerListener.FILE_EDITOR_MANAGER, new TodoHighlighter());
    }

    private static void updateTodoListForFile(VirtualFile file) {
        Document document = FileDocumentManager.getInstance().getDocument(file);
        if (document == null) return;

        String[] lines = document.getText().split("\\n");
        List<TodoItem> todos = new ArrayList<>();

        for (int i = 0; i < lines.length; i++) {
            if (lines[i].contains("// TODO") || lines[i].contains("//TODO") ) {
                todos.add(new TodoItem(file.getName(), i + 1, lines[i].trim()));
            }
        }
        TodoPanel.updateTodos(todos);
    }
}
