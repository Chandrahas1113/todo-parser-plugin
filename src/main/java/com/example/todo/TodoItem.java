package com.example.todo;

public class TodoItem {
    private final String fileName;
    private final int lineNumber;
    private final String comment;

    public TodoItem(String fileName, int lineNumber, String comment) {
        this.fileName = fileName;
        this.lineNumber = lineNumber;
        this.comment = comment;
    }

    public String getFileName() {
        return fileName;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public String getComment() {
        return comment;
    }
}
