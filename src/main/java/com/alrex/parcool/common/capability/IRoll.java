package com.alrex.parcool.common.capability;

import com.alrex.parcool.common.capability.capabilities.Capabilities;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IRoll {
	@SideOnly(Side.CLIENT)
	public boolean canRollReady(EntityPlayer player);

	@SideOnly(Side.CLIENT)
	public boolean canContinueRollReady(EntityPlayer player);

	public boolean isRollReady();

	public boolean isRolling();

	public void setRollReady(boolean ready);

	public void setRolling(boolean rolling);

	public void updateRollingTime();

	public int getRollingTime();

	public int getStaminaConsumption();

	public int getRollAnimateTime();//Don't return 0;

	public static IRoll get(EntityPlayer entity) {
		IRoll roll = entity.getCapability(Capabilities.ROLL_CAPABILITY, null);
		return roll;
	}

}
