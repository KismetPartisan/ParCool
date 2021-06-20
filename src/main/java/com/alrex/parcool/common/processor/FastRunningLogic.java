package com.alrex.parcool.common.processor;

import com.alrex.parcool.ParCool;
import com.alrex.parcool.ParCoolConfig;
import com.alrex.parcool.common.capability.IFastRunning;
import com.alrex.parcool.common.capability.IStamina;
import com.alrex.parcool.common.network.SyncFastRunningMessage;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;

import java.util.UUID;

public class FastRunningLogic {
	private static final String FAST_RUNNING_MODIFIER_NAME = "parCool.modifier.fastrunnning";
	private static final UUID FAST_RUNNING_MODIFIER_UUID = UUID.randomUUID();
	private static final AttributeModifier FAST_RUNNING_MODIFIER
			= new AttributeModifier(
			FAST_RUNNING_MODIFIER_UUID,
			FAST_RUNNING_MODIFIER_NAME,
			0.041,
			0
	);

	@SubscribeEvent
	public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
		if (event.side == Side.SERVER) return;
		EntityPlayer player = event.player;
		IFastRunning fastRunning = IFastRunning.get(player);
		IStamina stamina = IStamina.get(player);
		if (stamina == null || fastRunning == null) return;
		if (fastRunning.isFastRunning()) player.setSprinting(true);

		if (event.phase != TickEvent.Phase.START) return;
		fastRunning.updateTime();
		if (!player.isUser() || !ParCoolConfig.client.ParCoolActivation) return;

		IAttributeInstance attr = player.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED);

		if (!ParCool.isActive()) {
			if (attr.hasModifier(FAST_RUNNING_MODIFIER)) attr.removeModifier(FAST_RUNNING_MODIFIER);
			return;
		}

		boolean oldFastRunning = fastRunning.isFastRunning();
		fastRunning.setFastRunning(fastRunning.canFastRunning(player));

		if (fastRunning.isFastRunning() != oldFastRunning) SyncFastRunningMessage.sync(player);

		if (fastRunning.isFastRunning()) {
			if (!attr.hasModifier(FAST_RUNNING_MODIFIER)) attr.applyModifier(FAST_RUNNING_MODIFIER);
			stamina.consume(fastRunning.getStaminaConsumption());
		} else {
			if (attr.hasModifier(FAST_RUNNING_MODIFIER)) attr.removeModifier(FAST_RUNNING_MODIFIER);
		}
	}
}
