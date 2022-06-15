package com.github.matt159.bottomgear;

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

import java.io.File;

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
    public static final CommandBG commandBG  = new CommandBG();

    public static File configDirectory = null;
    public static File gearConfigFile = null;
    public static File dimConfigFile = null;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        configDirectory = new File(event.getModConfigurationDirectory().getPath(), "BottomGear");
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {

        BGConfig.isBaublesLoaded = Loader.isModLoaded("Baubles");
        BGConfig.isTravellersGearLoaded = Loader.isModLoaded("TravellersGear");
        BGConfig.isTinkersLoaded = Loader.isModLoaded("TConstruct");

        MinecraftForge.EVENT_BUS.register(new PlayerListener());
        MinecraftForge.EVENT_BUS.register(new MobSpawnListener());

        GameRegistry.registerItem(DEBUG_STICK, DEBUG_STICK.getUnlocalizedName());
    }

    @EventHandler
    public void loadComplete(FMLLoadCompleteEvent event) {

        gearConfigFile = new File(configDirectory, "GearScores.txt");
        dimConfigFile = new File(configDirectory, "DimScores.txt");

        try {
            if (!configDirectory.exists()) {
                configDirectory.mkdir();
            }

            FileUtil.saveGearScoresToFile();
            FileUtil.saveDimThresholdsToFile();

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            FileUtil.parseGearConfigFile(gearConfigFile);
            FileUtil.parseDimConfigFile(dimConfigFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @EventHandler
    public void serverStarting(FMLServerStartingEvent event) {
        CommandHandler commandHandler = (CommandHandler) event.getServer().getCommandManager();
        commandHandler.registerCommand(commandBG);
    }
}
