package org.rocketplugins.cmdface.CommandManagers;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public interface CommandInterface {
    List<String> getArgs(int index);

    void Execute(CommandSender commandSender, Command command, String s, String[] strings);
}
