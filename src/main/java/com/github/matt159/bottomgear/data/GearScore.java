package com.github.matt159.bottomgear.data;

import com.github.matt159.bottomgear.util.BGUtil;
import net.minecraft.item.*;
import net.minecraft.world.WorldProvider;
import net.minecraftforge.common.DimensionManager;
import org.apache.commons.lang3.tuple.Triple;

import java.lang.reflect.Field;
import java.util.*;

public final class GearScore {
    private GearScore() {}

    private static final Map<String, Integer> GEAR_SCORES = new TreeMap<>();
    private static final Map<Integer, Integer> DIM_SCORES = new HashMap<>();
    private static final Map<UUID, Integer> PLAYER_SCORES = new HashMap<>();

    public static Map<String, Integer> getGearScores() {
        return GEAR_SCORES;
    }

    public static Map<Integer, Integer> getDimScores() {
        return DIM_SCORES;
    }

    public static Map<UUID, Integer> getPlayerScores() {
        return PLAYER_SCORES;
    }

    public static int calculateScore(List<String> equipment) {
        int ret = 0;
        StringBuilder output = new StringBuilder();
        for (String name : equipment) {
            if (GEAR_SCORES.containsKey(name)) {
                ret += GEAR_SCORES.get(name);
            } else {
//                output.append("\nCould not find item with name: ").append(name);
            }
        }

        if (!output.toString().isEmpty()) {
            System.out.println(output);
        }

        return ret;
    }

    public static List<String> getGearScoreList() {
        Map<String, ArrayList<Triple<String, String, Integer>>> gearInfo = getAllGearInfo();

        List<String> gsList = new ArrayList<>();
        gearInfo.forEach((key, value) -> {
            gsList.add(String.format("%s\n#%-38s#\n%s",
                    "########################################",
                    key,
                    "########################################"
            ));
            value.stream()
                    .map(v -> String.format("#%s\n%s=%d\n", v.getLeft(), v.getMiddle(), v.getRight()))
                    .distinct()
                    .forEach(gsList::add);
        });

        return gsList;
    }

    private static Map<String, ArrayList<Triple<String, String, Integer>>> getAllGearInfo() {
        Map<String, ArrayList<Triple<String, String, Integer>>> gearNames = new HashMap<>();
        GEAR_SCORES.forEach((key, value) -> {
            ItemStack itemStack = BGUtil.entryToItemStack(key);
            String category = EquipmentCategory.getItemCategory(itemStack.getItem());

            if (!gearNames.containsKey(category)) {
                gearNames.put(category, new ArrayList<>());
            }

            gearNames.get(category)
                    .add(Triple.of(
                            itemStack.getDisplayName(),
                            key,
                            value
                    ));
        });

        return gearNames;
    }

    public static List<String> getDimScoreList() {
        List<String> dsList = new ArrayList<>();

        Hashtable<Integer, Class<? extends WorldProvider>> providers = null;
        try {
            Field f = DimensionManager.class.getDeclaredField("providers");
            f.setAccessible(true);

            providers = (Hashtable<Integer, Class<? extends WorldProvider>>) f.get(new DimensionManager());

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (providers != null) {
            Integer[] dimIDs = DimensionManager.getStaticDimensionIDs();

            for (Integer dimID : dimIDs) {
                String dimensionName = providers.get(dimID).getName();
                dsList.add(String.format("#%s\n%d=%d\n",
                        dimensionName,
                        dimID,
                        Integer.MAX_VALUE));
            }
        }

        return dsList;
    }
}
