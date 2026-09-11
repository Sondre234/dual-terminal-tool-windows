package dev.sondre.dualterminal;

import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import java.awt.Component;

public final class ExtraTerminalSettingsConfigurable implements Configurable {
    private JSpinner countSpinner;

    @Override
    public @Nls String getDisplayName() {
        return "Dual Terminal Windows";
    }

    @Override
    public @Nullable JPanel createComponent() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel label = new JLabel("Additional terminal windows:");
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(label);

        countSpinner = new JSpinner(new SpinnerNumberModel(
                ExtraTerminalSettings.getInstance().getExtraWindowCount(),
                ExtraTerminalSettings.MIN_WINDOWS,
                ExtraTerminalSettings.MAX_WINDOWS,
                1
        ));
        countSpinner.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(countSpinner);

        JLabel note = new JLabel("Existing sessions above the new count will be closed when you apply.");
        note.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(note);
        return panel;
    }

    @Override
    public boolean isModified() {
        return countSpinner != null
                && ((Number) countSpinner.getValue()).intValue()
                != ExtraTerminalSettings.getInstance().getExtraWindowCount();
    }

    @Override
    public void apply() {
        if (countSpinner == null) {
            return;
        }

        ExtraTerminalSettings.getInstance().setExtraWindowCount(
                ((Number) countSpinner.getValue()).intValue()
        );
        for (Project project : ProjectManager.getInstance().getOpenProjects()) {
            ExtraTerminalRegistrar.sync(project);
        }
    }

    @Override
    public void reset() {
        if (countSpinner != null) {
            countSpinner.setValue(ExtraTerminalSettings.getInstance().getExtraWindowCount());
        }
    }

    @Override
    public void disposeUIResources() {
        countSpinner = null;
    }
}
