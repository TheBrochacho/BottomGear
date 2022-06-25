package com.github.matt159.bottomgear.data;

import baubles.api.IBauble;
import com.github.matt159.bottomgear.util.BGConfig;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.*;
import net.minecraft.world.WorldProvider;
import net.minecraftforge.common.DimensionManager;
import org.apache.commons.lang3.tuple.Triple;
import tconstruct.library.accessory.IAccessory;
import travellersgear.api.ITravellersGear;

import java.lang.reflect.Field;
import java.util.*;

public final class GearScore {
    private static final int DAMAGE_VALUE = Short.MAX_VALUE;

    private static final Map<String, Set<Integer>> EQUIPABLE_ITEMS = new HashMap<>();

    private static final Map<String, Integer> GEAR_SCORES = new HashMap<>();
    private static final Map<Integer, Integer> DIM_SCORES = new HashMap<>();
    private static final Map<UUID, Integer> PLAYER_SCORES = new HashMap<>();

    private GearScore() {}

    public static Map<String, Integer> getGearScores() {
        return GEAR_SCORES;
    }

    public static Map<Integer, Integer> getDimScores() {
        return DIM_SCORES;
    }

    public static Map<UUID, Integer> getPlayerScores() { return PLAYER_SCORES; }

    public static Map<String, Set<Integer>> getEquipableItems() {
        return EQUIPABLE_ITEMS;
    }

    public static int calculateScore(List<String> equipment) {
        int ret = 0;
        StringBuilder output = new StringBuilder();
        for (String name : equipment) {
            Integer score = GEAR_SCORES.get(name);

            if (score != null)
                ret += score;
            else
                output.append("\nCould not find item with name: ").append(name);
        }

        if (!output.toString().isEmpty()) {
            System.out.println(output);
        }

        return ret;
    }

    public static List<String> getGearScoreList() {
        Map<String, ArrayList<Triple<String, String, String>>> gearInfo = getAllGearInfo();

        List<String> gsList = new ArrayList<>();
        gearInfo.keySet().forEach(key -> {
            gsList.add(String.format("%s\n#%-38s#\n%s",
                    "########################################",
                    key,
                    "########################################"));
            gearInfo.get(key).stream()
                    .map(v -> String.format("#%s\n%s@%s=1\n", v.getLeft(), v.getMiddle(), v.getRight()))
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

    public static String getItemCategory(Item item) {
        String category = null;

        if (item instanceof ItemArmor) {
            String[] armorTypes = {"Helmets", "Chestplates", "Leggings", "Boots"};
            category = armorTypes[((ItemArmor) item).armorType];
        } else if (item instanceof ItemSword || item instanceof ItemBow) {
            category = "Weapons";
        } else if (BGConfig.isBaublesLoaded && item instanceof IBauble && ((IBauble) item).getBaubleType(new ItemStack(item)) != null) {
            category = "Baubles";
        } else if (BGConfig.isTravellersGearLoaded && item instanceof ITravellersGear) {
            category = "TravellersGear";
        } else if (BGConfig.isTinkersLoaded && item instanceof IAccessory) {
            category = "TinkersConstruct";
        }
        return category;
    }

    private static boolean addEquipableItem(String ui, int damageValue) {
        if (!EQUIPABLE_ITEMS.containsKey(ui)) {
            EQUIPABLE_ITEMS.put(ui, new HashSet<>());
        }

        return EQUIPABLE_ITEMS.get(ui).add(damageValue);
    }

    public static List<String> getDimScoreList() {
        List<String> dsList = new ArrayList<>();

        Hashtable<Integer, Class<? extends WorldProvider>> providers = null;
        try {
            Field f = DimensionManager.class.getDeclaredField("providers");
            f.setAccessible(true);

            providers = (Hashtable<Integer, Class<? extends WorldProvider>>) f.get(new DimensionManager());

        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        Integer[] dimIDs = DimensionManager.getStaticDimensionIDs();
        Arrays.stream(dimIDs).forEach(id -> System.out.println(id));

        for (Integer dimID : dimIDs) {
            String dimensionName = providers.get(dimID).getName();
            dsList.add(String.format("#%s\n%d=%d\n",
                    dimensionName,
                    dimID,
                    Integer.MAX_VALUE));
        }

        return dsList;
    }
}
