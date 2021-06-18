package com.alrex.parcool.common.capability.impl;

import com.alrex.parcool.ParCoolConfig;
import com.alrex.parcool.client.input.KeyRecorder;
import com.alrex.parcool.client.utils.StateRecorder;
import com.alrex.parcool.common.capability.IGrabCliff;
import com.alrex.parcool.common.capability.IStamina;
import com.alrex.parcool.common.capability.IWallJump;
import com.alrex.parcool.utilities.WorldUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class WallJump implements IWallJump {
	@Override
	public double getJumpPower() {
		return 0.3;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public boolean canWallJump(EntityPlayer player) {
		IStamina stamina = IStamina.get(player);
		IGrabCliff grabCliff = IGrabCliff.get(player);
		if (stamina == null || grabCliff == null) return false;

		return !stamina.isExhausted() && ParCoolConfig.client.canWallJump && StateRecorder.INSTANCE.tickNotLanding > 4 && !player.onGround && !player.isInWater() && !player.isElytraFlying() && !player.abilities.isFlying && !grabCliff.isGrabbing() && KeyRecorder.keyJumpState.isPressed() && WorldUtil.getWall(player) != null;
	}

	@SideOnly(Side.CLIENT)
	@Override
	@Nullable
	public Vec3d getJumpDirection(EntityPlayer player) {
		Vec3d wall = WorldUtil.getWall(player);
		if (wall == null) return null;

		Vec3d lookVec = player.getLookVec();
		Vec3d vec = new Vec3d(lookVec.x, 0, lookVec.z).normalize();

		Vec3d value;

		if (wall.dotProduct(vec) > 0) {//To Wall
			double dot = new Vec3d(-vec.x, 0, -vec.z).dotProduct(wall);
			value = vec.add(wall.scale(2 * dot / wall.length())); // Perfect.
		} else {//back on Wall
			value = vec;
		}

		return value.normalize().add(wall.scale(-0.7));
	}

	@Override
	public int getStaminaConsumption() {
		return 200;
	}
}
