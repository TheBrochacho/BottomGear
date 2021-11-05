package com.github.matt159.bottomgear.item;

import codechicken.nei.ItemList.AnyMultiItemFilter;
import codechicken.nei.ItemStackSet;
import codechicken.nei.SearchField;
import codechicken.nei.SubsetWidget;
import codechicken.nei.SubsetWidget.SubsetTag;
import codechicken.nei.api.ItemFilter;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lombok.val;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

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
        try {
            //Bunch of hacky bs
            Field f = SubsetWidget.class.getDeclaredField("root");
            f.setAccessible(true);
            SubsetTag tag = (SubsetTag) f.get(new SubsetWidget());

            if (p_77659_3_.isSneaking()) {
                printAllSubsetTags(tag);
            }
            else {
                printAllGear(tag);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return p_77659_1_;
    }

    public void printAllSubsetTags(SubsetTag tag) {
        System.out.println(tag.fullname);

        for (SubsetTag tag1 : tag.children.values()) {
            printAllSubsetTags(tag1);
        }
    }

    public void printAllGear(SubsetTag tag) {

        Map<String, ArrayList<String>> unlocalizedGearNames = new HashMap<>();

        unlocalizedGearNames.put("helmets", new ArrayList<>());
        ItemStackSet helmets = null;

        unlocalizedGearNames.put("chestplates", new ArrayList<>());
        ItemStackSet chestplates = null;

        unlocalizedGearNames.put("leggings", new ArrayList<>());
        ItemStackSet leggings = null;

        unlocalizedGearNames.put("boots", new ArrayList<>());
        ItemStackSet boots = null;

        unlocalizedGearNames.put("weapons", new ArrayList<>());
        ItemStackSet weapons = null;

        try {
            helmets = (ItemStackSet) SubsetWidget.getTag("Items.Armor.Helmets").filter;
            chestplates = (ItemStackSet) SubsetWidget.getTag("Items.Armor.Chestplates").filter;
            leggings = (ItemStackSet) SubsetWidget.getTag("Items.Armor.Leggings").filter;
            boots = (ItemStackSet) SubsetWidget.getTag("Items.Armor.Boots").filter;

            for (ItemStack item : helmets.keys()) {
                unlocalizedGearNames.get("helmets").add(item.getUnlocalizedName());
            }

            for (ItemStack item : chestplates.keys()) {
                unlocalizedGearNames.get("chestplates").add(item.getUnlocalizedName());
            }

            for (ItemStack item : leggings.keys()) {
                unlocalizedGearNames.get("leggings").add(item.getUnlocalizedName());
            }

            for (ItemStack item : boots.keys()) {
                unlocalizedGearNames.get("boots").add((item.getUnlocalizedName()));
            }

            Pattern p = SearchField.getPattern("weapon");
            List<SubsetTag> tags = new ArrayList<>();

            tag.search(tags, p);
            AnyMultiItemFilter weaponFilter = new AnyMultiItemFilter();

            for (SubsetTag tag1 : tags) {
                tag1.addFilters(weaponFilter.filters);
            }

            for (ItemFilter filter : weaponFilter.filters) {
                if (filter instanceof ItemStackSet) {
                    List<ItemStack> items = ((ItemStackSet) filter).keys();
                    for (ItemStack item : items) {
                        unlocalizedGearNames.get("weapons").add(item.getUnlocalizedName());
                    }
                }
            }

            for (String key : unlocalizedGearNames.keySet()) {
                System.out.println("Gear Type: " + key);
                for (String unlocalizedName : unlocalizedGearNames.get(key)) {
                    System.out.println(unlocalizedName);
                }
                System.out.println();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
