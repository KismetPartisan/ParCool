package com.alrex.parcool.client.utils;

import net.minecraft.entity.player.EntityPlayer;

//record client player status
public class StateRecorder {
	public static final StateRecorder INSTANCE = new StateRecorder();

	private void StateRecorder() {
	}

	;

	public int tickNotLanding = 0;

	public void record(EntityPlayer player) {
		if (player.onGround) tickNotLanding = 0;
		tickNotLanding++;
	}
}
