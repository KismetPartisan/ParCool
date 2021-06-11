package com.alrex.parcool.common.capability.storage;

import com.alrex.parcool.common.capability.IFastRunning;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

public class FastRunningStorage implements Capability.IStorage<IFastRunning> {
	@Nullable
	@Override
	public NBTBase writeNBT(Capability<IFastRunning> capability, IFastRunning instance, EnumFacing side) {
		return null;
	}

	@Override
	public void readNBT(Capability<IFastRunning> capability, IFastRunning instance, EnumFacing side, NBTBase nbt) {

	}
}
