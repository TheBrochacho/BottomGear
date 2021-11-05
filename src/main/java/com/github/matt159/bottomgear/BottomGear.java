package com.github.matt159.bottomgear;

import com.github.matt159.bottomgear.events.MobSpawnListener;
import com.github.matt159.bottomgear.events.PlayerListener;
import com.github.matt159.bottomgear.item.DebugStick;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;

@Mod(modid = BottomGear.MODID, name = BottomGear.NAME, version = BottomGear.VERSION, dependencies = "required-after:NotEnoughItems")
public class BottomGear {
    public static final String MODID = "bottomgear";
    public static final String NAME = "Bottom Gear";
    public static final String VERSION = "@version@";

    public static final DebugStick DEBUG_STICK = new DebugStick(Item.ToolMaterial.IRON);

    @EventHandler
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new PlayerListener());
        MinecraftForge.EVENT_BUS.register(new MobSpawnListener());

        GameRegistry.registerItem(DEBUG_STICK, DEBUG_STICK.getUnlocalizedName());
    }
}
