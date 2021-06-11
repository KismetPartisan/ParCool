package com.alrex.parcool.utilities;


import net.minecraft.util.math.Vec3d;

public class VectorUtil {
	public static double toYawDegree(Vec3d vec) {
		return (Math.atan2(vec.z, vec.x) * 180.0 / Math.PI - 90);
	}
}
