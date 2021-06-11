package com.alrex.parcool.common.capability.storage;

import com.alrex.parcool.common.capability.IWallJump;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

public class WallJumpStorage implements Capability.IStorage<IWallJump> {
	@Nullable
	@Override
	public NBTBase writeNBT(Capability<IWallJump> capability, IWallJump instance, EnumFacing side) {
		return null;
	}

	@Override
	public void readNBT(Capability<IWallJump> capability, IWallJump instance, EnumFacing side, NBTBase nbt) {

	}
}
