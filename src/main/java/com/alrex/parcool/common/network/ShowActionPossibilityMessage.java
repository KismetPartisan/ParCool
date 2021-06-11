package com.alrex.parcool.common.network;

import com.alrex.parcool.ParCool;
import com.alrex.parcool.ParCoolConfig;
import com.alrex.parcool.constants.ActionsEnum;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.nio.charset.StandardCharsets;

public class ShowActionPossibilityMessage implements IMessage, IMessageHandler<ShowActionPossibilityMessage, ShowActionPossibilityMessage> {
	ActionsEnum action = null;

	public void toBytes(ByteBuf packet) {
		packet.writeBoolean(action != null);
		if (action != null) {
			String name = action.name();
			packet.writeInt(name.length());
			packet.writeCharSequence(name, StandardCharsets.US_ASCII);
		}
	}

	public void fromBytes(ByteBuf packet) {
		ShowActionPossibilityMessage message = new ShowActionPossibilityMessage();
		try {
			if (packet.readBoolean())
				message.action = ActionsEnum.valueOf(packet.readCharSequence(packet.readInt(), StandardCharsets.US_ASCII).toString());
		} catch (IllegalArgumentException e) {
			message.action = null;
		}
	}

	public ShowActionPossibilityMessage onMessage(ShowActionPossibilityMessage message, MessageContext context) {
		Minecraft.getInstance().func_152344_a(() -> {
			EntityPlayerSP player = Minecraft.getInstance().player;
			if (player == null) return;
			player.sendStatusMessage(new TextComponentString(getText(action)), false);
		});
		return null;
	}

	//only in Client
	private static String getText(ActionsEnum action) {
		ParCoolConfig.Client c = ParCoolConfig.client;
		if (action != null) switch (action) {
			case CatLeap:
				return action.name() + " : " + c.canCatLeap;
			case Crawl:
				return action.name() + " : " + c.canCrawl;
			case Dodge:
				return action.name() + " : " + c.canDodge;
			case FastRunning:
				return action.name() + " : " + c.canFastRunning;
			case GrabCliff:
				return action.name() + " : " + c.canGrabCliff;
			case Roll:
				return action.name() + " : " + c.canRoll;
			case Vault:
				return action.name() + " : " + c.canVault;
			case WallJump:
				return action.name() + " : " + c.canWallJump;
		}
		StringBuilder builder = new StringBuilder();
		builder
				.append(ActionsEnum.CatLeap.name()).append(" : ").append(c.canCatLeap).append('\n')
				.append(ActionsEnum.Crawl.name()).append(" : ").append(c.canCrawl).append('\n')
				.append(ActionsEnum.Dodge.name()).append(" : ").append(c.canDodge).append('\n')
				.append(ActionsEnum.FastRunning.name()).append(" : ").append(c.canFastRunning).append('\n')
				.append(ActionsEnum.GrabCliff.name()).append(" : ").append(c.canGrabCliff).append('\n')
				.append(ActionsEnum.Roll.name()).append(" : ").append(c.canRoll).append('\n')
				.append(ActionsEnum.Vault.name()).append(" : ").append(c.canVault).append('\n')
				.append(ActionsEnum.WallJump.name()).append(" : ").append(c.canWallJump);
		return builder.toString();
	}

	public static void send(EntityPlayerMP player, ActionsEnum action) {
		ShowActionPossibilityMessage message = new ShowActionPossibilityMessage();
		message.action = action;
		ParCool.CHANNEL_INSTANCE.sendTo(message, player);
	}

}
