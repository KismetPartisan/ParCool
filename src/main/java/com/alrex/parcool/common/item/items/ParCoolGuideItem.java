package com.alrex.parcool.common.item.items;

import com.alrex.parcool.ParCool;
import com.alrex.parcool.common.item.ParCoolItemGroup;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class ParCoolGuideItem extends Item {
	public static final ResourceLocation RESOURCE_LOCATION = new ResourceLocation(ParCool.MOD_ID, "parcool_guide");
	public static final ParCoolGuideItem INSTANCE = new ParCoolGuideItem();

	private ParCoolGuideItem() {
		super();
		maxStackSize = 1;
		setRegistryName(RESOURCE_LOCATION);
	}

	@Nullable
	@Override
	public CreativeTabs getGroup() {
		return ParCoolItemGroup.INSTANCE;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		ParCool.PROXY.showParCoolGuideScreen(playerIn);
		return ActionResult.newResult(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
	}

}
