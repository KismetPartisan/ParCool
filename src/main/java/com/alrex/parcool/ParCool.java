package com.alrex.parcool;

import com.alrex.parcool.client.input.KeyBindings;
import com.alrex.parcool.common.capability.capabilities.Capabilities;
import com.alrex.parcool.common.item.ItemRegistry;
import com.alrex.parcool.common.registries.EventBusForgeRegistry;
import com.alrex.parcool.common.registries.EventBusModRegistry;
import com.alrex.parcool.proxy.CommonProxy;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLLoadCompleteEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = ParCool.MOD_ID)
public class ParCool {
	public static final String MOD_ID = "parcool";
	private static final String PROTOCOL_VERSION = "1.0";
	public static final SimpleNetworkWrapper CHANNEL_INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(
			MOD_ID.concat(".message")
	);
	@SidedProxy(clientSide = "com.alrex.parcool.proxy.CleintProxy", serverSide = "com.alrex.parcool.proxy.ServerProxy")
	public static final CommonProxy PROXY = null;

	public static final Logger LOGGER = LogManager.getLogger();

	//only in Client
	public static boolean isActive() {
		return ParCoolConfig.client.ParCoolActivation;
	}

	//only in Client
	public static void setActivation(boolean activation) {
		ParCoolConfig.client.ParCoolActivation = activation;
	}

	public ParCool() {
		MinecraftForge.EVENT_BUS.register(ItemRegistry.class);
		MinecraftForge.EVENT_BUS.register(this);
	}

	@Mod.EventHandler
	private void loaded(FMLLoadCompleteEvent event) {
	}

	@Mod.EventHandler
	private void setup(final FMLInitializationEvent event) {
		EventBusForgeRegistry.register(MinecraftForge.EVENT_BUS);
		EventBusModRegistry.registry(MinecraftForge.EVENT_BUS);
		Capabilities.registerAll(CapabilityManager.INSTANCE);
		PROXY.registerMessages(CHANNEL_INSTANCE);
	}

	@Mod.EventHandler
	private void doClientStuff(final FMLInitializationEvent event) {
		if (event.getSide() == Side.SERVER) return;
		KeyBindings.register();
		EventBusForgeRegistry.registerClient(MinecraftForge.EVENT_BUS);
		EventBusModRegistry.registerClient(MinecraftForge.EVENT_BUS);
	}
}
