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
				StartRollMessage::handleServer,
				StartRollMessage.class,
				3,
				Side.SERVER
		);
		instance.registerMessage(
				SyncCatLeapMessage::handleServer,
				SyncCatLeapMessage.class,
				4,
				Side.SERVER
		);
		instance.registerMessage(
				SyncCrawlMessage::handleServer,
				SyncCrawlMessage.class,
				5,
				Side.SERVER
		);
		instance.registerMessage(
				SyncDodgeMessage::handleServer,
				SyncDodgeMessage.class,
				6,
				Side.SERVER
		);
		instance.registerMessage(
				SyncFastRunningMessage::handleServer,
				SyncFastRunningMessage.class,
				7,
				Side.SERVER
		);
		instance.registerMessage(
				SyncGrabCliffMessage::handleServer,
				SyncGrabCliffMessage.class,
				8,
				Side.SERVER
		);
		instance.registerMessage(
				SyncRollReadyMessage::handleServer,
				SyncRollReadyMessage.class,
				9,
				Side.SERVER
		);
		instance.registerMessage(
				SyncStaminaMessage::handleServer,
				SyncStaminaMessage.class,
				10,
				Side.SERVER
		);
	}
}
