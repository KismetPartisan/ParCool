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
				StartRollMessage.ClientHandler.class,
				StartRollMessage.class,
				3,
				Side.CLIENT
		);
		instance.registerMessage(
				SyncCatLeapMessage.ClientHandler.class,
				SyncCatLeapMessage.class,
				4,
				Side.SERVER
		);
		instance.registerMessage(
				SyncCrawlMessage.ClientHandler.class,
				SyncCrawlMessage.class,
				5,
				Side.SERVER
		);
		instance.registerMessage(
				SyncDodgeMessage.ClientHandler.class,
				SyncDodgeMessage.class,
				6,
				Side.SERVER
		);
		instance.registerMessage(
				SyncFastRunningMessage.ClientHandler.class,
				SyncFastRunningMessage.class,
				7,
				Side.SERVER
		);
		instance.registerMessage(
				SyncGrabCliffMessage.ClientHandler.class,
				SyncGrabCliffMessage.class,
				8,
				Side.SERVER
		);
		instance.registerMessage(
				SyncRollReadyMessage.ClientHandler.class,
				SyncRollReadyMessage.class,
				9,
				Side.SERVER
		);
		instance.registerMessage(
				SyncStaminaMessage.ClientHandler.class,
				SyncStaminaMessage.class,
				10,
				Side.SERVER
		);
	}

	@Override
	public void showParCoolGuideScreen(EntityPlayer playerIn) {
		if (playerIn.world.isRemote) {
			Minecraft.getInstance().displayGuiScreen(new ParCoolGuideScreen());
		}
	}
}
