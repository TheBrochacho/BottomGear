package com.github.matt159.bottomgear.commands;

import com.github.matt159.bottomgear.BottomGear;
import com.github.matt159.bottomgear.util.FileParser;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;

public class CommandReload extends SubCommand {

    public CommandReload() {
        super("reload");
        setPermLevel(PermLevel.ADMIN);
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        if (args.length < 1) {
            throw new WrongUsageException("Insufficient number of arguments.");
        }

        try {
            switch (args[0]) {
                case "gear":
                    FileParser.parseGearConfigFile(BottomGear.gearConfigFile);
                    break;
                case "dims":
                    FileParser.parseDimConfigFile(BottomGear.dimConfigFile);
                    break;
                case "all":
                    FileParser.parseGearConfigFile(BottomGear.gearConfigFile);
                    FileParser.parseDimConfigFile(BottomGear.dimConfigFile);
                    break;
                default:
                    throw new WrongUsageException("Options are \"gear\", \"dims\" or \"all\"");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
