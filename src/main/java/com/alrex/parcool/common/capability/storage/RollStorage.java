package com.alrex.parcool.common.capability.storage;

import com.alrex.parcool.common.capability.IRoll;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

public class RollStorage implements Capability.IStorage<IRoll> {
	@Nullable
	@Override
	public NBTBase writeNBT(Capability<IRoll> capability, IRoll instance, EnumFacing side) {
		return null;
	}

	@Override
	public void readNBT(Capability<IRoll> capability, IRoll instance, EnumFacing side, NBTBase nbt) {

	}
}
