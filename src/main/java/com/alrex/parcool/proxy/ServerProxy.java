package com.alrex.parcool.proxy;

import com.alrex.parcool.common.network.*;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.SERVER)
public class ServerProxy extends CommonProxy {
	@Override
	public void registerMessages(SimpleNetworkWrapper instance) {
		instance.registerMessage(
				ResetFallDistanceMessage.class,
				ResetFallDistanceMessage.class,
				0,
				Side.SERVER
		);
		instance.registerMessage(
				SetActionPossibilityMessage.class,
				SetActionPossibilityMessage.class,
				1,
				Side.CLIENT
		);
		instance.registerMessage(
				ShowActionPossibilityMessage.class,
				ShowActionPossibilityMessage.class,
				2,
				Side.CLIENT
		);
		instance.registerMessage(
				StartRollMessage.ServerHandler.class,
				StartRollMessage.class,
				3,
				Side.CLIENT
		);
		instance.registerMessage(
				SyncCatLeapMessage.ServerHandler.class,
				SyncCatLeapMessage.class,
				4,
				Side.CLIENT
		);
		instance.registerMessage(
				SyncCrawlMessage.ServerHandler.class,
				SyncCrawlMessage.class,
				5,
				Side.CLIENT
		);
		instance.registerMessage(
				SyncDodgeMessage.ServerHandler.class,
				SyncDodgeMessage.class,
				6,
				Side.CLIENT
		);
		instance.registerMessage(
				SyncFastRunningMessage.ServerHandler.class,
				SyncFastRunningMessage.class,
				7,
				Side.CLIENT
		);
		instance.registerMessage(
				SyncGrabCliffMessage.ServerHandler.class,
				SyncGrabCliffMessage.class,
				8,
				Side.CLIENT
		);
		instance.registerMessage(
				SyncRollReadyMessage.ServerHandler.class,
				SyncRollReadyMessage.class,
				9,
				Side.CLIENT
		);
		instance.registerMessage(
				SyncStaminaMessage.ServerHandler.class,
				SyncStaminaMessage.class,
				10,
				Side.CLIENT
		);
	}
}
