package binhphuoc.tansenpai.pluggaybungee.main;

import binhphuoc.tansenpai.pluggaybungee.commands.PlugGayBungeeCommand;
import binhphuoc.tansenpai.pluggaybungee.commands.PluginsCommand;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;

public final class PlugGayBungee extends Plugin implements Listener {
    private static PlugGayBungee instance;

    @Override
    public void onEnable() {
        instance = this;

        ProxyServer.getInstance().getPluginManager().registerCommand(this, new PluginsCommand());
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new PlugGayBungeeCommand());
    }

    @Override
    public void onDisable() {
    }

    public static PlugGayBungee getInstance() {
        return instance;
    }
}
