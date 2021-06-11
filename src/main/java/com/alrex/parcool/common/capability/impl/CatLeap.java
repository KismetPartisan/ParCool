package com.alrex.parcool.common.capability.impl;

import com.alrex.parcool.ParCoolConfig;
import com.alrex.parcool.client.input.KeyBindings;
import com.alrex.parcool.common.capability.ICatLeap;
import com.alrex.parcool.common.capability.IFastRunning;
import com.alrex.parcool.common.capability.IStamina;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class CatLeap implements ICatLeap {
	private boolean leaping = false;
	private boolean ready = false;
	private int readyTime = 0;

	@SideOnly(Side.CLIENT)
	@Override
	public boolean canCatLeap(EntityPlayer player) {
		IStamina stamina = IStamina.get(player);
		if (stamina == null) return false;
		return player.collidedVertically && ParCoolConfig.client.canCatLeap && !stamina.isExhausted() && ready && readyTime < 10 && !KeyBindings.getKeySneak().isKeyDown();
	}

	@SideOnly(Side.CLIENT)
	@Override
	public boolean canReadyLeap(EntityPlayer player) {
		IFastRunning fastRunning = IFastRunning.get(player);
		if (fastRunning == null) return false;
		return (fastRunning.getNotRunningTime() < 10 && KeyBindings.getKeySneak().isKeyDown()) || (ready && KeyBindings.getKeySneak().isKeyDown() && readyTime < 10);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public double getBoostValue(EntityPlayer player) {
		return 0.49;
	}

	@Override
	public boolean isLeaping() {
		return leaping;
	}

	@Override
	public void setLeaping(boolean leaping) {
		this.leaping = leaping;
	}

	@Override
	public int getReadyTime() {
		return readyTime;
	}

	@Override
	public boolean isReady() {
		return ready;
	}

	@Override
	public void setReady(boolean ready) {
		this.ready = ready;
	}

	@Override
	public void updateReadyTime() {
		if (ready) readyTime++;
		else readyTime = 0;
	}

	@Override
	public int getStaminaConsumption() {
		return 200;
	}
}
