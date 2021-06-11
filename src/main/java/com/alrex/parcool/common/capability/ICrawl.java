package com.alrex.parcool.common.capability;

import com.alrex.parcool.common.capability.capabilities.Capabilities;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface ICrawl {
	@SideOnly(Side.CLIENT)
	public boolean canCrawl(EntityPlayer player);

	@SideOnly(Side.CLIENT)
	public boolean canSliding(EntityPlayer player);

	public boolean isCrawling();

	public void setCrawling(boolean crawling);

	public boolean isSliding();

	public void setSliding(boolean sliding);

	//only in Client
	public void updateSlidingTime(EntityPlayer player);

	public static ICrawl get(EntityPlayer entity) {
		ICrawl crawl = entity.getCapability(Capabilities.CRAWL_CAPABILITY, null);
		return crawl;
	}
}
