package vietnam.tansenpai.pluggay.util;

import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import com.google.common.io.Files;
import vietnam.tansenpai.pluggay.PlugGay;
import vietnam.tansenpai.pluggay.pojo.UpdateResult;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.TreeMap;

public class CurseForgeUtil {
    /**
     * The base URL for the CurseForge API.
     */
    public static final String API_BASE_URL = "https://servermods.forgesvc.net/servermods/";

    /**
     * Check which plugins are up-to-date or not.
     *
     * @return a map of the plugins and the results.
     */
    public static Map<String, UpdateResult> checkUpToDate() {
        Map<String, UpdateResult> results = new TreeMap<>();
        for (Plugin plugin : Bukkit.getPluginManager().getPlugins()) {
            results.put(plugin.getName(), checkUpToDate(plugin.getName()));
        }
        return results;
    }

    /**
     * Check if the installed plugin version is up-to-date with the Spigot version.
     *
     * @param pluginName the plugin name.
     * @return the reflective UpdateResult.
     */
    public static UpdateResult checkUpToDate(String pluginName) {
        return checkUpToDate(pluginName, null);
    }

    /**
     * Check if the installed plugin version is up-to-date with the Spigot version.
     *
     * @param pluginName the plugin name.
     * @return the reflective UpdateResult.
     */
    public static UpdateResult checkUpToDate(String pluginName, Long pluginId) {
        boolean idSpecified = pluginId != null;

        if (!idSpecified) {
            pluginId = CurseForgeUtil.getPluginId(pluginName);
        }

        if (pluginId < 0) {
            Plugin plugin = Bukkit.getPluginManager().getPlugin(pluginName);
            if (plugin == null) {
                if (idSpecified) {
                    return new UpdateResult(UpdateResult.ResultType.INVALID_PLUGIN, pluginName);
                } else {
                    return new UpdateResult(UpdateResult.ResultType.NOT_INSTALLED);
                }
            }
            return new UpdateResult(UpdateResult.ResultType.INVALID_PLUGIN, plugin.getDescription().getVersion());
        }

        JSONArray versions = CurseForgeUtil.getPluginVersions(pluginId);

        if (versions == null || versions.size() == 0) {
            Plugin plugin = Bukkit.getPluginManager().getPlugin(pluginName);
            if (plugin == null) {
                if (idSpecified) {
                    return new UpdateResult(UpdateResult.ResultType.INVALID_PLUGIN, pluginName);
                } else {
                    return new UpdateResult(UpdateResult.ResultType.NOT_INSTALLED);
                }
            }
            return new UpdateResult(UpdateResult.ResultType.INVALID_PLUGIN, plugin.getDescription().getVersion());
        }

        JSONObject latest = (JSONObject) versions.get(versions.size() - 1);

        String currentVersion = PlugGay.getInstance().getPluginUtil().getPluginVersion(pluginName);
        if (!(Bukkit.getPluginManager().getPlugin(pluginName) instanceof JavaPlugin)) {
            if (idSpecified) {
                return new UpdateResult(UpdateResult.ResultType.INVALID_PLUGIN, currentVersion);
            } else {
                return new UpdateResult(UpdateResult.ResultType.INVALID_PLUGIN, currentVersion, "null");
            }
        }
        JavaPlugin plugin = (JavaPlugin) Bukkit.getPluginManager().getPlugin(pluginName);
        if (plugin == null) {
            if (idSpecified) {
                return new UpdateResult(UpdateResult.ResultType.NOT_INSTALLED, currentVersion);
            } else {
                return new UpdateResult(UpdateResult.ResultType.NOT_INSTALLED, currentVersion, "null");
            }
        }
        String latestVersion = (String) latest.get("md5");
        HashCode currentPluginHashCode;

        try {
            Method getFileMethod = JavaPlugin.class.getDeclaredMethod("getFile");
            getFileMethod.setAccessible(true);
            File file = (File) getFileMethod.invoke(plugin);
            currentPluginHashCode = Files.asByteSource(file).hash(Hashing.md5());
            currentPluginHashCode.toString();
        } catch (IOException | InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
            e.printStackTrace();
            return new UpdateResult(UpdateResult.ResultType.INVALID_PLUGIN, currentVersion, latestVersion);
        }

        if (latestVersion == null) {
            return new UpdateResult(UpdateResult.ResultType.INVALID_PLUGIN, currentVersion, latestVersion);
        }

        latestVersion = (String) latest.get("name");

        if (currentVersion == null) {
            return new UpdateResult(UpdateResult.ResultType.NOT_INSTALLED, currentVersion, latestVersion);
        } else if (latestVersion == null) {
            return new UpdateResult(UpdateResult.ResultType.INVALID_PLUGIN, currentVersion, latestVersion);
        }

        Boolean isActual = UpdateUtil.isActualVersion(currentVersion, latestVersion);
        if (isActual != null && isActual) {
            return new UpdateResult(UpdateResult.ResultType.UP_TO_DATE, currentVersion, latestVersion);
        } else {
            return new UpdateResult(UpdateResult.ResultType.OUT_OF_DATE, currentVersion, latestVersion);
        }

    }

    /**
     * Get the id of the plugin.
     *
     * @param name the name of the plugin.
     * @return the id of the plugin.
     */
    public static long getPluginId(String name) {

        HttpClient client = HttpClients.createMinimal();

        HttpGet get = new HttpGet(API_BASE_URL + "projects?search=" + name.toLowerCase());
        get.setHeader("User-Agent", "PlugGay");

        try {

            HttpResponse response = client.execute(get);
            String body = IOUtils.toString(response.getEntity().getContent());

            Object object = JSONValue.parse(body);

            if (object instanceof JSONArray) {

                JSONArray array = (JSONArray) JSONValue.parse(body);

                for (int i = 0; i < array.size(); i++) {
                    JSONObject json = (JSONObject) array.get(i);
                    String pluginName = (String) json.get("slug");
                    if (name.equalsIgnoreCase(pluginName)) {
                        return (long) json.get("id");
                    }
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return -1;

    }

    /**
     * Get the versions for a given plugin.
     *
     * @param id the plugin id.
     * @return the JSON encoded data.
     */
    public static JSONArray getPluginVersions(long id) {

        HttpClient client = HttpClients.createMinimal();

        HttpGet get = new HttpGet(API_BASE_URL + "files?projectIds=" + id);
        get.setHeader("User-Agent", "PlugMan");

        try {

            HttpResponse response = client.execute(get);
            String body = IOUtils.toString(response.getEntity().getContent());

            return (JSONArray) JSONValue.parse(body);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }

}
