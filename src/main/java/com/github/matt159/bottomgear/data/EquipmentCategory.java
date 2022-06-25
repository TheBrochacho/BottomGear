package com.github.matt159.bottomgear.data;

import baubles.api.IBauble;
import com.github.matt159.bottomgear.util.BGConfig;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemSword;
import tconstruct.library.accessory.IAccessory;
import travellersgear.api.ITravellersGear;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public final class EquipmentCategory {
    private static final Map<Class<?>, CategoryCheck> CATEGORIES = new HashMap<>();

    private EquipmentCategory() {}

    public static Set<Class<?>> categories() {
        return CATEGORIES.keySet();
    }

    public static void addCategory(Class<?> category, CategoryCheck check) {
        if (!CATEGORIES.containsKey(category)){
            CATEGORIES.put(category, check);
        }
    }

    public static String getItemCategory(Item item) {
        try {
            Map.Entry<Class<?>, CategoryCheck> category = CATEGORIES.entrySet().stream()
                    .filter(entry -> entry.getValue().isInCategory(entry.getKey(), item.getClass()))
                    .findFirst()
                    .orElse(null);

            if (category != null) {
                return category.getValue().getCategoryName(item);
            }
        } catch (Exception ignored) {}
        return null;
    }

    public static void init() {
        addCategory(ItemArmor.class, new CategoryCheck() {
            private final String[] armorTypes = {"Helmets", "Chestplates", "Leggings", "Boots"};

            @Override
            public String getCategoryName(Item item) {
                return armorTypes[((ItemArmor) item).armorType];
            }
        });

        addCategory(ItemSword.class, new CategoryCheck() {
            @Override
            String getCategoryName(Item item) {
                return "Sword";
            }
        });

        addCategory(ItemBow.class, new CategoryCheck() {
            @Override
            String getCategoryName(Item item) {
                return "Bow";
            }
        });

        if (BGConfig.isBaublesLoaded) {
            addCategory(IBauble.class, new CategoryCheck() {
                @Override
                String getCategoryName(Item item) {
                    return "Baubles";
                }
            });
        }

        if (BGConfig.isTravellersGearLoaded) {
            addCategory(ITravellersGear.class, new CategoryCheck() {
                @Override
                String getCategoryName(Item item) {
                    return "TravellersGear";
                }
            });
        }

        if (BGConfig.isTinkersLoaded) {
            addCategory(IAccessory.class, new CategoryCheck() {
                @Override
                String getCategoryName(Item item) {
                    return "TinkersConstruct";
                }
            });
        }
    }

    public static abstract class CategoryCheck {
        boolean isInCategory(Class<?> itemA, Class<?> itemB) {
            return itemA.isAssignableFrom(itemB);
        }

        abstract String getCategoryName(Item item);
    }
}
