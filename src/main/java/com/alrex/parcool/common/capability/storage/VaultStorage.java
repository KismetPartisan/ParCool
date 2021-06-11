package com.alrex.parcool.common.capability.storage;

import com.alrex.parcool.common.capability.IVault;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

public class VaultStorage implements Capability.IStorage<IVault> {
	@Nullable
	@Override
	public NBTBase writeNBT(Capability<IVault> capability, IVault instance, EnumFacing side) {
		return null;
	}

	@Override
	public void readNBT(Capability<IVault> capability, IVault instance, EnumFacing side, NBTBase nbt) {

	}
}
