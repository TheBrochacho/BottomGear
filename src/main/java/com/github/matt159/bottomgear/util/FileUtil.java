package com.github.matt159.bottomgear.util;

import com.github.matt159.bottomgear.data.GearScore;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraft.item.ItemStack;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Scanner;

public final class FileUtil {
    private FileUtil() {}

    public static File configDirectory = null;
    public static File gearConfigFile = null;
    public static File dimConfigFile = null;

    public static void onPreInit(FMLPreInitializationEvent event) {
        configDirectory = new File(event.getModConfigurationDirectory().getPath(), "BottomGear");
    }

    public static void onLoadComplete() {
        gearConfigFile = new File(configDirectory, "GearScores.txt");
        dimConfigFile = new File(configDirectory, "DimScores.txt");

        try {
            configDirectory.mkdir();

            if (!gearConfigFile.exists()) {
                gearConfigFile.createNewFile();
            }

            if (!dimConfigFile.exists()) {
                dimConfigFile.createNewFile();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            FileUtil.parseGearConfigFile();
            FileUtil.parseDimConfigFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void parseGearConfigFile() throws FileNotFoundException {
        Scanner scan = new Scanner(gearConfigFile);
        final Map<String, Integer> gearScores = GearScore.getGearScores();

        while (scan.hasNextLine()) {
            String line = scan.nextLine();

            if (line.isEmpty() || line.charAt(0) == '#') continue;

            String[] s = line.split("=");
            ItemStack itemStack = BGUtil.entryToItemStack(s[0]);
            int gearScore = Integer.parseInt(s[1]);

            gearScores.put(itemStack.getDisplayName(), gearScore);
        }
    }

    public static void parseDimConfigFile() throws FileNotFoundException {
        Scanner scan = new Scanner(dimConfigFile);
        final Map<Integer, Integer> dimScores = GearScore.getDimScores();

        while (scan.hasNextLine()) {
            String line = scan.nextLine();

            if (line.isEmpty() || line.charAt(0) == '#') continue;

            String[] dimInfo = line.split("=");

            int dimID = Integer.parseInt(dimInfo[0].trim());
            int dimScore = Integer.parseInt(dimInfo[1].trim());

            dimScores.put(dimID, dimScore);
        }
    }

    public static void saveGearScoresToFile() throws IOException {
        PrintWriter pw = new PrintWriter(gearConfigFile);

        GearScore.getGearScoreList().forEach(pw::println);
        pw.flush();
        pw.close();
    }

    public static void saveDimThresholdsToFile() throws IOException {
        PrintWriter pw = new PrintWriter(dimConfigFile);

        GearScore.getDimScoreList().forEach(pw::println);
        pw.flush();
        pw.close();
    }
}
