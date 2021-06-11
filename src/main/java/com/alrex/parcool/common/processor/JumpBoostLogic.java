package com.alrex.parcool.common.processor;

import com.alrex.parcool.ParCool;
import com.alrex.parcool.common.capability.ICatLeap;
import com.alrex.parcool.common.capability.ICrawl;
import com.alrex.parcool.common.capability.IStamina;
import com.alrex.parcool.common.network.SyncCatLeapMessage;
import com.alrex.parcool.utilities.PlayerUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class JumpBoostLogic {
	@SubscribeEvent
	public static void onTick(TickEvent.PlayerTickEvent event) {
		if (event.side == Side.SERVER || event.phase != TickEvent.Phase.START) return;
		EntityPlayer player = event.player;
		ICatLeap catLeap = ICatLeap.get(player);
		IStamina stamina = IStamina.get(player);
		if (catLeap == null || stamina == null) return;

		if (!player.isUser()) return;


		catLeap.updateReadyTime();

		boolean oldLeaping = catLeap.isLeaping();
		if (catLeap.canCatLeap(player)) {
			Vec3d motionVec = PlayerUtils.getVelocity(player);
			Vec3d vec = new Vec3d(motionVec.x, 0, motionVec.z).normalize();
			PlayerUtils.setVelocity(player, new Vec3d(vec.x, catLeap.getBoostValue(player), vec.z));
			stamina.consume(catLeap.getStaminaConsumption());
			catLeap.setLeaping(true);
			catLeap.setReady(false);
		} else if (catLeap.isLeaping() && (player.collidedHorizontally || player.collidedVertically || player.isInWater())) {
			catLeap.setLeaping(false);
		}
		catLeap.setReady(catLeap.canReadyLeap(player));
		if (oldLeaping != catLeap.isLeaping()) SyncCatLeapMessage.sync(player);
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public static void onLivingJump(LivingEvent.LivingJumpEvent event) {
		Entity entity = event.getEntity();
		if (!(entity instanceof EntityPlayer)) return;
		EntityPlayer EntityPlayer = (EntityPlayer) entity;

		EntityPlayerSP player = Minecraft.getInstance().player;
		if (player != entity) return;
		if (!ParCool.isActive()) return;

		ICrawl crawl = ICrawl.get(player);
		IStamina stamina = IStamina.get(player);
		if (crawl == null || stamina == null) return;

		if (stamina.isExhausted()) {
			Vec3d vec = PlayerUtils.getVelocity(player);
			PlayerUtils.setVelocity(player, new Vec3d(vec.x, 0.3, vec.z));
			return;
		}
		if (crawl.isSliding()) {
			Vec3d vec = PlayerUtils.getVelocity(player);
			PlayerUtils.setVelocity(player, new Vec3d(vec.x, 0, vec.z));
		}
	}
}
