package com.github.matt159.bottomgear.item;

import baubles.api.IBauble;
import com.github.matt159.bottomgear.util.BGConfig;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import tconstruct.library.accessory.IAccessory;
import travellersgear.api.ITravellersGear;
import travellersgear.common.items.ItemTravellersGear;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.github.matt159.bottomgear.BottomGear.MODID;

public class BottomStick extends Item {

    public BottomStick() {
        this.setUnlocalizedName("bottom_stick");
        this.setNoRepair();
        this.setMaxDamage(1);
        setTextureName(MODID + ":bottomStick");
    }

    @Override
    public boolean isDamageable() {
        return false;
    }

    @Override
    public boolean onBlockDestroyed(ItemStack p_150894_1_, World p_150894_2_, Block p_150894_3_, int p_150894_4_, int p_150894_5_, int p_150894_6_, EntityLivingBase p_150894_7_) {
        return true;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack p_77659_1_, World p_77659_2_, EntityPlayer p_77659_3_) {
        if (p_77659_3_.isSneaking())
            printAllDims();
        else
            printAllGearNames(getAllGearNames());

        return p_77659_1_;
    }

    public Map<String, ArrayList<String>> getAllGearNames() {
        Map<String, ArrayList<String>> unlocalizedGearNames = new HashMap<>();
        unlocalizedGearNames.put("helmets", new ArrayList<>());
        unlocalizedGearNames.put("chestplates", new ArrayList<>());
        unlocalizedGearNames.put("leggings", new ArrayList<>());
        unlocalizedGearNames.put("boots", new ArrayList<>());
        unlocalizedGearNames.put("weapons", new ArrayList<>());

        if (BGConfig.isBaublesLoaded) {
            unlocalizedGearNames.put("baubles", new ArrayList<>());
        }

        if (BGConfig.isTravellersGearLoaded) {
            unlocalizedGearNames.put("traveller's gear", new ArrayList<>());
        }

        if (BGConfig.isTinkersLoaded) {
            unlocalizedGearNames.put("tinkers", new ArrayList<>());
        }

        ArrayList<ItemStack> items = new ArrayList<>();
        for (Item item : (Iterable<Item>) Item.itemRegistry) {
            item.getSubItems(item, getCreativeTabs()[0], items);
        }

        items.forEach(itemstack -> {
            Item item = itemstack.getItem();
            if (item instanceof ItemArmor) {
                switch (((ItemArmor) item).armorType) {
                    case 0:
                        unlocalizedGearNames.get("helmets").add(item.getUnlocalizedName());
                        break;
                    case 1:
                        unlocalizedGearNames.get("chestplates").add(item.getUnlocalizedName());
                        break;
                    case 2:
                        unlocalizedGearNames.get("leggings").add(item.getUnlocalizedName());
                        break;
                    case 3:
                        unlocalizedGearNames.get("boots").add(item.getUnlocalizedName());
                        break;
                }
            }
            else if (item instanceof ItemSword || item instanceof ItemBow) {
                unlocalizedGearNames.get("weapons").add(item.getUnlocalizedName());
            }
            else if (BGConfig.isBaublesLoaded && item instanceof IBauble && ((IBauble) item).getBaubleType(itemstack) != null) {
                unlocalizedGearNames.get("baubles").add(item.getUnlocalizedName());
            }
            else if (BGConfig.isTravellersGearLoaded && item instanceof ITravellersGear) {
                String unlocalizedName = item.getUnlocalizedName() + "_" + ItemTravellersGear.subNames[itemstack.getItemDamage()];
                unlocalizedGearNames.get("traveller's gear").add(unlocalizedName);
            }
            else if (BGConfig.isTinkersLoaded && item instanceof IAccessory) {
                unlocalizedGearNames.get("tinkers").add(item.getUnlocalizedName());
            }
        });

        return unlocalizedGearNames;
    }

    public void printAllGearNames(Map<String, ArrayList<String>> unlocalizedGearNames) {
        StringBuilder output = new StringBuilder("\nGear Type: \n");
        for (String key : unlocalizedGearNames.keySet()) {
            output.append(key).append('\n');
            for (String unlocalizedName : unlocalizedGearNames.get(key)) {
                output.append(unlocalizedName).append('\n');
            }
            output.append('\n');
        }
        System.out.println(output);
    }

    public void printAllDims() {
        StringBuilder output = new StringBuilder("\nDimensions:\n");
        Integer[] dims = DimensionManager.getStaticDimensionIDs();
        for (Integer i : dims) {
            output.append(String.format("%3d - %s\n", i, DimensionManager.getProvider(i).getDimensionName()));
        }
        System.out.println(output);
    }
}
