package vietnam.tansenpai.pluggay.util;

import vietnam.tansenpai.pluggay.PlugGay;
import vietnam.tansenpai.pluggay.pojo.UpdateResult;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.json.simple.JSONArray;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UpdateUtil {
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
        if (PlugGay.getInstance().getResourceMap().containsKey(pluginName.toLowerCase(Locale.ROOT))) {
            Map.Entry<Long, Boolean> entry = PlugGay.getInstance().getResourceMap().get(pluginName.toLowerCase(Locale.ROOT));

            if (entry.getValue()) {
                return SpiGetUtil.checkUpToDate(pluginName, entry.getKey());
            } else {
                return CurseForgeUtil.checkUpToDate(pluginName, entry.getKey());
            }
        }

        long id = SpiGetUtil.getPluginId(pluginName);
        if (id < 0) {
            id = CurseForgeUtil.getPluginId(pluginName);
            if (id < 0) {
                Plugin plugin = Bukkit.getPluginManager().getPlugin(pluginName);
                if (plugin == null) {
                    return new UpdateResult(UpdateResult.ResultType.INVALID_PLUGIN, pluginName);
                }
                return new UpdateResult(UpdateResult.ResultType.INVALID_PLUGIN, plugin.getDescription().getVersion());
            }
            return CurseForgeUtil.checkUpToDate(pluginName);
        }
        return SpiGetUtil.checkUpToDate(pluginName);
    }

    /**
     * Get the id of the plugin.
     *
     * @param name the name of the plugin.
     * @return the id of the plugin.
     */
    public static long getPluginId(String name) {
        if (PlugGay.getInstance().getResourceMap().containsKey(name.toLowerCase(Locale.ROOT))) {
            Map.Entry<Long, Boolean> entry = PlugGay.getInstance().getResourceMap().get(name.toLowerCase(Locale.ROOT));

            if (entry.getValue()) {
                return SpiGetUtil.getPluginId(name);
            } else {
                return CurseForgeUtil.getPluginId(name);
            }
        }

        long id = SpiGetUtil.getPluginId(name);
        if (id < 0) {
            id = CurseForgeUtil.getPluginId(name);
        }
        return id;

    }

    /**
     * Get the versions for a given plugin.
     *
     * @param id the plugin id.
     * @return the JSON encoded data.
     */
    public static JSONArray getPluginVersions(long id) {
        for (Map.Entry<Long, Boolean> entry : PlugGay.getInstance().getResourceMap().values()) {
            if (entry.getKey() == id) {
                if (entry.getValue()) {
                    return SpiGetUtil.getPluginVersions(id);
                } else {
                    return CurseForgeUtil.getPluginVersions(id);
                }
            }
        }

        JSONArray jsonArray = SpiGetUtil.getPluginVersions(id);
        if (jsonArray == null || jsonArray.size() <= 0) {
            return CurseForgeUtil.getPluginVersions(id);
        }
        return jsonArray;
    }

    private static final Pattern VERSION_FAMILY_NUMBERS_PATTERN = Pattern.compile("\\d+(\\.\\d+)*");

    protected static Boolean isActualVersion(String current, String latest) {
        if (current.equalsIgnoreCase(latest)) return true; // Strings are fully equals

        List<List<Integer>> currentNumbers;
        List<List<Integer>> latestNumbers;

        try {
            currentNumbers = parseNumbers(VERSION_FAMILY_NUMBERS_PATTERN.matcher(current));
            latestNumbers = parseNumbers(VERSION_FAMILY_NUMBERS_PATTERN.matcher(latest));
        } catch (NumberFormatException ex) {
            return null; // Unable to parse numbers to int
        }

        for (int familyIndex = 0; familyIndex < CollectionUtil.maxCollectionsSize(currentNumbers, latestNumbers); familyIndex++) {
            List<Integer> currentFamily = CollectionUtil.getElementOrDefault(currentNumbers, familyIndex, ArrayList::new);
            List<Integer> latestFamily = CollectionUtil.getElementOrDefault(latestNumbers, familyIndex, ArrayList::new);

            for (int numberIndex = 0; numberIndex < CollectionUtil.maxCollectionsSize(currentFamily, latestFamily); numberIndex++) {
                int currentValue = CollectionUtil.getElementOrDefault(currentFamily, numberIndex, () -> 0);
                int latestValue = CollectionUtil.getElementOrDefault(latestFamily, numberIndex, () -> 0);

                if (latestValue > currentValue) {
                    return false;
                } else if (latestValue < currentValue) {
                    return true;
                }
            }
        }
        return true; // Numbers amount equals, numbers values too
    }

    private static List<List<Integer>> parseNumbers(Matcher matcher) {
        List<List<Integer>> result = new ArrayList<>();
        while (matcher.find()) {
            String familyString = matcher.group();
            List<Integer> family = new ArrayList<>();
            for (String number : familyString.split("\\.")) {
                family.add(Integer.parseInt(number));
            }
            result.add(family);
        }
        return result;
    }
}
