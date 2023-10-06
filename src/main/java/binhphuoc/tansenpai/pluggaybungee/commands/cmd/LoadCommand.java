package binhphuoc.tansenpai.pluggaybungee.commands.cmd;

import binhphuoc.tansenpai.pluggaybungee.util.BungeePluginUtil;
import binhphuoc.tansenpai.pluggaybungee.util.PluginResult;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class LoadCommand {

    public void execute(CommandSender sender, String[] args) {
        if (args.length <= 0) {
            this.sendMessage(sender, "§cCú pháp: §4/PlugGayBungee unload <File>");
            return;
        }

        String filename = args[0].replaceAll("[/\\\\]", "");

        File file = new File("plugins", filename + ".jar");

        if (!file.exists()) {
            this.sendMessage(sender, "§cKhông có tập tin plugin có tên §4" + filename + "§c!");
            return;
        }

        PluginResult pluginResult = BungeePluginUtil.loadPlugin(file);

        this.sendMessage(sender, pluginResult.getMessage());
    }

    private void sendMessage(CommandSender sender, String message) {
        sender.sendMessage(new TextComponent("§8[§2PlugGayBungee§8] §7" + message));
    }

    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        if (args.length == 1) {
            List<String> completions = new ArrayList<>();
            //Yaml yaml = new Yaml();
            for (File file : new File("plugins").listFiles())
                if (file.isFile()) if (file.getName().toLowerCase(Locale.ROOT).endsWith(".jar"))
                    try (JarFile jar = new JarFile(file)) {
                        JarEntry pdf = jar.getJarEntry("bungee.yml");
                        if (pdf == null) pdf = jar.getJarEntry("plugin.yml");

                        if (pdf == null)
                            continue;

                        try (InputStream in = jar.getInputStream(pdf)) {
                            //PluginDescription desc = yaml.loadAs(in, PluginDescription.class);
                            Configuration cfg = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new InputStreamReader(in));

                            if (cfg.get("name", null) == null)
                                continue;

                            if (cfg.get("main", null) == null)
                                continue;

                            if (ProxyServer.getInstance().getPluginManager().getPlugin(cfg.getString("name", null)) != null)
                                continue;

                            completions.add(file.getName().substring(0, file.getName().length() - 4));
                        }
                    } catch (IOException ignored) {
                    }

            List<String> realCompletions = new ArrayList<>();

            for (String com : completions)
                if (com.toLowerCase(Locale.ROOT).startsWith(args[0].toLowerCase(Locale.ROOT))) realCompletions.add(com);

            return realCompletions.size() > 0 ? realCompletions : completions;
        }
        return new ArrayList<>();
    }
}
