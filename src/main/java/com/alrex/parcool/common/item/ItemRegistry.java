package com.alrex.parcool.common.item;

import com.alrex.parcool.common.item.items.ParCoolGuideItem;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ItemRegistry {
	@SubscribeEvent
	public static void register(RegistryEvent.Register<Item> event) {
		event.getRegistry().register(ParCoolGuideItem.INSTANCE);
	}
}
