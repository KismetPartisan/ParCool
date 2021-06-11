package com.alrex.parcool.common.network;

import com.alrex.parcool.ParCool;
import com.alrex.parcool.common.capability.IFastRunning;
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

public class SyncFastRunningMessage implements IMessage {
	private boolean isFastRunning = false;
	private UUID playerID = null;

	public void toBytes(ByteBuf packet) {
		packet.writeBoolean(this.isFastRunning);
		packet.writeLong(this.playerID.getMostSignificantBits());
		packet.writeLong(this.playerID.getLeastSignificantBits());
	}

	public void fromBytes(ByteBuf packet) {
		this.isFastRunning = packet.readBoolean();
		this.playerID = new UUID(packet.readLong(), packet.readLong());
	}

	@SideOnly(Side.SERVER)
	public static SyncFastRunningMessage handleServer(SyncFastRunningMessage message, MessageContext context) {
		EntityPlayerMP player = context.getServerHandler().player;
		player.getServerWorld().func_152344_a(() -> {
			ParCool.CHANNEL_INSTANCE.sendToAll(message);

			IFastRunning fastRunning = IFastRunning.get(player);
			if (fastRunning == null) return;
			fastRunning.setFastRunning(message.isFastRunning);
		});
		return null;
	}

	@SideOnly(Side.CLIENT)
	public static SyncFastRunningMessage handleClient(SyncFastRunningMessage message, MessageContext context) {
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

			IFastRunning fastRunning = IFastRunning.get(player);
			if (fastRunning == null) return;
			fastRunning.setFastRunning(message.isFastRunning);
		});
		return null;
	}

	@SideOnly(Side.CLIENT)
	public static void sync(EntityPlayer player) {
		IFastRunning fastRunning = IFastRunning.get(player);
		SyncFastRunningMessage message = new SyncFastRunningMessage();
		message.isFastRunning = fastRunning.isFastRunning();
		message.playerID = player.getUniqueID();

		ParCool.CHANNEL_INSTANCE.sendToServer(message);
	}

}
