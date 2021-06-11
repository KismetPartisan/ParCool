package com.alrex.parcool.common.capability;

import com.alrex.parcool.common.capability.capabilities.Capabilities;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IGrabCliff {
	@SideOnly(Side.CLIENT)
	public boolean canGrabCliff(EntityPlayer player);

	@SideOnly(Side.CLIENT)
	public boolean canJumpOnCliff(EntityPlayer player);

	public boolean isGrabbing();

	public void setGrabbing(boolean grabbing);

	public void updateTime();

	public int getGrabbingTime();

	public int getNotGrabbingTime();

	public int getStaminaConsumptionGrab();

	public int getStaminaConsumptionClimbUp();

	public static IGrabCliff get(EntityPlayer entity) {
		IGrabCliff grabCliff = entity.getCapability(Capabilities.GRAB_CLIFF_CAPABILITY, null);
		return grabCliff;
	}

}
