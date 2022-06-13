package com.github.matt159.bottomgear;

import com.github.matt159.bottomgear.data.GearScore;
import com.github.matt159.bottomgear.events.MobSpawnListener;
import com.github.matt159.bottomgear.events.PlayerListener;
import com.github.matt159.bottomgear.item.BottomStick;
import com.github.matt159.bottomgear.util.BGConfig;
import com.github.matt159.bottomgear.util.FileParser;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLLoadCompleteEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;

import java.io.File;
import java.io.PrintWriter;

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

    private File configDirectory = null;
    private File gearConfigFile = null;
    private File dimConfigFile = null;

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

        gearConfigFile = new File(this.configDirectory, "GearScores.txt");
        dimConfigFile = new File(this.configDirectory, "DimScores.txt");

        try {
            if (!configDirectory.exists()) {
                configDirectory.mkdir();
            }

            if (!gearConfigFile.exists()) {
                gearConfigFile.createNewFile();
                PrintWriter pw = new PrintWriter(gearConfigFile);

                GearScore gs = GearScore.getInstance();
                gs.getGearScoreList().forEach(pw::println);
                pw.flush();
                pw.close();
            }

            if (!dimConfigFile.exists()) {
                dimConfigFile.createNewFile();
                PrintWriter pw = new PrintWriter(dimConfigFile);

                GearScore gs = GearScore.getInstance();
                gs.getDimScoreList().forEach(pw::println);
                pw.flush();
                pw.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            FileParser.parseGearConfigFile(gearConfigFile);
            FileParser.parseDimConfigFile(dimConfigFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
