package com.alrex.parcool.client.input;


import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;

@SideOnly(Side.CLIENT)
public class KeyBindings {
	private static final GameSettings settings = Minecraft.getInstance().gameSettings;
	private static final KeyBinding keyBindCrawl = new KeyBinding("key.crawl.description", Keyboard.KEY_C, "key.categories.movement");
	private static final KeyBinding keyBindGrabWall = new KeyBinding("key.grab.description", 0, "key.categories.movement");
	private static final KeyBinding keyBindRoll = new KeyBinding("key.roll.description", Keyboard.KEY_C, "key.categories.movement");
	private static final KeyBinding keyBindFastRunning = new KeyBinding("key.fastrunning.description", Keyboard.KEY_LCONTROL, "key.categories.movement");
	private static final KeyBinding keyBindFrontFlip = new KeyBinding("key.frontflip.description", Keyboard.KEY_W, "key.categories.movement");
	private static final KeyBinding keyBindActivateParCool = new KeyBinding("key.parcool.activate", Keyboard.KEY_P, "key.categories.parcool");

	public static KeyBinding getKeySprint() {
		return settings.keyBindSprint;
	}

	public static KeyBinding getKeyJump() {
		return settings.keyBindJump;
	}

	public static KeyBinding getKeySneak() {
		return settings.field_74311_E;
	}

	public static KeyBinding getKeyLeft() {
		return settings.keyBindLeft;
	}

	public static KeyBinding getKeyRight() {
		return settings.keyBindRight;
	}

	public static KeyBinding getKeyForward() {
		return settings.keyBindForward;
	}

	public static KeyBinding getKeyBack() {
		return settings.keyBindBack;
	}

	public static KeyBinding getKeyCrawl() {
		return keyBindCrawl;
	}

	public static KeyBinding getKeyGrabWall() {
		return keyBindGrabWall;
	}

	public static KeyBinding getKeyActivateParCool() {
		return keyBindActivateParCool;
	}

	public static KeyBinding getKeyRoll() {
		return keyBindRoll;
	}

	public static KeyBinding getKeyFastRunning() {
		return keyBindFastRunning;
	}

	public static KeyBinding getKeyFrontFlip() {
		return keyBindFrontFlip;
	}

	@SubscribeEvent
	public static void register() {
		ClientRegistry.registerKeyBinding(keyBindCrawl);
		ClientRegistry.registerKeyBinding(keyBindGrabWall);
		ClientRegistry.registerKeyBinding(keyBindRoll);
		ClientRegistry.registerKeyBinding(keyBindFastRunning);
		ClientRegistry.registerKeyBinding(keyBindFrontFlip);
		ClientRegistry.registerKeyBinding(keyBindActivateParCool);
	}
}
