package vietnam.tansenpai.pluggay.util;

import org.bukkit.command.Command;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;

public interface PluginUtil {

    /**
     * Download a plugin from a URL.
     *
     * @param url the URL to download from
     * @return downloaded plugin
     * @throws IOException if an error occurs
     */
    public File download(URL url) throws IOException;

    /**
     * Enable a plugin.
     *
     * @param plugin the plugin to enable
     */
    public void enable(Plugin plugin);

    /**
     * Enable all plugins.
     */
    public void enableAll();

    /**
     * Disable a plugin.
     *
     * @param plugin the plugin to disable
     */
    public void disable(Plugin plugin);

    /**
     * Disable all plugins.
     */
    public void disableAll();

    /**
     * Returns the formatted name of the plugin.
     *
     * @param plugin the plugin to format
     * @return the formatted name
     */
    public String getFormattedName(Plugin plugin);

    /**
     * Returns the formatted name of the plugin.
     *
     * @param plugin          the plugin to format
     * @param includeVersions whether to include the version
     * @return the formatted name
     */
    public String getFormattedName(Plugin plugin, boolean includeVersions);

    /**
     * Returns a plugin from an array of Strings.
     *
     * @param args  the array
     * @param start the index to start at
     * @return the plugin
     */
    public Plugin getPluginByName(String[] args, int start);

    /**
     * Returns a plugin from a String.
     *
     * @param name the name of the plugin
     * @return the plugin
     */
    public Plugin getPluginByName(String name);

    /**
     * Returns a List of plugin names.
     *
     * @return list of plugin names
     */
    public List<String> getPluginNames(boolean fullName);

    /**
     * Returns a List of disabled plugin names.
     *
     * @return list of disabled plugin names
     */
    public List<String> getDisabledPluginNames(boolean fullName);

    /**
     * Returns a List of enabled plugin names.
     *
     * @return list of enabled plugin names
     */
    public List<String> getEnabledPluginNames(boolean fullName);

    /**
     * Get the version of another plugin.
     *
     * @param name the name of the other plugin.
     * @return the version.
     */
    public String getPluginVersion(String name);

    /**
     * Returns the commands a plugin has registered.
     *
     * @param plugin the plugin to deal with
     * @return the commands registered
     */
    public String getUsages(Plugin plugin);

    /**
     * Find which plugin has a given command registered.
     *
     * @param command the command.
     * @return the plugin.
     */
    public List<String> findByCommand(String command);

    /**
     * Checks whether the plugin is ignored.
     *
     * @param plugin the plugin to check
     * @return whether the plugin is ignored
     */
    public boolean isIgnored(Plugin plugin);

    /**
     * Checks whether the plugin is ignored.
     *
     * @param plugin the plugin to check
     * @return whether the plugin is ignored
     */
    public boolean isIgnored(String plugin);

    /**
     * Loads and enables a plugin.
     *
     * @param name plugin's name
     * @return status message
     */
    public String load(String name);

    public Map<String, Command> getKnownCommands();


    /**
     * Reload a plugin.
     *
     * @param plugin the plugin to reload
     */
    public void reload(Plugin plugin);

    /**
     * Reload all plugins.
     */
    public void reloadAll();

    /**
     * Unload a plugin.
     *
     * @param plugin the plugin to unload
     * @return the message to send to the user.
     */
    public String unload(Plugin plugin);

    /**
     * Returns if the plugin is a Paper plugin.
     * @param plugin the plugin to check
     * @return if the plugin is a Paper plugin
     */
    public boolean isPaperPlugin(Plugin plugin);
}
