package dev.sondre.dualterminal;

import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.startup.StartupActivity;
import com.intellij.openapi.util.IconLoader;
import com.intellij.openapi.wm.RegisterToolWindowTask;
import com.intellij.openapi.wm.ToolWindowAnchor;
import com.intellij.openapi.wm.ToolWindowManager;
import org.jetbrains.annotations.NotNull;

import javax.swing.Icon;

public final class ExtraTerminalRegistrar implements StartupActivity, DumbAware {
    private static final String ID_PREFIX = "Extra Terminal ";
    private static final Icon ICON = IconLoader.getIcon("/icons/dualTerminal.svg", ExtraTerminalRegistrar.class);

    @Override
    public void runActivity(@NotNull Project project) {
        sync(project);
    }

    public static void sync(@NotNull Project project) {
        ToolWindowManager manager = ToolWindowManager.getInstance(project);
        manager.invokeLater(() -> syncNow(project, manager));
    }

    private static void syncNow(@NotNull Project project, @NotNull ToolWindowManager manager) {
        if (project.isDisposed()) {
            return;
        }

        int desiredCount = ExtraTerminalSettings.getInstance().getExtraWindowCount();
        for (int index = 1; index <= ExtraTerminalSettings.MAX_WINDOWS; index++) {
            String id = ID_PREFIX + index;
            boolean registered = manager.getToolWindow(id) != null;

            if (index <= desiredCount && !registered) {
                ToolWindowAnchor anchor = index % 2 == 1 ? ToolWindowAnchor.RIGHT : ToolWindowAnchor.BOTTOM;
                manager.registerToolWindow(RegisterToolWindowTask.lazyAndClosable(
                        id,
                        new DualTerminalToolWindowFactory(),
                        ICON,
                        anchor
                ));
            } else if (index > desiredCount && registered) {
                manager.unregisterToolWindow(id);
            }
        }
    }
}
