# TODO Finder Plugin for IntelliJ IDEA

This plugin scans `.java` files in your IntelliJ project for `// TODO` comments, lists them in a dedicated panel, and lets you click to jump to each one directly. It’s designed for developers who want a quick way to track outstanding TODOs in their codebase.

---

##  Features

-  Finds all `// TODO` or `//TODO` comments in `.java` files across your project
-  Displays TODOs in a side panel (Tool Window)
-  Clicking a TODO navigates directly to the file and line
-  Updates when files are opened or switched
-  Works without AI, using plain IntelliJ SDK APIs


## How to Run the Plugin

1. Open the project in IntelliJ IDEA (Community or Ultimate).
2. Ensure the following is set in `build.gradle`:

```groovy
plugins {
    id 'java'
    id 'org.jetbrains.intellij' version '1.15.0'
}

intellij {
    version = '2023.2.2'
    type = 'IC'
}

patchPluginXml {
    changeNotes = "Initial release of TODO Finder Plugin."
}
```

3. Open the **Gradle** panel → Tasks → `intellij` → Run `runIde`.
4. A new IntelliJ instance will open with the plugin installed.
5. Open a Java file with `// TODO` comments to see the panel populate.

---

##  plugin.xml Configuration

Inside `resources/META-INF/plugin.xml`:

```xml
<idea-plugin>
    <id>com.todo</id>
    <name>TODOs</name>
    <version>1.0</version>
    <vendor email="23pa1a5708@vishnu.edu.in">Garikimukku Chandrahas</vendor>

    <depends>com.intellij.modules.platform</depends>
    <depends>com.intellij.modules.java</depends>

    <extensions defaultExtensionNs="com.intellij">
        <toolWindow
                id="TODOs Plugin"
                anchor="right"
                factoryClass="com.todo.TodoToolWindowFactory"/>
        <projectService serviceImplementation="com.todo.TodoHighlighter"/>
    </extensions>
</idea-plugin>

```

---

## Requirements

- IntelliJ IDEA 2023.2.2 or later
- Java 17 (Ensure your IntelliJ project SDK and Gradle JDK match)
- Gradle 8+

---

## Notes

- TODOs are picked up using simple text parsing (`// TODO`).
- Works on `.java` files only.
- If the panel doesn’t refresh automatically, try reopening the file or switching tabs.

---

##  Testing Checklist

- [x] Plugin launches using `runIde`
- [x] TODO panel appears in sidebar
- [x] TODOs show on opening `.java` files
- [x] Click on a TODO navigates to correct line
- [x] Opening/closing tabs updates TODO list
- [x] All classes compile without errors

---

## Support

If you run into issues, feel free to:
- Raise a GitHub issue (if published on GitHub)
- Contact the plugin author via the email in `plugin.xml`