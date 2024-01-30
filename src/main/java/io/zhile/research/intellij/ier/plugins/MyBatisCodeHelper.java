package io.zhile.research.intellij.ier.plugins;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.PersistentStateComponent;
import io.zhile.research.intellij.ier.common.PluginRecord;
import io.zhile.research.intellij.ier.helper.ReflectionHelper;
import java.lang.reflect.Method;
public final class MyBatisCodeHelper extends PluginRecord {
    private static final String PLUGIN_NAME = "MyBatisCodeHelperPro (Marketplace Edition)";

    public void reset() throws Exception {
//        Object state;
//        Method method;
//        PersistentStateComponent component = ApplicationManager.getApplication().getComponent("MyBatisCodeHelper");
//        if (null == component || null == (state = component.getState())
//                || null == (method = ReflectionHelper.getMethod(state.getClass(), "getProfile"))) {
//            return;
//        }
//        Object profile = method.invoke(state);
//        Method method2 = ReflectionHelper.getMethod(profile.getClass(), "setValid", Boolean.TYPE);
//        if (null != method2) {
//            method2.invoke(profile, true);
//        }
//        Method method3 = ReflectionHelper.getMethod(profile.getClass(), "setTheUsageCount", String.class);
//        if (null != method3) {
//            method3.invoke(profile, "-1");
//        }
    }

    public String getName() {
        return PLUGIN_NAME;
    }
}
