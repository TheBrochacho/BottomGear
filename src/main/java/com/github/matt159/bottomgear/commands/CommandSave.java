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
                    sender.addChatMessage(new ChatComponentText("Saved gear scores to file."));
                    FileUtil.saveGearScoresToFile();
                    break;
                case "dims":
                    sender.addChatMessage(new ChatComponentText("Saved dimension thresholds to file."));
                    FileUtil.saveDimThresholdsToFile();
                    break;
                case "all":
                    sender.addChatMessage(new ChatComponentText("Saved gear scores to file."));
                    FileUtil.saveGearScoresToFile();

                    sender.addChatMessage(new ChatComponentText("Saved dimension thresholds to file."));
                    FileUtil.saveDimThresholdsToFile();
                    break;
                default:
                    throw new WrongUsageException("Options are \"gear\", \"dims\" or \"all\"");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
