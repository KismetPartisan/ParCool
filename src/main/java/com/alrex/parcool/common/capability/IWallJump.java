package com.alrex.parcool.common.capability;

import com.alrex.parcool.common.capability.capabilities.Capabilities;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IWallJump {
	public double getJumpPower();

	@SideOnly(Side.CLIENT)
	public boolean canWallJump(EntityPlayer player);

	@SideOnly(Side.CLIENT)
	public Vec3d getJumpDirection(EntityPlayer player);

	public int getStaminaConsumption();

	public static IWallJump get(EntityPlayer entity) {
		IWallJump wallJump = entity.getCapability(Capabilities.WALL_JUMP_CAPABILITY, null);
		return wallJump;
	}

}
