package com.alrex.parcool.common.capability;

import com.alrex.parcool.common.capability.capabilities.Capabilities;
import net.minecraft.entity.player.EntityPlayer;

public interface IStamina {
	public void setStamina(int newStamina);

	public int getStamina();

	public int getMaxStamina();

	public boolean isExhausted();

	public void setExhausted(boolean exhausted);

	public void consume(int amount);

	public void recover(int amount);

	public void updateRecoveryCoolTime();

	public int getRecoveryCoolTime();

	public static IStamina get(EntityPlayer entity) {
		IStamina stamina = entity.getCapability(Capabilities.STAMINA_CAPABILITY, null);
		return stamina;
	}

}
