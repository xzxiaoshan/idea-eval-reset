package io.zhile.research.intellij.ier.listener;


import com.intellij.ide.plugins.IdeaPluginDescriptor;
import com.intellij.ide.plugins.PluginInstaller;
import com.intellij.ide.plugins.PluginStateListener;
import com.intellij.openapi.application.ApplicationManager;
import io.zhile.research.intellij.ier.common.Resetter;
import io.zhile.research.intellij.ier.helper.ReflectionHelper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.jetbrains.annotations.NotNull;

public class PluginInstallListener implements PluginStateListener {
    private static final PluginStateListener stateListener = new PluginInstallListener();

    private static void reflectionCall(String methodName) throws InvocationTargetException, IllegalAccessException {
        Method[] methods = {
                ReflectionHelper.getMethod(PluginInstaller.class, methodName, PluginStateListener.class),
                ReflectionHelper.getMethod("com.intellij.ide.plugins.PluginStateManager", methodName, PluginStateListener.class)
        };
        for (Method method : methods) {
            if (null != method) {
                method.invoke(null, stateListener);
                return;
            }
        }
    }

    public static void setup() throws InvocationTargetException, IllegalAccessException {
        reflectionCall("addStateListener");
    }

    public static void remove() throws InvocationTargetException, IllegalAccessException {
        reflectionCall("removeStateListener");
    }

    @Override
    public void install(@NotNull IdeaPluginDescriptor descriptor) {
        ApplicationManager.getApplication().invokeLater(() -> Resetter.addPluginLicense(descriptor));
    }

    @Override
    public void uninstall(@NotNull IdeaPluginDescriptor descriptor) {
        // no impl
    }
}
