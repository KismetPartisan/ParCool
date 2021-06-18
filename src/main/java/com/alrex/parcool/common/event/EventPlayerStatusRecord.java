package com.alrex.parcool.common.event;

import com.alrex.parcool.client.utils.StateRecorder;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class EventPlayerStatusRecord {
	@SubscribeEvent
	public static void onRecord(TickEvent.PlayerTickEvent event) {
		if (event.phase == TickEvent.Phase.END) return;
		if (!event.player.isUser()) return;

		StateRecorder.INSTANCE.record(event.player);
	}
}
