package com.alrex.parcool.client.hud;


import com.alrex.parcool.ParCoolConfig;
import com.alrex.parcool.common.capability.IStamina;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.client.config.GuiUtils;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class StaminaHUD extends Gui {
	public static void render(RenderGameOverlayEvent event) {
		if (!ParCoolConfig.client.ParCoolActivation) return;
		EntityPlayerSP player = Minecraft.getInstance().player;
		if (player == null) return;
		IStamina stamina = IStamina.get(player);
		if (stamina == null) return;

		ScaledResolution resolution = new ScaledResolution(Minecraft.getInstance());
		final int width = resolution.func_78326_a();
		final int height = resolution.func_78328_b();
		final int boxWidth = 100;
		final int boxHeight = 20;
		final int heartWidth = boxHeight - 9;
		final int staminaWidth = boxWidth - heartWidth - 11;
		int x = width - boxWidth - 1;
		int y = height - boxHeight - 1;

		double staminaScale = (double) stamina.getStamina() / stamina.getMaxStamina();
		if (staminaScale < 0) staminaScale = 0;
		if (staminaScale > 1) staminaScale = 1;
		int color = getStaminaColor(staminaScale, stamina.isExhausted());

		GuiUtils.drawGradientRect(0, x, y, x + boxWidth, y + boxHeight, 0x99585654, 0x99585654);
		GuiUtils.drawGradientRect(0, x + 2, y + 2, x + boxWidth - 2, y + boxHeight - 2, 0x66898989, 0x66898989);
		GuiUtils.drawGradientRect(0, x + heartWidth + 7, y + 4, x + heartWidth + 7 + staminaWidth, y + 5 + heartWidth, 0x992B2B2B, 0x992B2B2B);
		GuiUtils.drawGradientRect(0, x + heartWidth + 7, y + 4, x + heartWidth + 7 + (int) Math.round(staminaWidth * staminaScale), y + 5 + heartWidth, color, color);
		renderYellowHeart(x + 4, y + 5, heartWidth, heartWidth);
	}

	private static void renderYellowHeart(int x, int y, int width, int height) {
		Gui.func_152125_a(x, y, 161, 1, 7, 7, width, height, 256, 256);
	}

	private static int getStaminaColor(double factor, boolean exhausted) {
		if (exhausted) return 0xCC993C3A;
		if (factor > 0.5) {
			return getColorCodeFromARGB(0xCC, (int) (0xFF * (1 - factor) * 2), 0xFF, 0);
		} else {
			return getColorCodeFromARGB(0xCC, 0xFF, (int) (0xFF * (factor * 2)), 0);
		}
	}

	private static int getColorCodeFromARGB(int a, int r, int g, int b) {
		return a * 0x1000000 + r * 0x10000 + g * 0x100 + b;
	}

	@SubscribeEvent
	public static void onOverlay(RenderGameOverlayEvent.Pre event) {
		if (event.getType() != RenderGameOverlayEvent.ElementType.EXPERIENCE) return;
		render(event);
	}
}
