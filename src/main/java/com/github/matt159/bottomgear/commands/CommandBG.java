package com.github.matt159.bottomgear.commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.util.ChatComponentText;

import java.util.*;

public class CommandBG extends CommandBase {
    private final Map<String, SubCommand> children = new HashMap<>();
    private final List<String> aliases = new ArrayList<>();

    public CommandBG() {
        aliases.add("bg");
        aliases.add("bottomgear");

        children.put("reload", new CommandReload());
        children.put("hand", new CommandSetHand());
        children.put("score", new CommandScore());
        children.put("dim", new CommandSetDim());
    }

    @Override
    public String getCommandName() {
        return "/bottomgear";
    }

    @Override
    @SuppressWarnings("rawtypes")
    public List getCommandAliases() {
        return aliases;
    }

    @Override
    public String getCommandUsage(ICommandSender p_71518_1_) {
        return getCommandName() + " help";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        if (args.length < 1) {
            throw new WrongUsageException("Too few arguments");
        }

        switch (args[0]) {
            case "help":
                sender.addChatMessage(new ChatComponentText("Use \"/bottomgear reload\" to reload gear/dimension values from file"));
                break;
            default:
                if (!children.containsKey(args[0])) {
                    throw new WrongUsageException("Invalid Arguments. Run \"/bottomgear help\" to see a list of commands.");
                }

                ICommand command = children.get(args[0]);
                if (command.canCommandSenderUseCommand(sender)) {
                    String[] newArgs = new String[args.length - 1];
                    System.arraycopy(args, 1, newArgs, 0, newArgs.length);
                    command.processCommand(sender, newArgs);
                }
                break;
        }
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }
}
