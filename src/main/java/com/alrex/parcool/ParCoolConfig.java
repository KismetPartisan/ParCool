package com.alrex.parcool;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;

@Config(modid = ParCool.MOD_ID)
public class ParCoolConfig {
	public static final Client client = new Client();

	public static class Client {
		public boolean canCatLeap;
		public boolean canCrawl;
		public boolean canDodge;
		public boolean canFastRunning;
		public boolean canFrontFlip;
		public boolean canGrabCliff;
		public boolean canRoll;
		public boolean canVault;
		public boolean canWallJump;
		@Config.RangeInt(min = 10, max = 5000)
		public int maxStamina;
		public boolean ParCoolActivation;
	}

	public static void sync() {
		ConfigManager.sync(ParCool.MOD_ID, Config.Type.INSTANCE);
	}
}
