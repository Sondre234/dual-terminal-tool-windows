package dev.sondre.dualterminal;

import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.terminal.ui.TerminalWidget;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.plugins.terminal.LocalTerminalDirectRunner;
import org.jetbrains.plugins.terminal.ShellStartupOptions;

public final class DualTerminalToolWindowFactory implements com.intellij.openapi.wm.ToolWindowFactory, DumbAware {
    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        // Create the shell directly so it belongs only to this tool window.
        ShellStartupOptions options = new ShellStartupOptions.Builder()
                .workingDirectory(project.getBasePath())
                .build();
        TerminalWidget terminal = LocalTerminalDirectRunner.createTerminalRunner(project)
                .startShellTerminalWidget(toolWindow.getDisposable(), options, true);

        Content content = ContentFactory.getInstance().createContent(terminal.getComponent(), "", false);
        content.setPreferredFocusableComponent(terminal.getPreferredFocusableComponent());
        content.setDisposer(terminal);
        toolWindow.getContentManager().addContent(content);
    }
}
