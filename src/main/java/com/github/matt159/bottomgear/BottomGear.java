package com.github.matt159.bottomgear;

import net.minecraft.init.Blocks;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;

@Mod(modid = BottomGear.MODID, name = BottomGear.NAME, version = BottomGear.VERSION)
public class BottomGear {
    public static final String MODID = "examplemod";
    public static final String NAME = "Example Mod";
    public static final String VERSION = "@version@";

    @EventHandler
    public void init(FMLInitializationEvent event) {
        //Some example code
        System.out.println("DIRT BLOCK 2 >> " + Blocks.dirt.getUnlocalizedName());

        System.out.println(MODID);
        System.out.println(MODID);
        System.out.println(MODID);
    }
}
