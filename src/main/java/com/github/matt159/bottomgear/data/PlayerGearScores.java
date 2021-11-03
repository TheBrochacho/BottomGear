package com.github.matt159.bottomgear.data;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerGearScores {
    private static final Map<UUID, Integer> gearScores = new HashMap<>();

    public static Map<UUID, Integer> getInstance() {
        return gearScores;
    }
}
