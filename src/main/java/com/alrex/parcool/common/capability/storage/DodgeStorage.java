package com.alrex.parcool.common.capability.storage;

import com.alrex.parcool.common.capability.IDodge;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

public class DodgeStorage implements Capability.IStorage<IDodge> {
	@Nullable
	@Override
	public NBTBase writeNBT(Capability<IDodge> capability, IDodge instance, EnumFacing side) {
		return null;
	}

	@Override
	public void readNBT(Capability<IDodge> capability, IDodge instance, EnumFacing side, NBTBase nbt) {

	}
}
