package com.alrex.parcool.common.capability.impl;

import com.alrex.parcool.ParCoolConfig;
import com.alrex.parcool.client.input.KeyBindings;
import com.alrex.parcool.common.capability.ICrawl;
import com.alrex.parcool.common.capability.IFastRunning;
import com.alrex.parcool.common.capability.IRoll;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Crawl implements ICrawl {
	private static final int maxSlidingTime = 15;

	private boolean crawling = false;
	private boolean sliding = false;
	private int slidingTime = -1;

	@Override
	public boolean isCrawling() {
		return crawling;
	}

	@Override
	public void setCrawling(boolean crawling) {
		this.crawling = crawling;
	}

	@Override
	public boolean isSliding() {
		return sliding;
	}

	@Override
	public void setSliding(boolean sliding) {
		this.sliding = sliding;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public boolean canCrawl(EntityPlayer player) {
		IRoll roll = IRoll.get(player);
		if (roll == null) return false;

		return KeyBindings.getKeyCrawl().isKeyDown() && ParCoolConfig.client.canCrawl && !roll.isRolling() && !player.isInWater() && player.collidedVertically;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public boolean canSliding(EntityPlayer player) {
		IFastRunning fastRunning = IFastRunning.get(player);
		IRoll roll = IRoll.get(player);
		if (fastRunning == null || roll == null) return false;

		if (!isSliding() && ParCoolConfig.client.canCrawl && fastRunning.isFastRunning() && !roll.isRollReady() && !roll.isRolling() && player.collidedVertically && KeyBindings.getKeyCrawl().isKeyDown() && slidingTime >= 0) {
			return true;
		}
		if (isSliding() && slidingTime <= maxSlidingTime) return true;
		return false;
	}

	@Override
	public void updateSlidingTime(EntityPlayer player) {
		if (slidingTime < 0 && KeyBindings.getKeyCrawl().isKeyDown()) return;

		if (isSliding()) slidingTime++;
		else slidingTime = 0;
		if ((slidingTime > maxSlidingTime && player.collidedVertically) || player.isInWater()) {
			slidingTime = -1;
			setSliding(false);
		}
	}
}
