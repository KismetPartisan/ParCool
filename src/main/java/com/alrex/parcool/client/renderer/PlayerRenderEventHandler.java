package com.alrex.parcool.client.renderer;

import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class PlayerRenderEventHandler {
	@SubscribeEvent
	public static void onPlayerRenderPre(RenderPlayerEvent.Pre event) {
		GL11.glPushMatrix();

		PlayerDodgeRenderer.onRender(event);
		PlayerRollRenderer.onRender(event);
		PlayerGrabCliffRenderer.onRender(event);
	}

	@SubscribeEvent
	public static void onPlayerRenderPost(RenderPlayerEvent.Post event) {
		RenderPlayer renderer = event.getRenderer();
		ModelPlayer model = renderer.func_177087_b();

		GL11.glPopMatrix();
		model.bipedRightArm.showModel = true;
		model.bipedLeftArm.showModel = true;
		model.bipedLeftLeg.showModel = true;
		model.bipedRightLeg.showModel = true;
		model.bipedLeftArmwear.showModel = true;
		model.bipedRightArmwear.showModel = true;
		model.bipedLeftLegwear.showModel = true;
		model.bipedRightLegwear.showModel = true;
	}
}
