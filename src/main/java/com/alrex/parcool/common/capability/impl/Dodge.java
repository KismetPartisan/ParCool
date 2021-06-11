package com.alrex.parcool.common.capability.impl;

import com.alrex.parcool.ParCoolConfig;
import com.alrex.parcool.client.input.KeyBindings;
import com.alrex.parcool.client.input.KeyRecorder;
import com.alrex.parcool.common.capability.IDodge;
import com.alrex.parcool.common.capability.IStamina;
import com.alrex.parcool.utilities.PlayerUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class Dodge implements IDodge {
	private int dodgingTime = 0;
	private boolean dodging = false;
	private int coolTime = 0;
	private DodgeDirection direction = null;

	@SideOnly(Side.CLIENT)
	@Override
	public boolean canDodge(EntityPlayer player) {
		IStamina stamina = IStamina.get(player);
		if (stamina == null) return false;
		return coolTime <= 0 && player.collidedVertically && !PlayerUtils.isSneaking(player) && !stamina.isExhausted() && ParCoolConfig.client.canDodge && (
				KeyRecorder.keyBack.isDoubleTapped() ||
						KeyRecorder.keyLeft.isDoubleTapped() ||
						KeyRecorder.keyRight.isDoubleTapped() ||
						(ParCoolConfig.client.canFrontFlip &&
								(KeyBindings.getKeyForward().conflicts(KeyBindings.getKeyFrontFlip()) ?
										KeyRecorder.keyFrontFlip.isDoubleTapped() :
										KeyRecorder.keyFrontFlip.isPressed()))
		);
	}

	@Override
	public void setDirection(DodgeDirection direction) {
		this.direction = direction;
	}

	@SideOnly(Side.CLIENT)
	@Nullable
	@Override
	public Vec3d getAndSetDodgeDirection(EntityPlayer player) {
		Vec3d lookVec = player.getLookVec();
		lookVec = new Vec3d(lookVec.x, 0, lookVec.z).normalize();

		if (KeyBindings.getKeyBack().isKeyDown()) {
			direction = DodgeDirection.Back;
			return new Vec3d(-lookVec.x, 0, -lookVec.z);
		}
		if (KeyBindings.getKeyFrontFlip().isKeyDown()) {
			direction = DodgeDirection.Front;
			return lookVec;
		}
		if (KeyBindings.getKeyLeft().isKeyDown() && KeyBindings.getKeyRight().isKeyDown()) return null;
		Vec3d vecToRight = lookVec.rotateYaw((float) Math.PI / -2);
		if (KeyBindings.getKeyLeft().isKeyDown()) {
			direction = DodgeDirection.Left;
			return new Vec3d(-vecToRight.x, -vecToRight.y, -vecToRight.z);
		} else {
			direction = DodgeDirection.Right;
			return vecToRight;
		}
	}

	@Override
	public boolean isDodging() {
		return dodging;
	}

	@Override
	public void setDodging(boolean dodging) {
		this.dodging = dodging;
		if (dodging) coolTime = 10;
		else direction = null;
	}

	@Override
	public int getDodgingTime() {
		return dodgingTime;
	}

	@Nullable
	@Override
	public DodgeDirection getDirection() {
		return direction;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public boolean canContinueDodge(EntityPlayer player) {
		return dodging && !player.collidedVertically && !player.isInWater() && !player.isElytraFlying() && !player.abilities.isFlying && ParCoolConfig.client.canDodge;
	}

	@Override
	public void updateDodgingTime() {
		if (coolTime > 0) coolTime--;
		if (dodging) {
			dodgingTime++;
		} else {
			dodgingTime = 0;
		}
	}

	@Override
	public int getStaminaConsumption() {
		return 100;
	}
}
