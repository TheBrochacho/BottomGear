package com.github.matt159.bottomgear.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Scores {
    private static final Map<String, Integer> GEAR_SCORES = new HashMap<>();
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

    public Map<String, Integer> getGearScores() {
        return GEAR_SCORES;
    }

    public Map<Integer, Integer> getDimScores() {
        return DIM_SCORES;
    }

    public Map<UUID, Integer> getPlayerScores() { return PLAYER_SCORES; }

    public int calculateScore(ArrayList<String> equipment) {
        int ret = 0;
        StringBuilder output = new StringBuilder();
        for (String name : equipment) {
            Integer score = getInstance().GEAR_SCORES.get(name);

            if (score != null)
                ret += score;
            else
                output.append("\nCould not find item with unlocalized name: ").append(name);
        }

        if (!output.toString().isEmpty()) {
            System.out.println(output);
        }

        return ret;
    }
}
