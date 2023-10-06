package binhphuoc.tansenpai.pluggaybungee.commands.cmd;

import binhphuoc.tansenpai.pluggaybungee.util.BungeePluginUtil;
import binhphuoc.tansenpai.pluggaybungee.util.PluginResult;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.PluginManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

public class ReloadCommand {

    public void execute(CommandSender sender, String[] args) {
        if (args.length <= 0) {
            sendMessage(sender, "§cCú pháp: §4/PlugGayBungee reload <Plugin>");
            return;
        }

        String pluginName = args[0];

        PluginManager pluginManager = ProxyServer.getInstance().getPluginManager();

        if (pluginManager.getPlugin(pluginName) == null) {
            sendMessage(sender, "§cKhông có plugin nào có tên §4" + pluginName + "§c!");
            return;
        }

        Map.Entry<PluginResult, PluginResult> pluginResults = BungeePluginUtil.reloadPlugin(pluginManager.getPlugin(pluginName));
        sendMessage(sender, pluginResults.getKey().getMessage());
        sendMessage(sender, pluginResults.getValue().getMessage());
    }

    private void sendMessage(CommandSender sender, String message) {
        sender.sendMessage(new TextComponent("§8[§2PlugGayBungee§8] §7" + message));
    }

    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        if (args.length == 1) {
            List<String> completions = ProxyServer.getInstance().getPluginManager().getPlugins().stream().map(plugin -> plugin.getDescription().getName()).collect(Collectors.toList());

            List<String> realCompletions = new ArrayList<>();

            for (String com : completions) {
                if (com.toLowerCase(Locale.ROOT).startsWith(args[0].toLowerCase(Locale.ROOT))) {
                    realCompletions.add(com);
                }
            }

            return realCompletions.size() > 0 ? realCompletions : completions;
        }
        return new ArrayList<>();
    }
}
