package io.zhile.research.intellij.ier.listener;

import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.application.PreloadingActivity;
import com.intellij.openapi.progress.ProgressIndicator;
import org.jetbrains.annotations.NotNull;

public class AppInitActivity extends PreloadingActivity {

    public void preload(@NotNull ProgressIndicator indicator) {
        ApplicationManager.getApplication().executeOnPooledThread(() -> ActionManager.getInstance().getAction("io.zhile.research.intellij.ier.action.ResetAction"));
    }
}
