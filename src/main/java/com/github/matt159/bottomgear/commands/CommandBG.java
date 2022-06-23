package com.github.matt159.bottomgear.commands;

import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;

import java.util.*;

public class CommandBG extends SubCommand {

    public CommandBG() {
        super("bottomgear");
        setPermLevel(PermLevel.EVERYONE);

        aliases.add("bg");

        addChildCommand(new CommandReload());
        addChildCommand(new CommandSave());
        addChildCommand(new CommandSetHand());
        addChildCommand(new CommandScore());
        addChildCommand(new CommandSetDim());
        addChildCommand(new CommandSetScore());
    }

    @Override
    public String getCommandName() {
        return "/bottomgear";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return getCommandName() + " help";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        if (args.length < 1) {
            throw new WrongUsageException("Too few arguments");
        }

        if ("help".equals(args[0])) {
            printHelp(sender, this);
        } else {
            if (!children.containsKey(args[0])) {
                throw new WrongUsageException("Invalid Arguments. Run \"/bottomgear help\" to see a list of commands.");
            }

            ICommand command = children.get(args[0]);
            if (command.canCommandSenderUseCommand(sender)) {
                String[] newArgs = new String[args.length - 1];
                System.arraycopy(args, 1, newArgs, 0, newArgs.length);
                command.processCommand(sender, newArgs);
            }
        }
    }

    @SuppressWarnings("unchecked")
    public static void printHelp(ICommandSender sender, SubCommand command) {
        ChatStyle header = new ChatStyle().setColor(EnumChatFormatting.AQUA);
        sender.addChatMessage(new ChatComponentText("Format: /bottomgear <sub-command>").setChatStyle(header));

        List<String> text = new LinkedList<>();
        ChatStyle body = new ChatStyle().setColor(EnumChatFormatting.GRAY);

        text.add("Aliases: " + String.join(", ", command.getCommandAliases()));
        text.add("Permission Level: " + command.permLevel.permLevel);
        text.add("Various Sub-Commands for Bottom Gear");
        text.forEach(line -> sender.addChatMessage(new ChatComponentText(line).setChatStyle(body)));

        text.clear();
        ChatStyle footer = new ChatStyle().setColor(EnumChatFormatting.WHITE);
        text.add("Available Sub-Commands");
        command.getChildren().values().forEach(child -> text.add("- " + child.getCommandName()));
        text.forEach(line -> sender.addChatMessage(new ChatComponentText(line).setChatStyle(footer)));
    }
}
