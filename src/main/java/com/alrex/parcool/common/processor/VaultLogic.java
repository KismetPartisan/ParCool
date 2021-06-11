package com.alrex.parcool.common.processor;

import com.alrex.parcool.ParCool;
import com.alrex.parcool.common.capability.IVault;
import com.alrex.parcool.utilities.PlayerUtils;
import com.alrex.parcool.utilities.WorldUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;

public class VaultLogic {
	//only in Client
	private static double wallHeight = 0;
	//only in Client
	private static Vec3d stepDirection = null;

	@SubscribeEvent
	public static void onTick(TickEvent.PlayerTickEvent event) {
		if (event.phase != TickEvent.Phase.START || event.side == Side.SERVER) return;

		EntityPlayer player = event.player;
		IVault vault = IVault.get(player);
		if (vault == null) return;

		vault.updateVaultingTime();


		if (!player.isUser()) return;
		if (!ParCool.isActive()) return;

		if (!vault.isVaulting() && vault.canVault(player)) {
			vault.setVaulting(true);
			stepDirection = WorldUtil.getStep(event.player);
			wallHeight = WorldUtil.getWallHeight(event.player);
		}
		if (vault.isVaulting()) {
			PlayerUtils.setVelocity(player, new Vec3d(stepDirection.x / 10, (wallHeight + 0.05) / vault.getVaultAnimateTime(), stepDirection.z / 10));
		}
		if (vault.getVaultingTime() >= vault.getVaultAnimateTime()) {
			vault.setVaulting(false);
			stepDirection = stepDirection.normalize();
			PlayerUtils.setVelocity(player, new Vec3d(stepDirection.x * 0.45, 0.15, stepDirection.z * 0.45));
		}
	}
}
