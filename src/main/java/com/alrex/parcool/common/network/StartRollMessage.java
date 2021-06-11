package com.alrex.parcool.common.network;

import com.alrex.parcool.ParCool;
import com.alrex.parcool.ParCoolConfig;
import com.alrex.parcool.common.capability.IRoll;
import com.alrex.parcool.common.processor.RollLogic;
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

public class StartRollMessage implements IMessage {
	private UUID playerID = null;

	public void toBytes(ByteBuf packet) {
		packet.writeLong(this.playerID.getMostSignificantBits());
		packet.writeLong(this.playerID.getLeastSignificantBits());
	}

	public void fromBytes(ByteBuf packet) {
		this.playerID = new UUID(packet.readLong(), packet.readLong());
	}

	@SideOnly(Side.CLIENT)
	public static StartRollMessage handleClient(StartRollMessage message, MessageContext context) {
		Minecraft.getInstance().func_152344_a(() -> {
			if (context.side == Side.CLIENT) {
				World world = Minecraft.getInstance().world;
				if (world == null) return;
				EntityPlayer startPlayer = world.func_152378_a(message.playerID);
				if (startPlayer == null) return;

				IRoll roll = IRoll.get(startPlayer);
				if (roll == null) return;

				if (!ParCoolConfig.client.canRoll || !ParCoolConfig.client.ParCoolActivation)
					return;
				if (startPlayer.isUser()) {
					RollLogic.rollStart();
				} else {
					roll.setRollReady(false);
					roll.setRolling(true);
				}
			}
		});
		return null;
	}

	@SideOnly(Side.SERVER)
	public static StartRollMessage handleServer(StartRollMessage message, MessageContext context) {
		return null;
	}

	public static void send(EntityPlayerMP player) {
		StartRollMessage message = new StartRollMessage();
		message.playerID = player.getUniqueID();
		ParCool.CHANNEL_INSTANCE.sendToAll(message);
	}
}
