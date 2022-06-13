package com.github.matt159.bottomgear.item;

import baubles.api.IBauble;
import com.github.matt159.bottomgear.util.BGConfig;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;

import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import org.apache.commons.lang3.tuple.Triple;
import tconstruct.library.accessory.IAccessory;
import travellersgear.api.ITravellersGear;

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

    @SideOnly(Side.CLIENT)
    @Override
    public ItemStack onItemRightClick(ItemStack p_77659_1_, World p_77659_2_, EntityPlayer p_77659_3_) {
        if (p_77659_3_.capabilities.isCreativeMode) {
            if (p_77659_3_.isSneaking())
                printAllDims();
            else
                printAllGearNames(getAllGearNames());
        }
        else {
            //p_77659_3_.addChatMessage();
        }

        return p_77659_1_;
    }

    public Map<String, ArrayList<Triple<String, String, String>>> getAllGearNames() {
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
            item.getSubItems(item, getCreativeTabs()[0], items);
        }

        items.forEach(itemstack -> {
            Item item = itemstack.getItem();
            String key = null;

            String uniqueName = null;
            String itemID = Integer.toString(item.getDamage(itemstack));

            if (item instanceof ItemArmor) {
                switch (((ItemArmor) item).armorType) {
                    case 0:
                        key = "Helmets";
                        break;
                    case 1:
                        key = "Chestplates";
                        break;
                    case 2:
                        key = "Leggings";
                        break;
                    case 3:
                        key = "Boots";
                        break;
                }
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

                GearNames.get(key).add(Triple.of(item.getUnlocalizedName(itemstack), uniqueName, itemID));
            }
        });

        return GearNames;
    }

    public void printAllGearNames(Map<String, ArrayList<Triple<String, String, String>>> gearNames) {
        StringBuilder output = new StringBuilder("\n#Gear Type: \n");
        for (String key : gearNames.keySet()) {
            output.append('#' + key + '\n');
            for (Triple<String, String, String> value : gearNames.get(key)) {
                output.append(String.format("#%s\n%s@%s=1\n\n",    value.getLeft(),
                                                                    value.getMiddle(),
                                                                    value.getRight()));
            }
            output.append('\n');
        }
        System.out.println(output);
    }

    public void printAllDims() {
        StringBuilder output = new StringBuilder("\n#Dimensions:\n");
        Integer[] dims = DimensionManager.getStaticDimensionIDs();
        for (Integer i : dims) {
            output.append('#' + DimensionManager.getProvider(i).getDimensionName() + '\n');
            output.append(String.format("%2d=%d\n", i, Integer.MAX_VALUE));
        }
        System.out.println(output);
    }
}
