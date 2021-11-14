package com.github.matt159.bottomgear.data;

import net.minecraft.item.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Scores {
    private static final Map<ItemStack, Integer> GEAR_SCORES = new HashMap<>();
    private static final Map<Integer, Integer> DIM_SCORES = new HashMap<>();
    private static final Map<UUID, Integer> PLAYER_SCORES = new HashMap<>();

    private static Scores INSTANCE = null;

    private Scores() {}

    public static Scores getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Scores();
        }

        return INSTANCE;
    }

    public Map<ItemStack, Integer> getGearScores() {
        return GEAR_SCORES;
    }

    public Map<Integer, Integer> getDimScores() {
        return DIM_SCORES;
    }

    public Map<UUID, Integer> getPlayerScores() { return PLAYER_SCORES; }
}
