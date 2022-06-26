package com.github.matt159.bottomgear.data;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class PlayerScore {
    private PlayerScore() {}

    private static final Map<UUID, Integer> PLAYER_SCORES = new HashMap<>();

    public static Integer get(UUID uuid) {
        return PLAYER_SCORES.get(uuid);
    }

    public static void set(UUID uuid, int value) {
        PLAYER_SCORES.put(uuid, value);
    }
}
