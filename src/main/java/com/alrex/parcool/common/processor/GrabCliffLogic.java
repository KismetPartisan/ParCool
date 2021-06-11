package com.alrex.parcool.common.processor;

import com.alrex.parcool.ParCool;
import com.alrex.parcool.common.capability.IGrabCliff;
import com.alrex.parcool.common.capability.IStamina;
import com.alrex.parcool.common.network.ResetFallDistanceMessage;
import com.alrex.parcool.common.network.SyncGrabCliffMessage;
import com.alrex.parcool.utilities.PlayerUtils;
import com.alrex.parcool.utilities.VectorUtil;
import com.alrex.parcool.utilities.WorldUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class GrabCliffLogic {
	@SubscribeEvent
	public static void onTick(TickEvent.PlayerTickEvent event) {
		if (event.phase != TickEvent.Phase.END) return;
		if (event.side == Side.SERVER) return;
		EntityPlayer player = event.player;
		IStamina stamina = IStamina.get(player);
		IGrabCliff grabCliff = IGrabCliff.get(player);
		if (stamina == null || grabCliff == null) return;

		grabCliff.updateTime();

		if (!player.isUser()) return;
		if (!ParCool.isActive()) return;

		boolean oldGrabbing = grabCliff.isGrabbing();
		grabCliff.setGrabbing(grabCliff.canGrabCliff(player));

		if (oldGrabbing != grabCliff.isGrabbing()) {
			SyncGrabCliffMessage.sync(player);
			ResetFallDistanceMessage.sync(player);
		}

		if (grabCliff.isGrabbing()) {
			Vec3d vec = PlayerUtils.getVelocity(player);
			PlayerUtils.setVelocity(player, new Vec3d(vec.x / 10, vec.y > 0.1 ? vec.y / 10 : 0, vec.z / 10));
			stamina.consume(grabCliff.getStaminaConsumptionGrab());
		}
		if (grabCliff.canJumpOnCliff(player)) {
			player.addVelocity(0, 0.6, 0);
			stamina.consume(grabCliff.getStaminaConsumptionClimbUp());
		}
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public static void onRender(TickEvent.RenderTickEvent event) {
		if (event.phase != TickEvent.Phase.END) return;
		EntityPlayerSP player = Minecraft.getInstance().player;
		if (player == null) return;
		if (!ParCool.isActive()) return;

		IGrabCliff grabCliff = IGrabCliff.get(player);
		if (grabCliff == null) return;

		if (grabCliff.isGrabbing()) {
			Vec3d wall = WorldUtil.getWall(player);
			Vec3d look = player.getLookVec();
			if (wall != null)
				player.rotationYaw = (float) VectorUtil.toYawDegree(wall.normalize().add(look.normalize()));
		}
	}
}
