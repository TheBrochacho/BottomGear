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

public class GearScore {
    private static final int DAMAGE_VALUE = 16;

    private final Map<String, Integer> GEAR_SCORES = new HashMap<>();
    private final Map<Integer, Integer> DIM_SCORES = new HashMap<>();
    private final Map<UUID, Integer> PLAYER_SCORES = new HashMap<>();

    private static GearScore INSTANCE = null;

    private GearScore() {}

    public static GearScore getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new GearScore();
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

    public int calculateScore(List<String> equipment) {
        int ret = 0;
        StringBuilder output = new StringBuilder();
        for (String name : equipment) {
            Integer score = GEAR_SCORES.get(name);

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

    public List<String> getGearScoreList() {
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

    private Map<String, ArrayList<Triple<String, String, String>>> getAllGearInfo() {
        Map<String, ArrayList<Triple<String, String, String>>> gearNames = new HashMap<>();

        gearNames.put("Helmets", new ArrayList<>());
        gearNames.put("Chestplates", new ArrayList<>());
        gearNames.put("Leggings", new ArrayList<>());
        gearNames.put("Boots", new ArrayList<>());
        gearNames.put("Weapons", new ArrayList<>());

        if (BGConfig.isBaublesLoaded) {
            gearNames.put("Baubles", new ArrayList<>());
        }

        if (BGConfig.isTravellersGearLoaded) {
            gearNames.put("TravellersGear", new ArrayList<>());
        }

        if (BGConfig.isTinkersLoaded) {
            gearNames.put("TinkersConstruct", new ArrayList<>());
        }

        // This is about the hackiest thing I've ever conceived, but it works...
        Map<String, ItemStack> items = new HashMap<>();
        for (Item item : (Iterable<Item>) Item.itemRegistry) {
            String category = getItemCategory(item);

            if (category == null) { continue; }

            for (int i = 0; i < DAMAGE_VALUE; ++i) {
                try {
                    ItemStack itemStack = new ItemStack(item, 1, i);
                    String displayName = item.getItemStackDisplayName(itemStack);

                    if (!displayName.contains(".name") && !items.containsKey(displayName)) {
                        items.put(displayName, itemStack);

                        String uniqueName = null;
                        try {
                            uniqueName = GameRegistry.findUniqueIdentifierFor(item).toString();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        gearNames.get(category).add(Triple.of(displayName, uniqueName, Integer.toString(i)));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return gearNames;
    }

    public String getItemCategory(Item item) {
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

    public List<String> getDimScoreList() {
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
