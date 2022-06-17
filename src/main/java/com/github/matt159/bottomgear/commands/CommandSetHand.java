package com.github.matt159.bottomgear.commands;

import com.github.matt159.bottomgear.data.GearScore;
import com.github.matt159.bottomgear.util.BGUtil;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;

public class CommandSetHand extends SubCommand {

    public CommandSetHand() {
        super("hand");
        setPermLevel(PermLevel.ADMIN);
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        if (args.length < 1) {
            throw new WrongUsageException("Missing gear score to assign to current item");
        }

        try {
            int value = Integer.parseInt(args[0]);

            if (value >= 0) {
                if (sender instanceof EntityPlayerMP) {
                    ItemStack itemStack = ((EntityPlayerMP) sender).getCurrentEquippedItem();

                    GearScore.getGearScores().put(itemStack.getDisplayName(), value);

                    sender.addChatMessage(new ChatComponentText(String.format("Set the gear score of %s to %d.",
                            itemStack.getDisplayName(),
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
