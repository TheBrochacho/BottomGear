package com.github.matt159.bottomgear.events;

import baubles.common.container.InventoryBaubles;
import baubles.common.lib.PlayerHandler;
import com.github.matt159.bottomgear.data.GearScore;
import com.github.matt159.bottomgear.util.BGConfig;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.*;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.player.PlayerUseItemEvent;
import tconstruct.armor.player.TPlayerStats;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class PlayerListener {

    @SubscribeEvent
    public void onPlayerUseItem(PlayerUseItemEvent.Start event) {
        System.out.println(event.entityPlayer.worldObj.isRemote);
        event.entityPlayer.worldObj.playSoundAtEntity(event.entityPlayer, "bottomgear:bottomGear", 1.0f, 1.0f);
    }

    @SubscribeEvent
    public void onPlayerTick(LivingUpdateEvent event) {
        if (!(event.entity instanceof EntityPlayer)) {
            return;
        }

        Set<String> equipment = new HashSet<>();
        EntityPlayer player = (EntityPlayer) event.entity;

        if (!player.worldObj.isRemote) {

            for (ItemStack itemStack : player.inventory.armorInventory) {
                if (itemStack != null) {
                    equipment.add(itemStack.getDisplayName());
                }
            }

            for (int i = 0; i < InventoryPlayer.getHotbarSize(); ++i) {
                //compatibility with dws
                ItemStack itemStack;
                if (i >= 9 && BGConfig.isDWSLoaded) {
                    itemStack = player.inventory.getStackInSlot(i + 54);
                }
                else {
                    itemStack = player.inventory.getStackInSlot(i);
                }

                if (itemStack != null) {
                    equipment.add(itemStack.getDisplayName());
                }
            }

            if (BGConfig.isBaublesLoaded) {
                InventoryBaubles baubles = PlayerHandler.getPlayerBaubles(player);
                for (ItemStack itemStack : baubles.stackList) {
                    if (itemStack != null) {
                        equipment.add(itemStack.getDisplayName());
                    }
                }
            }

            if (BGConfig.isTinkersLoaded) {
                TPlayerStats tps = TPlayerStats.get(player);
                for (ItemStack itemStack : tps.armor.inventory) {
                    if (itemStack != null) {
                        equipment.add(itemStack.getDisplayName());
                    }
                }
            }

            GearScore.getPlayerScores().put(player.getUniqueID(), GearScore.calculateScore(new ArrayList<>(equipment)));
        }
    }
}
