package com.github.matt159.bottomgear;

import com.github.matt159.bottomgear.data.EquipmentCategory;
import com.github.matt159.bottomgear.events.MobSpawnListener;
import com.github.matt159.bottomgear.events.PlayerListener;
import com.github.matt159.bottomgear.item.BottomStick;
import com.github.matt159.bottomgear.util.BGConfig;
import com.github.matt159.bottomgear.commands.CommandBG;
import com.github.matt159.bottomgear.util.FileUtil;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLLoadCompleteEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.command.CommandHandler;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;

@Mod(modid = BottomGear.MODID, name = BottomGear.NAME, version = BottomGear.VERSION,
        dependencies = "after:Baubles; "
        + "after:TravellersGear; "
        + "after:TConstruct; "
    )
public class BottomGear {
    public static final String MODID = "bottomgear";
    public static final String NAME = "Bottom Gear";
    public static final String VERSION = "@version@";

    public static final Item DEBUG_STICK = new BottomStick();
    public static final CommandBG commandBG = new CommandBG();


    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        FileUtil.onPreInit(event);
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {

        BGConfig.isBaublesLoaded = Loader.isModLoaded("Baubles");
        BGConfig.isTravellersGearLoaded = Loader.isModLoaded("TravellersGear");
        BGConfig.isTinkersLoaded = Loader.isModLoaded("TConstruct");
        BGConfig.isDWSLoaded = Loader.isModLoaded("dws");

        MinecraftForge.EVENT_BUS.register(new PlayerListener());
        MinecraftForge.EVENT_BUS.register(new MobSpawnListener());

        GameRegistry.registerItem(DEBUG_STICK, DEBUG_STICK.getUnlocalizedName());
    }

    @EventHandler
    public void loadComplete(FMLLoadCompleteEvent event) {
        EquipmentCategory.init();
        FileUtil.onLoadComplete();
    }

    @EventHandler
    public void serverStarting(FMLServerStartingEvent event) {
        CommandHandler commandHandler = (CommandHandler) event.getServer().getCommandManager();
        commandHandler.registerCommand(commandBG);
    }
}
