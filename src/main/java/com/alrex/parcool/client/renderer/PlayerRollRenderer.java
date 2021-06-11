package com.alrex.parcool.client.renderer;

import com.alrex.parcool.common.capability.IRoll;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraftforge.client.event.RenderPlayerEvent;

public class PlayerRollRenderer {
	public static void onRender(RenderPlayerEvent.Pre event) {
		EntityPlayerSP player = (EntityPlayerSP) event.getEntityPlayer();
		IRoll roll = IRoll.get(player);
		if (roll == null) return;

		if (roll.isRolling()) {
			/*
			EntityPlayerSP mainPlayer = Minecraft.getInstance().player;
			if (mainPlayer == null) return;

			Vec3d lookVec = player.getLookVec().rotateYaw((float) Math.PI / 2);
			Vec3d vec = new Vec3d((float) lookVec.x, 0, (float) lookVec.z);

			event.getMatrixStack().translate(0, player.getHeight() / 2, 0);
			event.getMatrixStack().rotate(vec.rotationDegrees((roll.getRollingTime() + event.getPartialRenderTick()) * 40));
			event.getMatrixStack().translate(0, -player.getHeight() / 2, 0);

			RenderPlayer renderer = event.getRenderer();
			ModelPlayer model = renderer.getEntityModel();

			event.getMatrixStack().push();
			Vec3d posOffset = RenderUtil.getPlayerOffset(mainPlayer, player, event.getPartialRenderTick());
			event.getMatrixStack().translate(posOffset.x, posOffset.getY(), posOffset.z);

			model.bipedRightArm.showModel = true;
			RenderUtil.rotateRightArm(player, model.bipedRightArm,
					(float) Math.toRadians(110.0F),
					(float) -Math.toRadians(player.renderYawOffset),
					(float) Math.toRadians(20.0F)
			);
			model.bipedRightArmwear.showModel = true;
			RenderUtil.rotateRightArm(player, model.bipedRightArmwear,
					(float) Math.toRadians(110.0F),
					(float) -Math.toRadians(player.renderYawOffset),
					(float) Math.toRadians(20.0F)
			);
			model.bipedLeftArm.showModel = true;
			RenderUtil.rotateLeftArm(player, model.bipedLeftArm,
					(float) Math.toRadians(110.0F),
					(float) -Math.toRadians(player.renderYawOffset),
					(float) Math.toRadians(-20.0F)
			);
			model.bipedLeftArmwear.showModel = true;
			RenderUtil.rotateLeftArm(player, model.bipedLeftArmwear,
					(float) Math.toRadians(110.0F),
					(float) -Math.toRadians(player.renderYawOffset),
					(float) Math.toRadians(-20.0F)
			);
			model.bipedLeftLeg.showModel = true;
			RenderUtil.rotateLeftLeg(player, model.bipedLeftLeg,
					(float) Math.toRadians(90.0f),
					(float) -Math.toRadians(player.renderYawOffset),
					(float) Math.toRadians(0F)
			);
			model.bipedLeftLegwear.showModel = true;
			RenderUtil.rotateLeftLeg(player, model.bipedLeftLegwear,
					(float) Math.toRadians(90.0f),
					(float) -Math.toRadians(player.renderYawOffset),
					(float) Math.toRadians(0F)
			);
			model.bipedRightLeg.showModel = true;
			RenderUtil.rotateRightLeg(player, model.bipedRightLeg,
					(float) Math.toRadians(90.0f),
					(float) -Math.toRadians(player.renderYawOffset),
					(float) Math.toRadians(0F)
			);
			model.bipedRightLegwear.showModel = true;
			RenderUtil.rotateRightLeg(player, model.bipedRightLegwear,
					(float) Math.toRadians(90.0f),
					(float) -Math.toRadians(player.renderYawOffset),
					(float) Math.toRadians(0F)
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
			model.bipedLeftLeg.render(
					event.getMatrixStack(),
					event.getBuffers().getBuffer(RenderType.getEntitySolid(location)),
					light,
					OverlayTexture.NO_OVERLAY
			);
			model.bipedLeftLegwear.render(
					event.getMatrixStack(),
					event.getBuffers().getBuffer(RenderType.getEntityTranslucent(location)),
					light,
					OverlayTexture.NO_OVERLAY
			);
			model.bipedRightLeg.render(
					event.getMatrixStack(),
					event.getBuffers().getBuffer(RenderType.getEntitySolid(location)),
					light,
					OverlayTexture.NO_OVERLAY
			);
			model.bipedRightLegwear.render(
					event.getMatrixStack(),
					event.getBuffers().getBuffer(RenderType.getEntityTranslucent(location)),
					light,
					OverlayTexture.NO_OVERLAY
			);
			event.getMatrixStack().pop();
			model.bipedRightArm.showModel = false;
			model.bipedRightArmwear.showModel = false;
			model.bipedLeftArm.showModel = false;
			model.bipedLeftArmwear.showModel = false;
			model.bipedLeftLeg.showModel = false;
			model.bipedLeftLegwear.showModel = false;
			model.bipedRightLeg.showModel = false;
			model.bipedRightLegwear.showModel = false;
			model.bipedRightLeg.showModel = true;

			 */
		}
	}
}
