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

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

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

        Set<ItemStack> equipment = new HashSet<>();
        EntityPlayer player = (EntityPlayer) event.entity;

        if (!player.worldObj.isRemote) {

            equipment.addAll(Arrays.asList(player.inventory.armorInventory));

            for (int i = 0; i < InventoryPlayer.getHotbarSize(); ++i) {
                //compatibility with dws
                ItemStack itemStack = null;
                if (i >= 9 && BGConfig.isDWSLoaded) {
                    itemStack = player.inventory.getStackInSlot(i + 54);
                }
                else {
                    itemStack = player.inventory.getStackInSlot(i);
                }
                equipment.add(itemStack);
            }

            if (BGConfig.isBaublesLoaded) {
                InventoryBaubles baubles = PlayerHandler.getPlayerBaubles(player);
                equipment.addAll(Arrays.asList(baubles.stackList));
            }

            if (BGConfig.isTinkersLoaded) {
                TPlayerStats tps = TPlayerStats.get(player);
                equipment.addAll(Arrays.asList(tps.armor.inventory));
            }

            GearScore.getPlayerScores().put(
                    player.getUniqueID(),
                    GearScore.calculateScore(equipment.stream()
                            .filter(Objects::nonNull)
                            .map(BGUtil::itemStackToEntry)
                            .distinct()
                            .collect(Collectors.toList())));
        }
    }
}
