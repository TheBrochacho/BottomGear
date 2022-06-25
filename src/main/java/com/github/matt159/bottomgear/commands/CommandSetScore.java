package com.github.matt159.bottomgear.commands;

import com.github.matt159.bottomgear.data.GearScore;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;

import java.util.List;

public class CommandSetScore extends SubCommand {
    public CommandSetScore() {
        super("set");
        setPermLevel(PermLevel.ADMIN);
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        switch (args.length) {
            case 1:
                if ("help".equalsIgnoreCase(args[0])) {
                    sender.addChatMessage(new ChatComponentText("\"/bg set <unlocalized item name> <damage value> <gear score>\""));
                } else {
                    throw new WrongUsageException("Too few arguments");
                }
                break;
            case 3:
                try {
                    Item item = (Item) Item.itemRegistry.getObject(args[0]);
                    int damageValue = Integer.parseInt(args[1]);
                    int gearScore = Integer.parseInt(args[2]);

                    ItemStack itemStack = new ItemStack(item, 1, damageValue);

                    GearScore.getGearScores().put(itemStack.getDisplayName(), gearScore);

                    sender.addChatMessage(new ChatComponentText(String.format("Set the gear score of %s to %d",
                            itemStack.getDisplayName(),
                            gearScore)));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }

                break;
            default:
                throw new WrongUsageException("Invalid number of arguments");
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args) {
        if (args.length == 1) {
            return (List<String>) CommandBase.getListOfStringsFromIterableMatchingLastWord(args, Item.itemRegistry.getKeys());
        }
        return null;
    }
}
