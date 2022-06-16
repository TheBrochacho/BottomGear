package com.github.matt159.bottomgear.commands;

import com.github.matt159.bottomgear.data.GearScore;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.WorldProvider;

public class CommandSetDim extends SubCommand {
    public CommandSetDim() {
        super("dim");
        setPermLevel(PermLevel.ADMIN);
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        if (args.length < 1) {
            throw new WrongUsageException("Missing dimensional gear score threshold to assign to current dimension");
        }

        try {
            int value = Integer.parseInt(args[0]);

            if (value >= 0) {
                if (sender instanceof EntityPlayerMP) {
                    WorldProvider wp = ((EntityPlayerMP) sender).getEntityWorld().provider;
                    int dimID = wp.dimensionId;

                    GearScore.getInstance().getDimScores().put(dimID, value);

                    sender.addChatMessage(new ChatComponentText(String.format("Set the gear score threshold of the %s to %d.",
                            wp.getDimensionName(),
                            value)));
                }
            } else {
                throw new WrongUsageException("Value should be >= 0");
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }
}
