package com.alrex.parcool.common.capability;

import com.alrex.parcool.common.capability.capabilities.Capabilities;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IFastRunning {
	@SideOnly(Side.CLIENT)
	public boolean canFastRunning(EntityPlayer player);

	public boolean isFastRunning();

	public void setFastRunning(boolean fastRunning);

	public void updateTime();

	public int getRunningTime();

	public int getNotRunningTime();

	public int getStaminaConsumption();

	public static IFastRunning get(EntityPlayer entity) {
		IFastRunning fastRunning = entity.getCapability(Capabilities.FAST_RUNNING_CAPABILITY, null);
		return fastRunning;
	}

}
