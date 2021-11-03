package com.github.matt159.bottomgear.events;

import com.github.matt159.bottomgear.data.PlayerGearScores;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import cpw.mods.fml.common.gameevent.TickEvent.PlayerTickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemTool;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.event.entity.player.PlayerOpenContainerEvent;
import net.minecraftforge.event.entity.player.PlayerUseItemEvent;

public class PlayerListener {

    @SubscribeEvent
    public void onPlayerUseItem(PlayerUseItemEvent.Start event) {
        System.out.println(event.entityPlayer.worldObj.isRemote);
        event.entityPlayer.worldObj.playSoundAtEntity(event.entityPlayer, "bottomgear:bottomGear", 1.0f, 1.0f);
    }

    @SubscribeEvent
    public void onPlayerTick(PlayerTickEvent event) {
        System.out.println("lmao");
    }

    @SubscribeEvent
    public void onPlayerOpenContainer(PlayerOpenContainerEvent event) {
        if (!event.entityPlayer.worldObj.isRemote) {
            int numGearItems = 0;

            EntityPlayer player = event.entityPlayer;

            //counting items in the armor slots
            for (int i = 0; i < 4; i++) {
                numGearItems += player.getCurrentArmor(i) != null ? 1 : 0;
            }

            for (int i = 0; i < player.inventory.mainInventory.length; i++) {
                if (player.inventory.mainInventory[i] != null) {
                    numGearItems += player.inventory.mainInventory[i].getItem() instanceof ItemTool ? 1 : 0;
                }
            }

//            System.out.println("numGearItems: " + numGearItems);
            PlayerGearScores.getInstance().put(player.getUniqueID(), numGearItems);
        }
    }
}
