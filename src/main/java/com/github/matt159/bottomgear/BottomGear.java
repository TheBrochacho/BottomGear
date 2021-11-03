package com.github.matt159.bottomgear;

import com.github.matt159.bottomgear.events.MobSpawnListener;
import com.github.matt159.bottomgear.events.PlayerListener;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.common.MinecraftForge;

@Mod(modid = BottomGear.MODID, name = BottomGear.NAME, version = BottomGear.VERSION)
public class BottomGear {
    public static final String MODID = "bottomgear";
    public static final String NAME = "Bottom Gear";
    public static final String VERSION = "@version@";

    @EventHandler
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new PlayerListener());
        MinecraftForge.EVENT_BUS.register(new MobSpawnListener());
    }
}
