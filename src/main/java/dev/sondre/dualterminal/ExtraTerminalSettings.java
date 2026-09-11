package dev.sondre.dualterminal;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.Service;
import com.intellij.openapi.components.Storage;
import org.jetbrains.annotations.NotNull;

@Service(Service.Level.APP)
@com.intellij.openapi.components.State(
        name = "DualTerminalWindowsSettings",
        storages = @Storage("dual-terminal-windows.xml")
)
public final class ExtraTerminalSettings implements PersistentStateComponent<ExtraTerminalSettings.State> {
    public static final int MIN_WINDOWS = 1;
    public static final int MAX_WINDOWS = 4;

    public static final class State {
        public int extraWindowCount = 1;
    }

    private State state = new State();

    public static ExtraTerminalSettings getInstance() {
        return ApplicationManager.getApplication().getService(ExtraTerminalSettings.class);
    }

    public int getExtraWindowCount() {
        return Math.clamp(state.extraWindowCount, MIN_WINDOWS, MAX_WINDOWS);
    }

    public void setExtraWindowCount(int count) {
        state.extraWindowCount = Math.clamp(count, MIN_WINDOWS, MAX_WINDOWS);
    }

    @Override
    public @NotNull State getState() {
        return state;
    }

    @Override
    public void loadState(@NotNull State state) {
        this.state = state;
        this.state.extraWindowCount = Math.clamp(state.extraWindowCount, MIN_WINDOWS, MAX_WINDOWS);
    }
}
