package com.github.matt159.bottomgear.util;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.ItemStack;

public class BGUtil {
    public static ItemStack entryToItemStack(String entry) {
        String[] itemInfo = entry.split("@");
        String[] itemName = itemInfo[0].split(":");
        int damageValue = Integer.parseInt(itemInfo[1]);

        return new ItemStack(GameRegistry.findItem(itemName[0], itemName[1]), 1 , damageValue);
    }
}
