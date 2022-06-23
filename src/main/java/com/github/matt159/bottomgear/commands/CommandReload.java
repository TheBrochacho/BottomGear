package com.github.matt159.bottomgear.commands;

import com.github.matt159.bottomgear.util.FileUtil;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.util.ChatComponentText;

public class CommandReload extends SubCommand {

    public CommandReload() {
        super("reload");
        setPermLevel(PermLevel.ADMIN);
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        if (args.length < 1) {
            args = new String[] { "all" };
        }

        try {
            switch (args[0]) {
                case "gear":
                    FileUtil.parseGearConfigFile();
                    sender.addChatMessage(new ChatComponentText("Reloaded gear scores from file."));
                    break;
                case "dims":
                    FileUtil.parseDimConfigFile();
                    sender.addChatMessage(new ChatComponentText("Reloaded dimension thresholds from file."));
                    break;
                case "all":
                    FileUtil.parseGearConfigFile();
                    sender.addChatMessage(new ChatComponentText("Reloaded gear scores from file."));

                    FileUtil.parseDimConfigFile();
                    sender.addChatMessage(new ChatComponentText("Reloaded dimension thresholds from file."));
                    break;
                default:
                    throw new WrongUsageException("Options are \"gear\", \"dims\" or \"all\"");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
