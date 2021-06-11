package com.alrex.parcool.client.particle;

import com.alrex.parcool.utilities.PlayerUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.Random;

//only in Client
public class ParticleProvider {
	public static void spawnEffectActivateParCool(EntityPlayer player) {
		World world = player.getEntityWorld();
		final double x = player.posX;
		final double y = player.posY + 0.1;
		final double z = player.posZ;

		Vec3d motion = PlayerUtils.getVelocity(player);
		Vec3d vec = new Vec3d(0, 0, 0.3);
		for (int i = 0; i < 16; i++) {
			Vec3d direction = vec.rotateYaw((float) (Math.PI / 8 * i)).add(motion);
			world.func_175688_a(EnumParticleTypes.SMOKE_LARGE,
					x, y, z, direction.x, direction.y, direction.z);
		}
	}

	public static void spawnEffectSweat(EntityPlayer player) {
		World world = player.getEntityWorld();
		Random random = player.getRNG();
		final double x = player.posX;
		final double y = player.posY;
		final double z = player.posZ;
		world.func_175688_a(
				EnumParticleTypes.DRIP_WATER,
				x + random.nextInt(10) / 10d - 0.5,
				y + random.nextInt(20) / 10d,
				z + random.nextInt(10) / 10d - 0.5,
				0, 0, 0
		);
	}
}
