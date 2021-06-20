package com.alrex.parcool.common.processor;

import com.alrex.parcool.ParCool;
import com.alrex.parcool.common.capability.ICrawl;
import com.alrex.parcool.common.network.SyncCrawlMessage;
import com.alrex.parcool.utilities.PlayerUtils;
import com.alrex.parcool.utilities.VectorUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class CrawlLogic {
	private static Vec3d slidingVec = null;
	private static boolean isCrawlEyeHeight = false;

	@SubscribeEvent
	public static void onTick(TickEvent.PlayerTickEvent event) {
		EntityPlayer player = event.player;

		ICrawl crawl = ICrawl.get(player);
		if (crawl == null) return;

		if (crawl.isCrawling() || crawl.isSliding()) {
			player.setSprinting(false);
			PlayerUtils.setCrawlSize(player);
			Vec3d vec = PlayerUtils.getVelocity(player);
			PlayerUtils.setVelocity(player, new Vec3d(vec.x / 2, vec.y, vec.z / 2));
			player.eyeHeight = player.getDefaultEyeHeight() / 2;
			isCrawlEyeHeight = true;
		} else if (isCrawlEyeHeight) {
			isCrawlEyeHeight = false;
			player.eyeHeight = player.getDefaultEyeHeight();
		}
		if (!ParCool.isActive()) return;
		if (!player.isUser() || event.phase != TickEvent.Phase.START || event.side == Side.SERVER) return;

		boolean oldCrawling = crawl.isCrawling();
		crawl.setCrawling(crawl.canCrawl(player));
		boolean oldSliding = crawl.isSliding();
		crawl.setSliding(crawl.canSliding(player));
		crawl.updateSlidingTime(player);

		if (crawl.isCrawling() != oldCrawling || crawl.isSliding() != oldSliding) {
			SyncCrawlMessage.sync(player);
		}
		if (!oldSliding && crawl.isSliding()) {
			Vec3d vec = PlayerUtils.getVelocity(player);
			slidingVec = new Vec3d(vec.x, 0, vec.z).scale(3.0);
		}
		if (crawl.isSliding()) {
			if (player.collidedVertically) PlayerUtils.setVelocity(player, slidingVec);
			slidingVec = slidingVec.scale(0.9);
		}
		if (crawl.isSliding()) {
			player.rotationYaw = (float) (Math.atan2(slidingVec.z, slidingVec.x) * 180.0 / Math.PI - 90.0);
		}
	}


	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public static void onRender(TickEvent.RenderTickEvent event) {
		EntityPlayerSP player = Minecraft.getInstance().player;
		if (player == null || !ParCool.isActive()) return;

		ICrawl crawl = ICrawl.get(player);
		if (crawl == null) return;

		if (crawl.isSliding()) {
			player.rotationYaw = (float) VectorUtil.toYawDegree(slidingVec);
		}
	}
}
