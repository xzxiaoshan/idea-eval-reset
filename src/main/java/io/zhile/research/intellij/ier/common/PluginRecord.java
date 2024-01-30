package io.zhile.research.intellij.ier.common;

import com.intellij.ide.plugins.IdeaPluginDescriptor;
import com.intellij.ide.plugins.PluginManager;

import java.util.List;

public abstract class PluginRecord implements EvalRecord {
    public abstract String getName();

    public void test(List<EvalRecord> list) {
        for (IdeaPluginDescriptor descriptor : PluginManager.getPlugins()) {
            if (descriptor.getName().equals(getName())) {
                list.add(this);
                return;
            }
        }
    }

    public String toString() {
        return "PLUGIN: " + getName();
    }
}
