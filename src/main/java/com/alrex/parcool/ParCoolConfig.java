package com.alrex.parcool;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;

@Config(modid = ParCool.MOD_ID)
public class ParCoolConfig {
	public static final Client client = new Client();

	public static class Client {
		public boolean canCatLeap = true;
		public boolean canCrawl = true;
		public boolean canDodge = true;
		public boolean canFastRunning = true;
		public boolean canFrontFlip = true;
		public boolean canGrabCliff = true;
		public boolean canRoll = true;
		public boolean canVault = true;
		public boolean canWallJump = true;
		@Config.RangeInt(min = 10, max = 5000)
		public int maxStamina;
		public boolean ParCoolActivation;
	}

	public static void sync() {
		ConfigManager.sync(ParCool.MOD_ID, Config.Type.INSTANCE);
	}
}
