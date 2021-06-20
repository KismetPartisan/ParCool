package com.alrex.parcool.common.network;

import com.alrex.parcool.ParCool;
import com.alrex.parcool.common.capability.IDodge;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class SyncDodgeMessage implements IMessage {
	private UUID playerID = null;
	private boolean isDodging = false;
	private String dodgeDirection = null;

	public void toBytes(ByteBuf packet) {
		packet.writeBoolean(this.isDodging);
		String name = dodgeDirection;
		packet.writeInt(name.length());
		packet.writeCharSequence(name, StandardCharsets.US_ASCII);
		packet.writeLong(this.playerID.getMostSignificantBits());
		packet.writeLong(this.playerID.getLeastSignificantBits());
	}

	public void fromBytes(ByteBuf packet) {
		isDodging = packet.readBoolean();
		dodgeDirection = packet.readCharSequence(packet.readInt(), StandardCharsets.US_ASCII).toString();
		playerID = new UUID(packet.readLong(), packet.readLong());
	}

	@SideOnly(Side.SERVER)
	public static class ServerHandler implements IMessageHandler<SyncDodgeMessage, SyncDodgeMessage> {
		@Override
		public SyncDodgeMessage onMessage(SyncDodgeMessage message, MessageContext context) {
			EntityPlayerMP player = context.getServerHandler().player;
			player.getServerWorld().func_152344_a(() -> {
				ParCool.CHANNEL_INSTANCE.sendToAll(message);

				IDodge dodge = IDodge.get(player);
				if (dodge == null) return;
				dodge.setDirection(IDodge.DodgeDirection.valueOf(message.dodgeDirection));
				dodge.setDodging(message.isDodging);
			});
			return null;
		}
	}

	@SideOnly(Side.CLIENT)
	public static class ClientHandler implements IMessageHandler<SyncDodgeMessage, SyncDodgeMessage> {
		@Override
		public SyncDodgeMessage onMessage(SyncDodgeMessage message, MessageContext context) {
			Minecraft.getInstance().func_152344_a(() -> {
				EntityPlayer player;
				if (context.side == Side.CLIENT) {
					World world = Minecraft.getInstance().world;
					if (world == null) return;
					player = world.func_152378_a(message.playerID);
					if (player == null || player.isUser()) return;
				} else {
					player = context.getServerHandler().player;
					ParCool.CHANNEL_INSTANCE.sendToAll(message);
					if (player == null) return;
				}

				IDodge dodge = IDodge.get(player);
				if (dodge == null) return;
				dodge.setDirection(IDodge.DodgeDirection.valueOf(message.dodgeDirection));
				dodge.setDodging(message.isDodging);
			});
			return null;
		}
	}

	@SideOnly(Side.CLIENT)
	public static void sync(EntityPlayer player) {
		IDodge dodge = IDodge.get(player);
		if (dodge == null) return;

		SyncDodgeMessage message = new SyncDodgeMessage();
		message.isDodging = dodge.isDodging();
		message.playerID = player.getUniqueID();
		IDodge.DodgeDirection direction = dodge.getDirection();
		if (direction == null) return;
		message.dodgeDirection = direction.name();

		ParCool.CHANNEL_INSTANCE.sendToServer(message);
	}
}
