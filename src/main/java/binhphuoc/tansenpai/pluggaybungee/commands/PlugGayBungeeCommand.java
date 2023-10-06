package binhphuoc.tansenpai.pluggaybungee.commands;

import binhphuoc.tansenpai.pluggaybungee.commands.cmd.LoadCommand;
import binhphuoc.tansenpai.pluggaybungee.commands.cmd.ReloadCommand;
import binhphuoc.tansenpai.pluggaybungee.commands.cmd.UnloadCommand;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;

import java.util.*;

public class PlugGayBungeeCommand extends Command implements TabExecutor {
    private LoadCommand loadCommand;
    private UnloadCommand unloadCommand;
    private ReloadCommand reloadCommand;

    public PlugGayBungeeCommand() {
        super("pluggaybungee", "pluggay.use");
        loadCommand = new LoadCommand();
        unloadCommand = new UnloadCommand();
        reloadCommand = new ReloadCommand();
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length <= 0) {
            sendMessage(sender, "Syntax: §2/pluggay <load|unload|reload>");
            return;
        }

        if (args[0].equalsIgnoreCase("load")) {
            if (!sender.hasPermission("pluggay.load")) {
                sendMessage(sender, "§cBạn không có đủ quyền để thực thi lệnh này!!");
                return;
            }
            String[] newArgs = new String[args.length - 1];
            for (int i = 0, i1 = 1; i1 < args.length; i1++) {
                newArgs[i] = args[i1];
            }
            loadCommand.execute(sender, newArgs);
            return;
        }

        if (args[0].equalsIgnoreCase("unload")) {
            if (!sender.hasPermission("pluggay.unload")) {
                sendMessage(sender, "§cBạn không có đủ quyền để thực thi lệnh này!!");
                return;
            }
            String[] newArgs = new String[args.length - 1];
            for (int i = 0, i1 = 1; i1 < args.length; i1++) {
                newArgs[i] = args[i1];
            }
            unloadCommand.execute(sender, newArgs);
            return;
        }

        if (args[0].equalsIgnoreCase("reload")) {
            if (!sender.hasPermission("pluggay.reload")) {
                sendMessage(sender, "§cBạn không có đủ quyền để thực thi lệnh này!!");
                return;
            }
            String[] newArgs = new String[args.length - 1];
            for (int i = 0, i1 = 1; i1 < args.length; i1++) {
                newArgs[i] = args[i1];
            }
            reloadCommand.execute(sender, newArgs);
            return;
        }

        sendMessage(sender, "Syntax: §2/pluggay <load|unload|reload>");
    }

    private void sendMessage(CommandSender sender, String message) {
        sender.sendMessage(new TextComponent("§8[§2PlugGayBungee§8] §7" + message));
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        if (args.length == 1) {
            List<String> completion = new ArrayList<>(Arrays.asList("load", "unload", "reload"));
            for (String com : completion) {
                if (com.startsWith(args[0].toLowerCase(Locale.ROOT))) {
                    return Collections.singletonList(com);
                }
            }
            return completion;
        }

        if (args.length == 2) {
            if (args[0].equalsIgnoreCase("unload")) {
                if (!sender.hasPermission("pluggay.unload")) {
                    return new ArrayList<>();
                }

                String[] newArgs = new String[args.length - 1];
                for (int i = 0, i1 = 1; i1 < args.length; i1++) {
                    newArgs[i] = args[i1];
                }
                return unloadCommand.onTabComplete(sender, newArgs);
            }

            if (args[0].equalsIgnoreCase("load")) {
                if (!sender.hasPermission("pluggay.load")) {
                    return new ArrayList<>();
                }

                String[] newArgs = new String[args.length - 1];
                for (int i = 0, i1 = 1; i1 < args.length; i1++) {
                    newArgs[i] = args[i1];
                }
                return loadCommand.onTabComplete(sender, newArgs);
            }

            if (args[0].equalsIgnoreCase("reload")) {
                if (!sender.hasPermission("pluggay.reload")) {
                    return new ArrayList<>();
                }

                String[] newArgs = new String[args.length - 1];
                for (int i = 0, i1 = 1; i1 < args.length; i1++) {
                    newArgs[i] = args[i1];
                }
                return loadCommand.onTabComplete(sender, newArgs);
            }
        }

        return new ArrayList<>();
    }
}
