package com.alrex.parcool.common.capability.impl;

import com.alrex.parcool.ParCoolConfig;
import com.alrex.parcool.client.input.KeyBindings;
import com.alrex.parcool.common.capability.ICrawl;
import com.alrex.parcool.common.capability.IRoll;
import com.alrex.parcool.utilities.PlayerUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Roll implements IRoll {
	private boolean rollReady = false;
	private boolean rolling = false;
	private int rollingTime = 0;

	@SideOnly(Side.CLIENT)
	@Override
	public boolean canContinueRollReady(EntityPlayer player) {
		ICrawl crawl = ICrawl.get(player);
		if (crawl == null) return false;

		return rollReady && ParCoolConfig.client.canRoll && !crawl.isCrawling() && !crawl.isSliding() && KeyBindings.getKeyRoll().isKeyDown() && !rolling && !player.isInWater();
	}

	@SideOnly(Side.CLIENT)
	@Override
	public boolean canRollReady(EntityPlayer player) {
		ICrawl crawl = ICrawl.get(player);
		if (crawl == null) return false;
		return !player.collidedVertically && ParCoolConfig.client.canRoll && PlayerUtils.getVelocity(player).y < -0.25 && !crawl.isCrawling() && !crawl.isSliding() && KeyBindings.getKeyRoll().isKeyDown() && !player.isInWater();
	}

	@Override
	public boolean isRollReady() {
		return rollReady;
	}

	@Override
	public boolean isRolling() {
		return rolling;
	}

	@Override
	public void setRollReady(boolean rollReady) {
		this.rollReady = rollReady;
	}

	@Override
	public void setRolling(boolean rolling) {
		this.rolling = rolling;
		if (!rolling) rollingTime = 0;
	}

	@Override
	public void updateRollingTime() {
		if (rolling) {
			rollingTime++;
			if (rollingTime > getRollAnimateTime()) {
				setRolling(false);
			}
		} else rollingTime = 0;
	}

	@Override
	public int getRollingTime() {
		return rollingTime;
	}

	@Override
	public int getStaminaConsumption() {
		return 50;
	}

	@Override
	public int getRollAnimateTime() {
		return 10;
	}

}
