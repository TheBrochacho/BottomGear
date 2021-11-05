package com.github.matt159.bottomgear.events;

import com.github.matt159.bottomgear.data.PlayerGearScores;
import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;

public class MobSpawnListener {
    @SubscribeEvent
    public void mobSpawnEvent(LivingSpawnEvent.CheckSpawn event) {
        try {
            if (event.entity instanceof EntityMob) {
                EntityMob mob = (EntityMob) event.entity;

                EntityPlayer player = mob.worldObj.getClosestPlayer(event.x, event.y, event.z, 128);

                if (player != null && PlayerGearScores.getInstance().get(player.getUniqueID()) > 4) {
                    event.setResult(Event.Result.DENY);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
