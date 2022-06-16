package com.github.matt159.bottomgear.events;

import baubles.common.container.InventoryBaubles;
import baubles.common.lib.PlayerHandler;
import com.github.matt159.bottomgear.data.GearScore;
import com.github.matt159.bottomgear.util.BGConfig;
import com.github.matt159.bottomgear.util.BGUtil;
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

            for (ItemStack item : player.inventory.armorInventory) {
                if (item != null) {
                    equipment.add(BGUtil.itemStackToEntry(item));
                }
            }

            for (int i = 0; i < InventoryPlayer.getHotbarSize(); ++i) {
                ItemStack item = player.inventory.getStackInSlot(i);
                if (item != null) {
                    equipment.add(BGUtil.itemStackToEntry(item));
                }
            }

            if (BGConfig.isBaublesLoaded) {
                InventoryBaubles baubles = PlayerHandler.getPlayerBaubles(player);
                for (ItemStack item : baubles.stackList) {
                    if (item != null) {
                        equipment.add(BGUtil.itemStackToEntry(item));
                    }
                }
            }

            if (BGConfig.isTinkersLoaded) {
                TPlayerStats tps = TPlayerStats.get(player);
                for (ItemStack item : tps.armor.inventory) {
                    if (item != null) {
                        equipment.add(BGUtil.itemStackToEntry(item));
                    }
                }
            }

            GearScore gs = GearScore.getInstance();
            gs.getPlayerScores().put(player.getUniqueID(), gs.calculateScore(new ArrayList<>(equipment)));
        }
    }
}
