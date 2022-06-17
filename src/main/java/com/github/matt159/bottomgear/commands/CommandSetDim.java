package com.github.matt159.bottomgear.commands;

import com.github.matt159.bottomgear.data.GearScore;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.WorldProvider;
import net.minecraftforge.common.DimensionManager;

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

        switch (args.length) {
            case 1:
                if ("help".equalsIgnoreCase(args[0])) {
                    sender.addChatMessage(new ChatComponentText(getHelpMessage()));
                    return;
                }

                try {
                    int value = Integer.parseInt(args[0]);

                    if (value >= 0) {
                        if (sender instanceof EntityPlayerMP) {
                            WorldProvider wp = sender.getEntityWorld().provider;
                            int dimID = wp.dimensionId;

                            GearScore.getDimScores().put(dimID, value);

                            sender.addChatMessage(new ChatComponentText(String.format("Set the gear score threshold of the %s to %d",
                                    wp.getDimensionName(),
                                    value)));
                        } else {
                            throw new WrongUsageException("This command should only be called by a player");
                        }
                    } else {
                        throw new WrongUsageException("Value should be >= 0");
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                break;
            case 2:
                try {
                    int dimID = Integer.parseInt(args[0]);
                    if (!DimensionManager.isDimensionRegistered(dimID)) {
                        throw new WrongUsageException("Invalid Dimension ID");
                    }

                    int value = Integer.parseInt(args[1]);
                    if (value < 0) {
                        throw new WrongUsageException("Value should be greater than 0");
                    }

                    GearScore.getDimScores().put(dimID, value);
                    sender.addChatMessage(new ChatComponentText(String.format("Set the gear score threshold of the %s to %d",
                            DimensionManager.getProvider(dimID).getDimensionName(),
                            value)));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                break;
            default:
                throw new WrongUsageException("Invalid number of arguments");
        }

    }

    private String getHelpMessage() {
        StringBuilder helpMessage = new StringBuilder();
        helpMessage.append("Usage options for \"/bg dim\"\n");
        helpMessage.append("Set the gear score threshold of the current dimension\n");
        helpMessage.append("/bg dim <value>\n");
        helpMessage.append("Set the gear score threshold of the specified dimension\n");
        helpMessage.append("/bg dim <dimID> <value>");

        return helpMessage.toString();
    }
}
