package com.github.matt159.bottomgear;

import com.github.matt159.bottomgear.events.MobSpawnListener;
import com.github.matt159.bottomgear.events.PlayerListener;
import com.github.matt159.bottomgear.item.BottomStick;
import com.github.matt159.bottomgear.util.BGConfig;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;

@Mod(modid = BottomGear.MODID, name = BottomGear.NAME, version = BottomGear.VERSION,
        dependencies = "after:Baubles; "
        + "after:TravellersGear; "
    )
public class BottomGear {
    public static final String MODID = "bottomgear";
    public static final String NAME = "Bottom Gear";
    public static final String VERSION = "@version@";

    public static final Item DEBUG_STICK = new BottomStick();

    @EventHandler
    public void init(FMLInitializationEvent event) {

        BGConfig.isBaublesLoaded = Loader.isModLoaded("Baubles");
        BGConfig.isTravellersGearLoaded = Loader.isModLoaded("TravellersGear");

        MinecraftForge.EVENT_BUS.register(new PlayerListener());
        MinecraftForge.EVENT_BUS.register(new MobSpawnListener());

        GameRegistry.registerItem(DEBUG_STICK, DEBUG_STICK.getUnlocalizedName());
    }
}
