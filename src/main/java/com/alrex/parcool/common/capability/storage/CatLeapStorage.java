package com.alrex.parcool.common.capability.storage;

import com.alrex.parcool.common.capability.ICatLeap;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

public class CatLeapStorage implements Capability.IStorage<ICatLeap> {
	@Nullable
	@Override
	public NBTBase writeNBT(Capability<ICatLeap> capability, ICatLeap instance, EnumFacing side) {
		return null;
	}

	@Override
	public void readNBT(Capability<ICatLeap> capability, ICatLeap instance, EnumFacing side, NBTBase nbt) {

	}
}
