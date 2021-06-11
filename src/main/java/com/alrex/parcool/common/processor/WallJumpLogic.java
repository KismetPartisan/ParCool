package com.alrex.parcool.common.processor;

import com.alrex.parcool.ParCool;
import com.alrex.parcool.common.capability.IStamina;
import com.alrex.parcool.common.capability.IWallJump;
import com.alrex.parcool.common.network.ResetFallDistanceMessage;
import com.alrex.parcool.utilities.PlayerUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;

public class WallJumpLogic {
	@SubscribeEvent
	public static void onTick(TickEvent.PlayerTickEvent event) {
		if (event.phase == TickEvent.Phase.END) return;
		if (event.side == Side.SERVER) return;
		EntityPlayer player = event.player;
		IWallJump wallJump = IWallJump.get(player);
		IStamina stamina = IStamina.get(player);
		if (wallJump == null || stamina == null) return;

		if (!player.isUser()) return;
		if (!ParCool.isActive()) return;

		if (wallJump.canWallJump(player)) {
			Vec3d jumpDirection = wallJump.getJumpDirection(player);
			if (jumpDirection == null) return;

			Vec3d direction = new Vec3d(jumpDirection.x, 1.4, jumpDirection.z).scale(wallJump.getJumpPower());
			Vec3d motion = PlayerUtils.getVelocity(player);

			stamina.consume(wallJump.getStaminaConsumption());
			PlayerUtils.setVelocity(player,
					new Vec3d(
							motion.x + direction.x,
							motion.y > direction.y ? motion.y + direction.y : direction.y,
							motion.z + direction.z
					)
			);
			ResetFallDistanceMessage.sync(player);
		}
	}
}
