package binhphuoc.tansenpai.pluggaybungee.commands;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.Plugin;

import java.util.Collection;
import java.util.stream.Collectors;

public class PluginsCommand extends Command {

    public PluginsCommand() {
        super("bungeeplugins", "pluggay.seeplugins", "bpl");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Collection<Plugin> plugins = ProxyServer.getInstance().getPluginManager().getPlugins();
        String message = plugins.stream().map(plugin -> ChatColor.RESET + "," + " " + (plugin.getDescription() == null ? ChatColor.RED : ChatColor.GREEN) + plugin.getDescription().getName()).collect(Collectors.joining("", "Plugins (" + plugins.size() + "):", ""));
        sender.sendMessage(new TextComponent(message.replace(":" + ChatColor.RESET + ",", ":")));
    }
}
