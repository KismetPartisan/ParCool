package com.alrex.parcool.proxy;

import com.alrex.parcool.client.gui.ParCoolGuideScreen;
import com.alrex.parcool.common.network.*;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy {
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
				StartRollMessage::handleClient,
				StartRollMessage.class,
				3,
				Side.CLIENT
		);
		instance.registerMessage(
				SyncCatLeapMessage::handleClient,
				SyncCatLeapMessage.class,
				4,
				Side.CLIENT
		);
		instance.registerMessage(
				SyncCrawlMessage::handleClient,
				SyncCrawlMessage.class,
				5,
				Side.CLIENT
		);
		instance.registerMessage(
				SyncDodgeMessage::handleClient,
				SyncDodgeMessage.class,
				6,
				Side.CLIENT
		);
		instance.registerMessage(
				SyncFastRunningMessage::handleClient,
				SyncFastRunningMessage.class,
				7,
				Side.CLIENT
		);
		instance.registerMessage(
				SyncGrabCliffMessage::handleClient,
				SyncGrabCliffMessage.class,
				8,
				Side.CLIENT
		);
		instance.registerMessage(
				SyncRollReadyMessage::handleClient,
				SyncRollReadyMessage.class,
				9,
				Side.CLIENT
		);
		instance.registerMessage(
				SyncStaminaMessage::handleClient,
				SyncStaminaMessage.class,
				10,
				Side.CLIENT
		);
	}

	@Override
	public void showParCoolGuideScreen(EntityPlayer playerIn) {
		if (playerIn.world.isRemote) {
			Minecraft.getInstance().displayGuiScreen(new ParCoolGuideScreen());
		}
	}
}
