package com.alrex.parcool.proxy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;

public abstract class CommonProxy {
	public abstract void registerMessages(SimpleNetworkWrapper instance);

	public void showParCoolGuideScreen(EntityPlayer playerIn) {
	}

	;
}
