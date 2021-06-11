package com.alrex.parcool.common.processor;

import com.alrex.parcool.ParCool;
import com.alrex.parcool.client.particle.ParticleProvider;
import com.alrex.parcool.common.capability.IStamina;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class StaminaLogic {
	static byte count = 0;

	@SubscribeEvent
	public static void onTick(TickEvent.PlayerTickEvent event) {
		if (event.phase != TickEvent.Phase.END) return;
		EntityPlayer player = event.player;
		if (!ParCool.isActive()) return;
		IStamina stamina = IStamina.get(player);
		if (stamina == null) return;
		if (stamina.isExhausted() && player.world.rand.nextInt(10) == 0 && player instanceof EntityPlayerSP) {
			ParticleProvider.spawnEffectSweat(player);
		}

		if (!player.isUser()) return;
		count++;
		if (count >= 20) {
			count = 0;

		}

		if (player.isCreative()) {
			stamina.setStamina(stamina.getMaxStamina());
			stamina.setExhausted(false);
			return;
		}
		if (stamina.getRecoveryCoolTime() <= 0) stamina.recover(stamina.getMaxStamina() / 100);
		stamina.updateRecoveryCoolTime();

		if (stamina.isExhausted()) {
			player.setSprinting(false);
		}
	}
}
