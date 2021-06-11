package com.alrex.parcool.common.processor;

import com.alrex.parcool.ParCool;
import com.alrex.parcool.common.capability.IDodge;
import com.alrex.parcool.common.capability.IStamina;
import com.alrex.parcool.common.network.SyncDodgeMessage;
import com.alrex.parcool.utilities.PlayerUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;

public class DodgeLogic {
	@SubscribeEvent
	public static void onTick(TickEvent.PlayerTickEvent event) {
		if (event.phase != TickEvent.Phase.START) return;
		EntityPlayer player = event.player;
		IStamina stamina = IStamina.get(player);
		IDodge dodge = IDodge.get(player);
		if (dodge == null || stamina == null) return;

		dodge.updateDodgingTime();
		if (event.side == Side.SERVER) return;

		if (!player.isUser() || !ParCool.isActive()) return;

		boolean start = dodge.canDodge(player);
		boolean oldDodging = dodge.isDodging();
		dodge.setDodging(start || (dodge.isDodging() && dodge.canContinueDodge(player)));
		if (start) {
			Vec3d vec = dodge.getAndSetDodgeDirection(player);
			if (vec != null) {
				vec = vec.scale(0.57);
				IDodge.DodgeDirection direction = dodge.getDirection();
				if (direction == IDodge.DodgeDirection.Back || direction == IDodge.DodgeDirection.Front) {
					PlayerUtils.setVelocity(player, new Vec3d(vec.x, 0.4, vec.z));
				} else {
					PlayerUtils.setVelocity(player, new Vec3d(vec.x, 0.23, vec.z));
				}
				stamina.consume(dodge.getStaminaConsumption());
			}
		}
		if (oldDodging != dodge.isDodging()) {
			SyncDodgeMessage.sync(player);
		}
	}
}
