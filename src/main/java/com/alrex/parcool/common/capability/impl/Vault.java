package com.alrex.parcool.common.capability.impl;

import com.alrex.parcool.ParCoolConfig;
import com.alrex.parcool.common.capability.IFastRunning;
import com.alrex.parcool.common.capability.IVault;
import com.alrex.parcool.utilities.WorldUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Vault implements IVault {
	private int vaultingTime = 0;
	private boolean vaulting = false;

	@Override
	public int getVaultAnimateTime() {
		return 2;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public boolean canVault(EntityPlayer player) {
		IFastRunning fastRunning = IFastRunning.get(player);
		if (fastRunning == null) return false;

		Vec3d lookVec = player.getLookVec();
		lookVec = new Vec3d(lookVec.x, 0, lookVec.z).normalize();
		Vec3d wall = WorldUtil.getWall(player);
		if (wall == null) return false;
		return !vaulting && ParCoolConfig.client.canVault && fastRunning.isFastRunning() && fastRunning.getRunningTime() > 20 && player.collidedVertically && (wall.dotProduct(lookVec) / wall.length() / lookVec.length()) > 0.707106 /*check facing wall*/ && WorldUtil.getStep(player) != null && WorldUtil.getWallHeight(player) > 0.8;
	}

	@Override
	public int getVaultingTime() {
		return vaultingTime;
	}

	@Override
	public void setVaulting(boolean vaulting) {
		this.vaulting = vaulting;
		if (!vaulting) vaultingTime = 0;
	}

	@Override
	public boolean isVaulting() {
		return vaulting;
	}

	@Override
	public void updateVaultingTime() {
		if (vaulting) vaultingTime++;
		if (vaultingTime > getVaultAnimateTime()) {
			vaultingTime = 0;
			vaulting = false;
		}
	}
}
