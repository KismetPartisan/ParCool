package com.alrex.parcool.common.item;

import com.alrex.parcool.common.item.items.ParCoolGuideItem;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class ParCoolItemGroup extends CreativeTabs {
	public static final ParCoolItemGroup INSTANCE = new ParCoolItemGroup();

	public ParCoolItemGroup() {
		super("parcool");
	}

	@Override
	public ItemStack createIcon() {
		return new ItemStack(ParCoolGuideItem.INSTANCE);
	}

	@Override
	public ItemStack getIcon() {
		return new ItemStack(ParCoolGuideItem.INSTANCE);
	}

	@Override
	public String getTranslationKey() {
		return "itemGroup.parcool";
	}
}
