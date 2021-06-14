package com.alrex.parcool.utilities;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class WorldUtil {
	@Nullable
	public static Vec3d getWall(EntityLivingBase entity) {
		final double d = 0.3;
		double distance = PlayerUtils.getWidth(entity) / 2;
		double wallX = 0;
		double wallZ = 0;
		byte wallNumX = 0;
		byte wallNumZ = 0;

		AxisAlignedBB baseBox = new AxisAlignedBB(
				entity.posX - d,
				entity.posY,
				entity.posZ - d,
				entity.posX + d,
				entity.posY + PlayerUtils.getHeight(entity),
				entity.posZ + d
		);

		if (entity.world.checkBlockCollision(baseBox.expand(distance, 0, 0))) {
			wallX++;
			wallNumX++;
		}
		if (entity.world.checkBlockCollision(baseBox.expand(-distance, 0, 0))) {
			wallX--;
			wallNumX++;
		}
		if (entity.world.checkBlockCollision(baseBox.expand(0, 0, distance))) {
			wallZ++;
			wallNumZ++;
		}
		if (entity.world.checkBlockCollision(baseBox.expand(0, 0, -distance))) {
			wallZ--;
			wallNumZ++;
		}
		if (wallNumX == 2 || wallNumZ == 2 || (wallNumX == 0 && wallNumZ == 0)) return null;

		return new Vec3d(wallX, 0, wallZ);
	}

	@Nullable
	public static Vec3d getStep(EntityLivingBase entity) {
		final double d = 0.3;
		World world = entity.world;
		double distance = PlayerUtils.getWidth(entity) / 2;
		double baseLine = 1.55;
		double stepX = 0;
		double stepZ = 0;

		AxisAlignedBB baseBoxSide = new AxisAlignedBB(
				entity.posX - d,
				entity.posY,
				entity.posZ - d,
				entity.posX + d,
				entity.posY + baseLine,
				entity.posZ + d
		);
		AxisAlignedBB baseBoxTop = new AxisAlignedBB(
				entity.posX - d,
				entity.posY + baseLine,
				entity.posZ - d,
				entity.posX + d,
				entity.posY + PlayerUtils.getHeight(entity),
				entity.posZ + d
		);
		if (world.checkBlockCollision(baseBoxSide.expand(distance, 0, 0)) && world.checkBlockCollision(baseBoxTop.expand(distance, 0, 0))) {
			stepX++;
		}
		if (world.checkBlockCollision(baseBoxSide.expand(-distance, 0, 0)) && world.checkBlockCollision(baseBoxTop.expand(-distance, 0, 0))) {
			stepX--;
		}
		if (world.checkBlockCollision(baseBoxSide.expand(0, 0, distance)) && world.checkBlockCollision(baseBoxTop.expand(0, 0, distance))) {
			stepZ++;
		}
		if (world.checkBlockCollision(baseBoxSide.expand(0, 0, -distance)) && world.checkBlockCollision(baseBoxTop.expand(0, 0, -distance))) {
			stepZ--;
		}
		if (stepX == 0 && stepZ == 0) return null;

		return new Vec3d(stepX, 0, stepZ);
	}

	public static double getWallHeight(EntityLivingBase entity) {
		Vec3d wall = getWall(entity);
		if (wall == null) return 0;
		World world = entity.world;
		final double v = 0.1;
		final double d = 0.3;
		int loopNum = (int) Math.round(PlayerUtils.getHeight(entity) / v);
		double x1 = entity.posX + d + (wall.x > 0 ? 1 : 0);
		double y1 = entity.posY;
		double z1 = entity.posZ + d + (wall.z > 0 ? 1 : 0);
		double x2 = entity.posX - d + (wall.x < 0 ? -1 : 0);
		double z2 = entity.posZ - d + (wall.z < 0 ? -1 : 0);
		boolean canReturn = false;
		for (int i = 0; i < loopNum; i++) {
			AxisAlignedBB box = new AxisAlignedBB(
					x1, y1 + v * i, z1, x2, y1 + v * (i + 1), z2
			);

			if (!world.checkBlockCollision(box)) {
				canReturn = true;
			} else {
				if (canReturn) return v * i;
			}
		}
		return PlayerUtils.getHeight(entity);
	}

	public static boolean existsGrabbableWall(EntityLivingBase entity) {
		final double d = 0.3;
		World world = entity.getEntityWorld();
		double distance = PlayerUtils.getWidth(entity) / 2;
		double baseLine = entity.getEyeHeight() + (PlayerUtils.getHeight(entity) - entity.getEyeHeight()) / 2;

		AxisAlignedBB baseBoxSide = new AxisAlignedBB(
				entity.posX - d,
				entity.posY + baseLine - PlayerUtils.getHeight(entity) / 6,
				entity.posZ - d,
				entity.posX + d,
				entity.posY + baseLine,
				entity.posZ + d
		);
		AxisAlignedBB baseBoxTop = new AxisAlignedBB(
				entity.posX - d,
				entity.posY + baseLine,
				entity.posZ - d,
				entity.posX + d,
				entity.posY + PlayerUtils.getHeight(entity),
				entity.posZ + d
		);

		if (world.checkBlockCollision(baseBoxSide.expand(distance, 0, 0)) && !world.checkBlockCollision(baseBoxTop.expand(distance, 0, 0)))
			return true;
		if (world.checkBlockCollision(baseBoxSide.expand(-distance, 0, 0)) && !world.checkBlockCollision(baseBoxTop.expand(-distance, 0, 0)))
			return true;
		if (world.checkBlockCollision(baseBoxSide.expand(0, 0, distance)) && !world.checkBlockCollision(baseBoxTop.expand(0, 0, distance)))
			return true;
		if (world.checkBlockCollision(baseBoxSide.expand(0, 0, -distance)) && !world.checkBlockCollision(baseBoxTop.expand(0, 0, -distance)))
			return true;

		return false;
	}
}
