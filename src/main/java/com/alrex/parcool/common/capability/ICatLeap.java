package com.alrex.parcool.common.capability;

import com.alrex.parcool.common.capability.capabilities.Capabilities;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public interface ICatLeap {
	@SideOnly(Side.CLIENT)
	public boolean canCatLeap(EntityPlayer player);

	@SideOnly(Side.CLIENT)
	public boolean canReadyLeap(EntityPlayer player);

	@SideOnly(Side.CLIENT)
	public double getBoostValue(EntityPlayer player);

	public boolean isLeaping();

	public void setLeaping(boolean leaping);

	public boolean isReady();

	public void setReady(boolean ready);

	public void updateReadyTime();

	public int getReadyTime();

	public int getStaminaConsumption();

	@Nullable
	public static ICatLeap get(EntityPlayer entity) {
		ICatLeap catLeap = entity.getCapability(Capabilities.CAT_LEAP_CAPABILITY, null);
		return catLeap;
	}
}
