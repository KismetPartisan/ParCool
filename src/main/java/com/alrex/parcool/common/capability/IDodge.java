package com.alrex.parcool.common.capability;

import com.alrex.parcool.common.capability.capabilities.Capabilities;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public interface IDodge {
	enum DodgeDirection {Left, Right, Back, Front}

	@SideOnly(Side.CLIENT)
	public boolean canDodge(EntityPlayer player);

	public void setDirection(DodgeDirection direction);

	@SideOnly(Side.CLIENT)
	@Nullable
	public Vec3d getAndSetDodgeDirection(EntityPlayer player);

	@SideOnly(Side.CLIENT)
	public boolean canContinueDodge(EntityPlayer player);

	public boolean isDodging();

	@Nullable
	public DodgeDirection getDirection();

	public void setDodging(boolean dodging);

	public int getDodgingTime();

	public void updateDodgingTime();

	public int getStaminaConsumption();

	public static IDodge get(EntityPlayer entity) {
		IDodge dodge = entity.getCapability(Capabilities.DODGE_CAPABILITY, null);
		return dodge;
	}
}
