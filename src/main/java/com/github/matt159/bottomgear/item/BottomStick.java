package com.github.matt159.bottomgear.item;

import com.github.matt159.bottomgear.data.GearScore;
import com.github.matt159.bottomgear.data.PlayerScore;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;

import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

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
    public boolean onBlockDestroyed(ItemStack itemStack, World world, Block block, int p_150894_4_, int p_150894_5_, int p_150894_6_, EntityLivingBase entity) {
        return true;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
        player.addChatMessage(new ChatComponentText(String.format("Current Gear Score is: %d.",
                PlayerScore.get(player.getUniqueID()))));
        player.addChatMessage(new ChatComponentText(String.format("The threshold for this dimension is: %d",
                GearScore.getDimScores().get(world.provider.dimensionId))));

        return itemStack;
    }
}
