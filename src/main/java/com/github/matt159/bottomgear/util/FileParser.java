package com.github.matt159.bottomgear.util;

import com.github.matt159.bottomgear.data.GearDimScores;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import scala.Int;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Scanner;

public class FileParser {

    public static void parseGearConfigFile(final File gearConfigFile) throws FileNotFoundException {
        Scanner scan = new Scanner(gearConfigFile);
        final Map<ItemStack, Integer> gearScores = GearDimScores.getInstance().getGearScores();

        while (scan.hasNextLine()) {
            String line = scan.nextLine();

            if (line.isEmpty() || line.charAt(0) == '#') continue;

            String[] s = line.split("=");
            int gearScore = Integer.parseInt(s[1]);

            String[] itemInfo = s[0].split("@");
            String[] itemName = itemInfo[0].split(":");
            int damageValue = Integer.parseInt(itemInfo[1]);

            ItemStack item = new ItemStack(GameRegistry.findItem(itemName[0], itemName[1]), 0 , damageValue);

            gearScores.put(item, gearScore);
        }
    }

    public static void parseDimConfigFile(final File dimConfigFile) throws FileNotFoundException {

    }
}
