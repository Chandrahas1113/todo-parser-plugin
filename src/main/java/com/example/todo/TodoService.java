package com.example.todo;

import com.intellij.openapi.editor.Document;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ProjectFileIndex;
import com.intellij.openapi.roots.ProjectRootManager;

import java.util.ArrayList;
import java.util.List;

public class TodoService {

    public static List<TodoItem> extractTodos(Project project) {
        List<TodoItem> todos = new ArrayList<>();
        FileDocumentManager fileDocumentManager = FileDocumentManager.getInstance();

        ProjectFileIndex fileIndex = ProjectRootManager.getInstance(project).getFileIndex();
        fileIndex.iterateContent(file -> {
            if (!file.isDirectory() && file.getName().endsWith(".java")) {
                Document document = fileDocumentManager.getDocument(file);
                if (document != null) {
                    String[] lines = document.getText().split("\\n");
                    for (int i = 0; i < lines.length; i++) {
                        if (lines[i].contains("// TODO")) {
                            todos.add(new TodoItem(file.getName(), i + 1, lines[i].trim()));
                        }
                    }
                }
            }
            return true;
        });

        return todos;
    }
}