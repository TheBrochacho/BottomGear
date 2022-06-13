package com.github.matt159.bottomgear.commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;

import java.util.ArrayList;
import java.util.List;

public class CommandBG extends CommandBase {

    private final List<String> aliases = new ArrayList<>();

    public CommandBG() {
        aliases.add("BottomGear");
        aliases.add("bottomGear");
        aliases.add("Bottomgear");
        aliases.add("bg");
        aliases.add("Bg");
        aliases.add("bG");
        aliases.add("BG");
    }

    @Override
    public String getCommandName() {
        return "bottomgear";
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
    public void processCommand(ICommandSender p_71515_1_, String[] p_71515_2_) {

    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }
}
