package com.alrex.parcool.common.capability.storage;

import com.alrex.parcool.common.capability.IStamina;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

public class StaminaStorage implements Capability.IStorage<IStamina> {
	private static final String STAMINA = "stamina";
	private static final String EXHAUSTED = "exhausted";

	@Nullable
	@Override
	public NBTBase writeNBT(Capability<IStamina> capability, IStamina instance, EnumFacing side) {
		NBTTagCompound compound = new NBTTagCompound();
		compound.putInt(STAMINA, instance.getStamina());
		compound.putBoolean(EXHAUSTED, instance.isExhausted());
		return compound;
	}

	@Override
	public void readNBT(Capability<IStamina> capability, IStamina instance, EnumFacing side, NBTBase nbt) {
		NBTTagCompound compound = (NBTTagCompound) nbt;
		instance.setStamina(compound.getInt(STAMINA));
		instance.setExhausted(compound.getBoolean(EXHAUSTED));
	}
}
