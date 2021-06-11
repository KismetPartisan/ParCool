package com.alrex.parcool.common.network;

import com.alrex.parcool.ParCool;
import com.alrex.parcool.ParCoolConfig;
import com.alrex.parcool.constants.ActionsEnum;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

import java.nio.charset.StandardCharsets;

public class SetActionPossibilityMessage implements IMessage, IMessageHandler<SetActionPossibilityMessage, SetActionPossibilityMessage> {
	private ActionsEnum actionsEnum = null;
	private boolean possibility = false;

	public void toBytes(ByteBuf packet) {
		packet.writeBoolean(possibility);
		String name = actionsEnum.name();
		packet.writeInt(name.length());
		packet.writeCharSequence(name, StandardCharsets.US_ASCII);
	}

	public void fromBytes(ByteBuf packet) {
		this.possibility = packet.readBoolean();
		this.actionsEnum = ActionsEnum.valueOf(packet.readCharSequence(packet.readInt(), StandardCharsets.US_ASCII).toString());
	}

	public SetActionPossibilityMessage onMessage(SetActionPossibilityMessage message, MessageContext context) {
		Minecraft.getInstance().func_152344_a(() -> {
			if (context.side == Side.CLIENT) {
				ParCoolConfig.Client c = ParCoolConfig.client;
				switch (actionsEnum) {
					case Crawl:
						c.canCrawl = (possibility);
						break;
					case CatLeap:
						c.canCatLeap = (possibility);
						break;
					case Dodge:
						c.canDodge = (possibility);
						break;
					case FastRunning:
						c.canFastRunning = (possibility);
						break;
					case Roll:
						c.canRoll = (possibility);
						break;
					case Vault:
						c.canVault = (possibility);
						break;
					case WallJump:
						c.canWallJump = (possibility);
						break;
					case GrabCliff:
						c.canGrabCliff = (possibility);
						break;
				}
				EntityPlayerSP player = Minecraft.getInstance().player;
			}
		});
		return null;
	}

	public static void send(EntityPlayerMP player, ActionsEnum actionsEnum, boolean possibility) {
		SetActionPossibilityMessage message = new SetActionPossibilityMessage();
		message.actionsEnum = actionsEnum;
		message.possibility = possibility;
		ParCool.CHANNEL_INSTANCE.sendTo(message, player);
	}
}