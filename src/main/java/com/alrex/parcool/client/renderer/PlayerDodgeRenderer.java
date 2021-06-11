package com.alrex.parcool.client.renderer;

import com.alrex.parcool.common.capability.IDodge;
import com.alrex.parcool.utilities.PlayerUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.RenderPlayerEvent;
import org.lwjgl.opengl.GL11;

public class PlayerDodgeRenderer {
	public static void onRender(RenderPlayerEvent.Pre event) {
		EntityPlayer player = event.getEntityPlayer();
		IDodge dodge = IDodge.get(player);
		if (dodge == null) return;

		if (dodge.isDodging() && dodge.getDirection() == IDodge.DodgeDirection.Back) {
			Vec3d lookVec = player.getLookVec().rotateYaw((float) Math.PI / 2);
			Vec3d vec = new Vec3d((float) lookVec.x, 0, (float) lookVec.z);

			GL11.glTranslated(0, PlayerUtils.getHeight(player) / 2, 0);
			GL11.glRotated((dodge.getDodgingTime() + event.getPartialRenderTick()) * -30, vec.x, 0, vec.z);
			GL11.glTranslated(0, -PlayerUtils.getHeight(player) / 2, 0);
		} else if (dodge.isDodging() && dodge.getDirection() == IDodge.DodgeDirection.Front) {
			Vec3d lookVec = player.getLookVec().rotateYaw((float) Math.PI / 2);
			Vec3d vec = new Vec3d((float) lookVec.x, 0, (float) lookVec.z);

			GL11.glTranslated(0, PlayerUtils.getHeight(player) / 2, 0);
			GL11.glRotated((dodge.getDodgingTime() + event.getPartialRenderTick()) * 30, vec.x, 0, vec.z);
			GL11.glTranslated(0, -PlayerUtils.getHeight(player) / 2, 0);
		}
	}
}
