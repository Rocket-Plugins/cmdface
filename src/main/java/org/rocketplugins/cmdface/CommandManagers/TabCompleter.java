package org.rocketplugins.cmdface.CommandManagers;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TabCompleter implements org.bukkit.command.TabCompleter {

    private final Map<String, CommandInterface> subCommands;

    public TabCompleter(Map<String, CommandInterface> subCommands) {
        this.subCommands = subCommands;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {

        if (args.length == 1) {
            return new ArrayList<>(subCommands.keySet());
        }

        CommandInterface sub = subCommands.get(args[0].toLowerCase());

        if (sub == null) {
            return List.of();
        }

        return sub.getArgs(args.length - 2);
    }
}
