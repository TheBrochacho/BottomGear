package com.github.matt159.bottomgear.commands;

import com.github.matt159.bottomgear.util.FileUtil;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.util.ChatComponentText;

public class CommandSave extends SubCommand {

    public CommandSave() {
        super("save");
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        if (args.length < 1) {
            args = new String[] { "all" };
        }

        try {
            switch (args[0]) {
                case "gear":
                    FileUtil.saveGearScoresToFile();
                    sender.addChatMessage(new ChatComponentText("Saved gear scores to file."));
                    break;
                case "dims":
                    FileUtil.saveDimThresholdsToFile();
                    sender.addChatMessage(new ChatComponentText("Saved dimension thresholds to file."));
                    break;
                case "all":
                    FileUtil.saveGearScoresToFile();
                    sender.addChatMessage(new ChatComponentText("Saved gear scores to file."));

                    FileUtil.saveDimThresholdsToFile();
                    sender.addChatMessage(new ChatComponentText("Saved dimension thresholds to file."));
                    break;
                default:
                    throw new WrongUsageException("Options are \"gear\", \"dims\" or \"all\"");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
