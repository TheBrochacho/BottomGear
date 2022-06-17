package com.github.matt159.bottomgear.commands;

import com.github.matt159.bottomgear.data.GearScore;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentText;

import java.util.UUID;

public class CommandScore extends SubCommand {

    public CommandScore() {
        super("score");
        setPermLevel(PermLevel.EVERYONE);
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        if (sender instanceof EntityPlayerMP) {
            UUID uuid = ((EntityPlayerMP) sender).getUniqueID();
            sender.addChatMessage(new ChatComponentText(String.format("Your current gear score is %d.",
                    GearScore.getPlayerScores().get(uuid))));
        }
    }
}
