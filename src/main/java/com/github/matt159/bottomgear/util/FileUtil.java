package com.github.matt159.bottomgear.util;

import com.github.matt159.bottomgear.data.GearScore;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Scanner;

import static com.github.matt159.bottomgear.BottomGear.dimConfigFile;
import static com.github.matt159.bottomgear.BottomGear.gearConfigFile;

public class FileUtil {

    public static void parseGearConfigFile(final File gearConfigFile) throws FileNotFoundException {
        Scanner scan = new Scanner(gearConfigFile);
        final Map<String, Integer> gearScores = GearScore.getGearScores();

        while (scan.hasNextLine()) {
            String line = scan.nextLine();

            if (line.isEmpty() || line.charAt(0) == '#') continue;

            String[] s = line.split("=");
            int gearScore = Integer.parseInt(s[1]);

            String[] s1 = s[0].split("@");
            String unlocalizedName = s1[0];
            int damageValue = Integer.parseInt(s1[1]);

            Item item = (Item) Item.itemRegistry.getObject(unlocalizedName);
            ItemStack itemStack = new ItemStack(item, 1, damageValue);

            gearScores.put(itemStack.getDisplayName(), gearScore);
        }
    }

    public static void parseDimConfigFile(final File dimConfigFile) throws FileNotFoundException {
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
        if (gearConfigFile.createNewFile()) {
            PrintWriter pw = new PrintWriter(gearConfigFile);

            GearScore.getGearScoreList().forEach(pw::println);
            pw.flush();
            pw.close();
        }
    }

    public static void saveDimThresholdsToFile() throws IOException {
        if (dimConfigFile.createNewFile()) {
            PrintWriter pw = new PrintWriter(dimConfigFile);

            GearScore.getDimScoreList().forEach(pw::println);
            pw.flush();
            pw.close();
        }
    }
}
