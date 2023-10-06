package vietnam.tansenpai.pluggay.api;

import vietnam.tansenpai.pluggay.PlugGay;

import java.util.HashMap;

public class PlugManAPI {
    protected static final HashMap<Object, GentleUnload> gentleUnloads = new HashMap<>();
    private static Class pluginClass = null;

    /**
     * @return = Returns all plugins which should be unloaded gently
     */
    public static HashMap<Object, GentleUnload> getGentleUnloads() {
        return new HashMap<>(PlugManAPI.gentleUnloads);
    }

    /**
     * A gentle unload is when a plugin wants to unload itself, so PlugMan doesn't break stuff
     *
     * @param plugin       = Plugin which wants a gentle unload
     * @param gentleUnload = The class which will handle the gentle unload
     * @return = If the plugin is allowed to be unloaded gently
     */
    public static boolean pleaseAddMeToGentleUnload(Object plugin, GentleUnload gentleUnload) {
        if (plugin == null)
            return false;

        if (gentleUnload == null)
            return false;

        if (PlugManAPI.pluginClass == null) try {
            PlugManAPI.pluginClass = Class.forName("org.bukkit.plugin.Plugin");
        } catch (ClassNotFoundException e) {
            try {
                PlugManAPI.pluginClass = Class.forName("net.md_5.bungee.api.plugin.Plugin");
            } catch (ClassNotFoundException ex) {
                ex.addSuppressed(e);
                ex.printStackTrace();
                return false;
            }
        }

        if (PlugManAPI.gentleUnloads.containsKey(plugin))
            return false;

        if (!PlugManAPI.pluginClass.isInstance(plugin))
            return false;

        PlugManAPI.gentleUnloads.put(plugin, gentleUnload);
        return true;
    }

    /**
     * If you don't want your plugin to be un-/reloaded, you can use this method to add it to the list of ignored plugins
     *
     * @param plugin = The plugin that should be ignored
     * @return = Whether the method was executed successfully
     */
    public static boolean iDoNotWantToBeUnOrReloaded(String plugin) {
        if (PlugGay.getInstance() == null)
            return false;

        if (PlugGay.getInstance().getIgnoredPlugins() == null)
            return false;

        PlugGay.getInstance().getIgnoredPlugins().add(plugin);
        return true;
    }
}
