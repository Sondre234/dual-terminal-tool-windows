package dev.sondre.dualterminal;

import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.plugins.terminal.ShellTerminalWidget;
import org.jetbrains.plugins.terminal.TerminalToolWindowManager;

public final class DualTerminalToolWindowFactory implements com.intellij.openapi.wm.ToolWindowFactory, DumbAware {
    @Override
    @SuppressWarnings({"deprecation", "removal"})
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        String workingDirectory = project.getBasePath();
        ShellTerminalWidget terminal = TerminalToolWindowManager.getInstance(project)
                .createLocalShellWidget(workingDirectory, toolWindow.getId(), false, true);

        Content content = ContentFactory.getInstance().createContent(terminal, "", false);
        content.setPreferredFocusableComponent(terminal);
        content.setDisposer(terminal);
        toolWindow.getContentManager().addContent(content);
    }
}
