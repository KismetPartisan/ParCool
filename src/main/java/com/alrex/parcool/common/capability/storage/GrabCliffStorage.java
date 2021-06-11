package com.alrex.parcool.common.capability.storage;

import com.alrex.parcool.common.capability.IGrabCliff;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

public class GrabCliffStorage implements Capability.IStorage<IGrabCliff> {
	@Nullable
	@Override
	public NBTBase writeNBT(Capability<IGrabCliff> capability, IGrabCliff instance, EnumFacing side) {
		return null;
	}

	@Override
	public void readNBT(Capability<IGrabCliff> capability, IGrabCliff instance, EnumFacing side, NBTBase nbt) {

	}
}
