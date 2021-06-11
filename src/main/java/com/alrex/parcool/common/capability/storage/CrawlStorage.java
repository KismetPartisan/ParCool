package com.alrex.parcool.common.capability.storage;

import com.alrex.parcool.common.capability.ICrawl;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

public class CrawlStorage implements Capability.IStorage<ICrawl> {
	@Nullable
	@Override
	public NBTBase writeNBT(Capability<ICrawl> capability, ICrawl instance, EnumFacing side) {
		return null;
	}

	@Override
	public void readNBT(Capability<ICrawl> capability, ICrawl instance, EnumFacing side, NBTBase nbt) {

	}
}
