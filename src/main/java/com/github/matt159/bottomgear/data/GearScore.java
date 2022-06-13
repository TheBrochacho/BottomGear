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
        Map<String, ArrayList<Triple<String, String, String>>> GearNames = new HashMap<>();

        GearNames.put("Helmets", new ArrayList<>());
        GearNames.put("Chestplates", new ArrayList<>());
        GearNames.put("Leggings", new ArrayList<>());
        GearNames.put("Boots", new ArrayList<>());
        GearNames.put("Weapons", new ArrayList<>());

        if (BGConfig.isBaublesLoaded) {
            GearNames.put("Baubles", new ArrayList<>());
        }

        if (BGConfig.isTravellersGearLoaded) {
            GearNames.put("TravellersGear", new ArrayList<>());
        }

        if (BGConfig.isTinkersLoaded) {
            GearNames.put("TinkersConstruct", new ArrayList<>());
        }

        ArrayList<ItemStack> items = new ArrayList<>();
        for (Item item : (Iterable<Item>) Item.itemRegistry) {
            item.getSubItems(item, null, items);
        }

        items.forEach(itemstack -> {
            Item item = itemstack.getItem();
            String key = null;

            String uniqueName = null;
            String itemID = Integer.toString(item.getDamage(itemstack));

            if (item instanceof ItemArmor) {
                String[] armorTypes = { "Helmets", "Chestplates", "Leggings", "Boots" };
                key = armorTypes[((ItemArmor) item).armorType];
            }
            else if (item instanceof ItemSword || item instanceof ItemBow) {
                key = "Weapons";
            }
            else if (BGConfig.isBaublesLoaded && item instanceof IBauble && ((IBauble) item).getBaubleType(itemstack) != null) {
                key = "Baubles";
            }
            else if (BGConfig.isTravellersGearLoaded && item instanceof ITravellersGear) {
                key = "TravellersGear";
            }
            else if (BGConfig.isTinkersLoaded && item instanceof IAccessory) {
                key = "TinkersConstruct";
            }

            if (key != null) {
                try {
                    uniqueName = GameRegistry.findUniqueIdentifierFor(item).toString();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                GearNames.get(key).add(Triple.of(item.getItemStackDisplayName(itemstack), uniqueName, itemID));
            }
        });

        return GearNames;
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

        for (Integer dimID : DimensionManager.getStaticDimensionIDs()) {
            try {
                String dimensionName = providers.get(dimID).newInstance().getDimensionName();
                dsList.add(String.format("#%s\n%d=%d\n",
                        dimensionName,
                        dimID,
                        Integer.MAX_VALUE));
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return dsList;
    }
}
