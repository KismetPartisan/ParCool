package com.alrex.parcool.common.processor;

import com.alrex.parcool.common.capability.IRoll;
import com.alrex.parcool.common.capability.IStamina;
import com.alrex.parcool.common.network.StartRollMessage;
import com.alrex.parcool.common.network.SyncRollReadyMessage;
import com.alrex.parcool.utilities.PlayerUtils;
import com.alrex.parcool.utilities.VectorUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class RollLogic {
	private static Vec3d rollDirection = null;

	@SideOnly(Side.CLIENT)
	public static void rollStart() {
		EntityPlayerSP player = Minecraft.getInstance().player;
		if (player == null) return;

		IRoll roll = IRoll.get(player);
		if (roll == null) return;

		roll.setRollReady(false);
		roll.setRolling(true);

		Vec3d lookVec = player.getLookVec();
		Vec3d motionVec = PlayerUtils.getVelocity(player);
		lookVec = new Vec3d(lookVec.x, 0, lookVec.z).normalize();
		motionVec = new Vec3d(motionVec.x, 0, motionVec.z);
		double speed = motionVec.length();
		if (speed < 0.8) speed = 0.8;
		rollDirection = lookVec.add(motionVec.normalize()).normalize().scale(speed / 1.4);
	}

	@SubscribeEvent
	public static void onTick(TickEvent.PlayerTickEvent event) {
		if (event.phase != TickEvent.Phase.END) return;
		if (event.side != Side.CLIENT) return;
		EntityPlayer player = event.player;
		IStamina stamina = IStamina.get(player);
		IRoll roll = IRoll.get(player);
		if (stamina == null || roll == null) return;

		roll.updateRollingTime();

		if (!player.isUser()) return;

		boolean oldReady = roll.isRollReady();
		if (roll.isRollReady()) {
			roll.setRollReady(roll.canContinueRollReady(player));
			stamina.consume(roll.getStaminaConsumption());
		} else roll.setRollReady(roll.canRollReady(player));

		if (roll.isRollReady() != oldReady) {
			SyncRollReadyMessage.sync(player);
		}

		if (roll.isRolling()) {
			if (rollDirection == null) return;
			rollDirection.scale(0.7);
			Vec3d motion = PlayerUtils.getVelocity(player);
			PlayerUtils.setVelocity(player, new Vec3d(rollDirection.x, motion.y, rollDirection.z));
		}
		if (roll.getRollingTime() >= roll.getRollAnimateTime()) {
			roll.setRolling(false);
			rollDirection = null;
		}
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public static void onRender(RenderPlayerEvent event) {
		if (event.getEntityPlayer() != Minecraft.getInstance().player) return;

		if (rollDirection != null) event.getEntityPlayer().rotationYaw = (float) VectorUtil.toYawDegree(rollDirection);
	}

	@SubscribeEvent
	public static void onDamage(LivingDamageEvent event) {
		if (!(event.getEntityLiving() instanceof EntityPlayerMP) || !event.getSource().getDamageType().equals(DamageSource.FALL.getDamageType()))
			return;
		EntityPlayerMP player = (EntityPlayerMP) event.getEntityLiving();

		IRoll roll = IRoll.get(player);
		if (roll == null) return;

		if (roll.isRollReady()) {
			roll.setRollReady(false);
			StartRollMessage.send(player);
			float damage = event.getAmount();
			if (damage < 2) {
				event.setCanceled(true);
			} else {
				event.setAmount((damage - 2) / 2);
			}
		}
	}
}
