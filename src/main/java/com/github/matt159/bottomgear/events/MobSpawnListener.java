package com.github.matt159.bottomgear.events;

import com.github.matt159.bottomgear.data.GearScore;
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
        if (GearScore.getInstance().getGearScores().size() == 0) {
            event.setResult(Event.Result.DENY);
            earlyReturn = true;
        }

        if (!(event.entity instanceof IMob) || earlyReturn) {
            return;
        }

        EntityLiving mob = (EntityLiving) event.entity;
        //range check is 130 instead of 128 because a spider spawn was occasionally happening due to spiders having
        //a size of 2x2 blocks
        EntityPlayer player = mob.worldObj.getClosestPlayer(event.x, event.y, event.z, 130);
        int dimID = mob.worldObj.provider.dimensionId;

        if (player != null && GearScore.getInstance().getDimScores().containsKey(dimID)) {
            Integer playerGearScore = GearScore.getInstance().getPlayerScores().get(player.getUniqueID());
            if (playerGearScore == null) playerGearScore = -1;

            Integer dimGearThreshold = GearScore.getInstance().getDimScores().get(dimID);

            if (playerGearScore > dimGearThreshold) {
                event.setResult(Event.Result.DENY);
            }
        }
    }
}
