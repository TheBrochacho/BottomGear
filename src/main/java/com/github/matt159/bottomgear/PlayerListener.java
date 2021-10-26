package com.github.matt159.bottomgear;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.event.entity.player.PlayerUseItemEvent;

public class PlayerListener {

    private static final String message = "welcome to bottom gear mates\n" +
            "today on bottom gear a driver silent\n" +
            "electric car\n" +
            "hammond uses a [ __ ] toilet and james\n" +
            "commits arson\n" +
            "warning strong budget does not exceed 23\n" +
            "yen good evening ladies and gents today\n" +
            "our sponsors are msc colgate b450 check\n" +
            "them out promo code\n" +
            "revving my wife to noi [ __ ] today we'll\n" +
            "be reviewing one of the kin\n" +
            "vehicle that has about 2.3 gigahertz\n" +
            "revving engine\n" +
            "sound goes up to the i don't know\n" +
            "[ __ ] 88 millimeters from every time i\n" +
            "find a proper job\n" +
            "mate we're going to ask do you want\n" +
            "anything\n" +
            "oh you love you push [ __ ] i have\n" +
            "gotten my back give me a second\n" +
            "where is the lambo chevy going to crash\n" +
            "the new james carmichael\n" +
            "how much you sorting this with my\n" +
            "lamborghini\n" +
            "call 999 me [ __ ] cars being on fire\n" +
            "my\n" +
            "come on i have crack addiction i'm dying\n" +
            "jeremy i have to really force papers\n" +
            "today i don't know what to do next\n" +
            "please help me i can't go have petroleum\n" +
            "tell me tell me i'll meet him gear what\n" +
            "happens when the taste exists feel good\n" +
            "i have a whiff\n" +
            "our noise no jeremiah car got bass for\n" +
            "health\n" +
            "shut up\n" +
            "shut up gems the spiders i load my\n" +
            "weed cart feet";

    @SubscribeEvent
    public void onPlayerUseItem(PlayerUseItemEvent.Start event) {
        event.entityPlayer.worldObj.playSoundAtEntity(event.entityPlayer, "bottomgear:bottomGear", 1.0f, 1.0f);
        //event.entityPlayer.addChatMessage(new ChatComponentText(message));
    }
}
