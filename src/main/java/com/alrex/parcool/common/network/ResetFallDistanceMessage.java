package com.alrex.parcool.common.network;

import com.alrex.parcool.ParCool;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

import java.util.UUID;

public class ResetFallDistanceMessage implements IMessage, IMessageHandler<ResetFallDistanceMessage, ResetFallDistanceMessage> {
	private UUID playerID = null;

	public void toBytes(ByteBuf packet) {
		packet.writeLong(this.playerID.getMostSignificantBits());
		packet.writeLong(this.playerID.getLeastSignificantBits());
	}

	public void fromBytes(ByteBuf packet) {
		this.playerID = new UUID(packet.readLong(), packet.readLong());
	}

	public ResetFallDistanceMessage onMessage(ResetFallDistanceMessage message, MessageContext context) {
		Minecraft.getInstance().func_152344_a(() -> {
			EntityPlayer player;
			if (context.side == Side.SERVER) {
				player = context.getServerHandler().player;
				if (player == null) return;
			} else return;
			player.fallDistance = 0;
		});
		return null;
	}

	//only in Client
	public static void sync(EntityPlayer player) {
		player.fallDistance = 0;
		ResetFallDistanceMessage message = new ResetFallDistanceMessage();
		message.playerID = player.getUniqueID();

		ParCool.CHANNEL_INSTANCE.sendToServer(message);
	}
}
