package com.alrex.parcool.common.network;

import com.alrex.parcool.ParCool;
import com.alrex.parcool.common.capability.IRoll;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.UUID;

public class SyncRollReadyMessage implements IMessage {
	private boolean rollReady = false;
	private UUID playerID = null;

	public void toBytes(ByteBuf packet) {
		packet.writeLong(this.playerID.getMostSignificantBits());
		packet.writeLong(this.playerID.getLeastSignificantBits());
		packet.writeBoolean(rollReady);
	}

	public void fromBytes(ByteBuf packet) {
		this.playerID = new UUID(packet.readLong(), packet.readLong());
		this.rollReady = packet.readBoolean();
	}

	@SideOnly(Side.SERVER)
	public static SyncRollReadyMessage handleServer(SyncRollReadyMessage message, MessageContext context) {
		EntityPlayerMP player = context.getServerHandler().player;
		player.getServerWorld().func_152344_a(() -> {
			ParCool.CHANNEL_INSTANCE.sendToAll(message);

			IRoll roll = IRoll.get(player);
			if (roll == null) return;

			roll.setRollReady(message.rollReady);
		});
		return null;
	}

	@SideOnly(Side.CLIENT)
	public static SyncRollReadyMessage handleClient(SyncRollReadyMessage message, MessageContext context) {
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
			IRoll roll = IRoll.get(player);
			if (roll == null) return;

			roll.setRollReady(message.rollReady);
		});
		return null;
	}

	@SideOnly(Side.CLIENT)
	public static void sync(EntityPlayer player) {
		IRoll roll = IRoll.get(player);
		if (roll == null) return;

		SyncRollReadyMessage message = new SyncRollReadyMessage();
		message.rollReady = roll.isRollReady();
		message.playerID = player.getUniqueID();

		ParCool.CHANNEL_INSTANCE.sendToServer(message);
	}
}