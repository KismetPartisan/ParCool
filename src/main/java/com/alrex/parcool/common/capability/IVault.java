package com.alrex.parcool.common.capability;

import com.alrex.parcool.common.capability.capabilities.Capabilities;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IVault {
	@SideOnly(Side.CLIENT)
	public boolean canVault(EntityPlayer player);

	public boolean isVaulting();

	public void setVaulting(boolean vaulting);

	public void updateVaultingTime();

	public int getVaultingTime();

	public int getVaultAnimateTime();//Don't "return 0;"

	public static IVault get(EntityPlayer entity) {
		IVault vault = entity.getCapability(Capabilities.VAULT_CAPABILITY, null);
		return vault;
	}

}
