
package com.example.todo;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import org.jetbrains.annotations.NotNull;

public class TodoToolWindowFactory implements ToolWindowFactory {
    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        Content content = ContentFactory.getInstance().createContent(new TodoPanel(project), "", false);
        toolWindow.getContentManager().addContent(content);
        TodoPanel.updateTodos(TodoService.extractTodos(project));
        // Register runtime update listeners
        TodoHighlighter.registerListeners(project);
    }

}