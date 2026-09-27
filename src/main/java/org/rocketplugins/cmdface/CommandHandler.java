package org.rocketplugins.cmdface;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.Plugin;
import org.rocketplugins.cmdface.CommandManagers.CommandInterface;
import org.rocketplugins.cmdface.CommandManagers.TabCompleter;

import java.util.HashMap;
import java.util.Map;

public class CommandHandler implements CommandExecutor {
    private final Map<String, CommandInterface> subCommands = new HashMap<>();

    /**
     * initializes all the subcommands to the root command, sets this class as the command executor and creates a tabcompleter instance.
     * @param command
     * @param subCommand
     */
    public CommandHandler(PluginCommand command, HashMap<String, CommandInterface> subCommand) {

        for (String key : subCommand.keySet()) {
            registerSubCommand(key, subCommand.get(key));
        }
        command.setExecutor(this);
        command.setTabCompleter(new TabCompleter(subCommands));
    }

    /**
     * registers a command into the commandHandler by inputting a key (name of subcommand) and a value (class instance) into a
     * hashmap.
     * @param label the subcommand that is typed in
     * @param subCommand the instance of the subcommand class to run when executed.
     */
    private void registerSubCommand(String label, CommandInterface subCommand) {
        subCommands.put(label.toLowerCase(), subCommand);
    }

    /**
     * when a command is run, check if it has a valid subcommand, then execute that subcommand, using the subcommand list minus the first element as an argument.
     * @param args the list of subcommands, starting from 0.
     */
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length > 0) {
            CommandInterface child = subCommands.get(args[0].toLowerCase());
            if (child != null) {
                String[] newArgs = new String[args.length - 1];
                System.arraycopy(args, 1, newArgs, 0, newArgs.length);
                child.Execute(sender, command, args[0], newArgs);
                return true;
            }
        }
        return false;
    }

    /**
     * used to map out all the subcommands in the tabcompleter
     * each subcommand should have its own getArgs() class which returns a precoded list of args at any given index
     * this system may seem convoluted but hopefully the fragmented code should make everything more readable
     * @return map of all subcommands
     */
    public Map<String, CommandInterface> getSubCommands() {
        return subCommands;
    }
}
