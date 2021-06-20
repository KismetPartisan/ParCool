package com.alrex.parcool.common.network;

import com.alrex.parcool.ParCool;
import com.alrex.parcool.common.capability.IStamina;
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

import java.util.UUID;

public class SyncStaminaMessage implements IMessage {

	private int stamina = 0;
	private boolean exhausted = false;
	private UUID playerID = null;

	@Override
	public void toBytes(ByteBuf packet) {
		packet.writeInt(this.stamina);
		packet.writeBoolean(this.exhausted);
		packet.writeLong(this.playerID.getMostSignificantBits());
		packet.writeLong(this.playerID.getLeastSignificantBits());
	}

	@Override
	public void fromBytes(ByteBuf packet) {
		this.stamina = packet.readInt();
		this.exhausted = packet.readBoolean();
		this.playerID = new UUID(packet.readLong(), packet.readLong());
	}

	@SideOnly(Side.CLIENT)
	public static class ClientHandler implements IMessageHandler<SyncStaminaMessage, SyncStaminaMessage> {
		@Override
		public SyncStaminaMessage onMessage(SyncStaminaMessage message, MessageContext context) {
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
				IStamina stamina = IStamina.get(player);
				if (stamina == null) return;
				stamina.setStamina(message.stamina);
				stamina.setExhausted(message.exhausted);
			});
			return null;
		}
	}

	@SideOnly(Side.SERVER)
	public static class ServerHandler implements IMessageHandler<SyncStaminaMessage, SyncStaminaMessage> {
		@Override
		public SyncStaminaMessage onMessage(SyncStaminaMessage message, MessageContext context) {
			EntityPlayerMP player = context.getServerHandler().player;
			player.getServerWorld().func_152344_a(() -> {
				ParCool.CHANNEL_INSTANCE.sendToAll(message);
				IStamina stamina = IStamina.get(player);
				if (stamina == null) return;

				stamina.setStamina(message.stamina);
				stamina.setExhausted(message.exhausted);
			});
			return null;
		}
	}

	@SideOnly(Side.CLIENT)
	public static void sync(EntityPlayer player) {
		IStamina stamina = IStamina.get(player);
		if (stamina == null) return;

		SyncStaminaMessage message = new SyncStaminaMessage();
		message.stamina = stamina.getStamina();
		message.exhausted = stamina.isExhausted();
		message.playerID = player.getUniqueID();

		ParCool.CHANNEL_INSTANCE.sendToServer(message);
	}
}
