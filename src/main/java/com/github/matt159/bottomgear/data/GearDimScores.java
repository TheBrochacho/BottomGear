package com.github.matt159.bottomgear.data;

import net.minecraft.item.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class GearDimScores {
    private static final Map<ItemStack, Integer> GEAR_SCORES = new HashMap<>();
    private static final Map<Integer, Integer> DIM_SCORES = new HashMap<>();

    private static GearDimScores INSTANCE = null;

    private GearDimScores() {}

    public static GearDimScores getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new GearDimScores();
        }

        return INSTANCE;
    }

    public Map<ItemStack, Integer> getGearScores() {
        return GEAR_SCORES;
    }

    public Map<Integer, Integer> getDimScores() {
        return DIM_SCORES;
    }
}
