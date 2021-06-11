package com.alrex.parcool.common.network;

import com.alrex.parcool.ParCool;
import com.alrex.parcool.common.capability.ICrawl;
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

public class SyncCrawlMessage implements IMessage {

	private boolean isCrawling = false;
	private boolean isSliding = false;
	private UUID playerID = null;

	public void toBytes(ByteBuf packet) {
		packet.writeBoolean(this.isCrawling);
		packet.writeBoolean(this.isSliding);
		packet.writeLong(this.playerID.getMostSignificantBits());
		packet.writeLong(this.playerID.getLeastSignificantBits());
	}

	public void fromBytes(ByteBuf packet) {
		isCrawling = packet.readBoolean();
		isSliding = packet.readBoolean();
		playerID = new UUID(packet.readLong(), packet.readLong());
	}

	@SideOnly(Side.SERVER)
	public static SyncCrawlMessage handleServer(SyncCrawlMessage message, MessageContext context) {
		EntityPlayerMP player = context.getServerHandler().player;
		player.getServerWorld().func_152344_a(() -> {
			ParCool.CHANNEL_INSTANCE.sendToAll(message);

			ICrawl crawl = ICrawl.get(player);
			if (crawl == null) return;

			crawl.setCrawling(message.isCrawling);
			crawl.setSliding(message.isSliding);
		});
		return null;
	}

	@SideOnly(Side.CLIENT)
	public static SyncCrawlMessage handleClient(SyncCrawlMessage message, MessageContext context) {
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

			ICrawl crawl = ICrawl.get(player);
			if (crawl == null) return;

			crawl.setCrawling(message.isCrawling);
			crawl.setSliding(message.isSliding);
		});
		return null;
	}

	@SideOnly(Side.CLIENT)
	public static void sync(EntityPlayer player) {
		ICrawl crawl = ICrawl.get(player);

		SyncCrawlMessage message = new SyncCrawlMessage();
		message.isCrawling = crawl.isCrawling();
		message.isSliding = crawl.isSliding();
		message.playerID = player.getUniqueID();

		ParCool.CHANNEL_INSTANCE.sendToServer(message);
	}
}
