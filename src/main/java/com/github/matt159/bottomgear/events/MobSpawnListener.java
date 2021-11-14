package com.github.matt159.bottomgear.events;

import com.github.matt159.bottomgear.data.Scores;
import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;

public class MobSpawnListener {
    @SubscribeEvent
    public void mobSpawnEvent(LivingSpawnEvent.CheckSpawn event) {
        //this function has a few areas that could be more well thought out.
        boolean earlyReturn = false;

        //Because mobs try to spawn on first world load before a player can tick.
        if (Scores.getInstance().getGearScores().size() == 0) {
            event.setResult(Event.Result.DENY);
            earlyReturn = true;
        }

        if (!(event.entity instanceof IMob) || earlyReturn) {
            return;
        }

        EntityLiving mob = (EntityLiving) event.entity;
        EntityPlayer player = mob.worldObj.getClosestPlayer(event.x, event.y, event.z, 128);
        int dimID = mob.worldObj.provider.dimensionId;

        if (player != null && Scores.getInstance().getDimScores().containsKey(dimID)) {
            Integer playerGearScore = Scores.getInstance().getPlayerScores().get(player.getUniqueID());
            if (playerGearScore == null) playerGearScore = -1;

            Integer dimGearThreshold = Scores.getInstance().getDimScores().get(dimID);

            if (playerGearScore > dimGearThreshold) {
                event.setResult(Event.Result.DENY);
            }
        }
    }
}
