package com.alrex.parcool.client.renderer;

import com.alrex.parcool.common.capability.IGrabCliff;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraftforge.client.event.RenderPlayerEvent;

public class PlayerGrabCliffRenderer {
	public static void onRender(RenderPlayerEvent.Pre event) {
		if (!(event.getEntityPlayer() instanceof EntityPlayerSP)) return;
		EntityPlayerSP player = (EntityPlayerSP) event.getEntityPlayer();

		IGrabCliff grabCliff = IGrabCliff.get(player);
		if (grabCliff == null) return;

		if (grabCliff.isGrabbing()) {
			/*
			EntityPlayerSP mainPlayer = Minecraft.getInstance().player;
			if (mainPlayer == null) return;

			RenderPlayer renderer = event.getRenderer();
			ModelPlayer model = renderer.func_177087_b();

			GL11.glPushMatrix();
			Vec3d posOffset = RenderUtil.getPlayerOffset(mainPlayer, player, event.getPartialRenderTick());
			GL11.glTranslated(posOffset.x, posOffset.y, posOffset.z);

			model.bipedRightArm.showModel = true;
			RenderUtil.rotateRightArm(player, model.bipedRightArm,
					(float) Math.toRadians(20.0F),
					(float) -Math.toRadians(player.renderYawOffset),
					(float) Math.toRadians(0.0F)
			);
			model.bipedRightArmwear.showModel = true;
			RenderUtil.rotateRightArm(player, model.bipedRightArmwear,
					(float) Math.toRadians(20.0F),
					(float) -Math.toRadians(player.renderYawOffset),
					(float) Math.toRadians(0.0F)
			);
			model.bipedLeftArm.showModel = true;
			RenderUtil.rotateLeftArm(player, model.bipedLeftArm,
					(float) Math.toRadians(20.0F),
					(float) -Math.toRadians(player.renderYawOffset),
					(float) Math.toRadians(0.0F)
			);
			model.bipedLeftArmwear.showModel = true;
			RenderUtil.rotateLeftArm(player, model.bipedLeftArmwear,
					(float) Math.toRadians(20.0F),
					(float) -Math.toRadians(player.renderYawOffset),
					(float) Math.toRadians(0.0F)
			);
			ResourceLocation location = player.getLocationSkin();
			int light = renderer.getPackedLight(player, event.getPartialRenderTick());
			renderer.getRenderManager().textureManager.bindTexture(location);
			model.bipedRightArm.render(
					event.getMatrixStack(),
					event.getBuffers().getBuffer(RenderType.getEntitySolid(location)),
					light,
					OverlayTexture.NO_OVERLAY
			);
			model.bipedRightArmwear.render(
					event.getMatrixStack(),
					event.getBuffers().getBuffer(RenderType.getEntityTranslucent(location)),
					light,
					OverlayTexture.NO_OVERLAY
			);
			model.bipedLeftArm.render(
					event.getMatrixStack(),
					event.getBuffers().getBuffer(RenderType.getEntitySolid(location)),
					light,
					OverlayTexture.NO_OVERLAY
			);
			model.bipedLeftArmwear.render(
					event.getMatrixStack(),
					event.getBuffers().getBuffer(RenderType.getEntityTranslucent(location)),
					light,
					OverlayTexture.NO_OVERLAY
			);
			GL11.glPopMatrix();
			model.bipedRightArm.showModel = false;
			model.bipedRightArmwear.showModel = false;
			model.bipedLeftArm.showModel = false;
			model.bipedLeftArmwear.showModel = false;

			 */
		}
	}
}
