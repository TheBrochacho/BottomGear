package com.github.matt159.bottomgear.events;

import com.github.matt159.bottomgear.data.PlayerGearScores;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemTool;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.player.PlayerUseItemEvent;

public class PlayerListener {

    @SubscribeEvent
    public void onPlayerUseItem(PlayerUseItemEvent.Start event) {
        System.out.println(event.entityPlayer.worldObj.isRemote);
        event.entityPlayer.worldObj.playSoundAtEntity(event.entityPlayer, "bottomgear:bottomGear", 1.0f, 1.0f);
    }

    @SubscribeEvent
    public void onPlayerTick(LivingUpdateEvent event) {
        if (event.entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.entity;
            if (!player.worldObj.isRemote) {
                int numGearItems = 0;

                //counting items in the armor slots
                for (int i = 0; i < 4; i++) {
                    numGearItems += player.getCurrentArmor(i) != null ? 1 : 0;
                }

                for (int i = 0; i < player.inventory.mainInventory.length; i++) {
                    if (player.inventory.mainInventory[i] != null) {
                        numGearItems += player.inventory.mainInventory[i].getItem() instanceof ItemTool ? 1 : 0;
                    }
                }

                PlayerGearScores.getInstance().put(player.getUniqueID(), numGearItems);
            }
        }
    }
}
