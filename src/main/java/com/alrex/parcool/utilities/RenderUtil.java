package com.alrex.parcool.utilities;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

//only in Client
public class RenderUtil {
	public static void rotateRightArm(EntityPlayerSP player, ModelRenderer rightArm, float angleX, float angleY, float angleZ) {
		rightArm.rotationPointX = -MathHelper.cos((float) Math.toRadians(player.renderYawOffset)) * 4.2F;
		rightArm.rotationPointY = 20.5f;
		rightArm.rotationPointZ = -MathHelper.sin((float) Math.toRadians(player.renderYawOffset)) * 5.0F;
		rightArm.rotateAngleX = angleX;
		rightArm.rotateAngleY = angleY;
		rightArm.rotateAngleZ = angleZ;
	}

	public static void rotateLeftArm(EntityPlayerSP player, ModelRenderer leftArm, float angleX, float angleY, float angleZ) {
		leftArm.rotationPointX = MathHelper.cos((float) Math.toRadians(player.renderYawOffset)) * 4.2F;
		leftArm.rotationPointY = 20.5f;
		leftArm.rotationPointZ = MathHelper.sin((float) Math.toRadians(player.renderYawOffset)) * 5.0F;
		leftArm.rotateAngleX = angleX;
		leftArm.rotateAngleY = angleY;
		leftArm.rotateAngleZ = angleZ;
	}

	public static void rotateRightLeg(EntityPlayerSP player, ModelRenderer rightLeg, float angleX, float angleY, float angleZ) {
		rightLeg.rotateAngleX = angleX;
		rightLeg.rotateAngleY = angleY;
		rightLeg.rotateAngleZ = angleZ;
	}

	public static void rotateLeftLeg(EntityPlayerSP player, ModelRenderer leftLeg, float angleX, float angleY, float angleZ) {
		leftLeg.rotateAngleX = angleX;
		leftLeg.rotateAngleY = angleY;
		leftLeg.rotateAngleZ = angleZ;
	}

	public static Vec3d getPlayerOffset(EntityPlayer basePlayer, EntityPlayer targetPlayer, float partialTick) {
		return new Vec3d(
				(basePlayer.lastTickPosX + ((basePlayer.posX - basePlayer.lastTickPosX) * partialTick)) - (targetPlayer.lastTickPosX + ((targetPlayer.posX - targetPlayer.lastTickPosX) * partialTick)),
				(basePlayer.lastTickPosY + ((basePlayer.posY - basePlayer.lastTickPosY) * partialTick)) - (targetPlayer.lastTickPosY + ((targetPlayer.posY - targetPlayer.lastTickPosY) * partialTick)),
				(basePlayer.lastTickPosZ + ((basePlayer.posZ - basePlayer.lastTickPosZ) * partialTick)) - (targetPlayer.lastTickPosZ + ((targetPlayer.posZ - targetPlayer.lastTickPosZ) * partialTick))
		);
	}
}
