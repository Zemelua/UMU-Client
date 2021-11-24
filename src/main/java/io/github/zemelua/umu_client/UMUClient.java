package io.github.zemelua.umu_client;

import io.github.zemelua.umu_client.config.ClientConfig;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

@Mod(UMUClient.MOD_ID)
public class UMUClient {
	public static final String MOD_ID = "umu_client";
	public static final Logger LOGGER = LogManager.getLogger();
	public static final Marker MARKER = MarkerManager.getMarker("UMU_CLIENT");

	public static final ClientConfig CONFIG_CLIENT;
	public static final ForgeConfigSpec CONFIG_CLIENT_SPEC;

	public static IClientHandler CLIENT_HANDLER = new IClientHandler.DummyClientHandler();

	public UMUClient() {
		IEventBus forgeBus = MinecraftForge.EVENT_BUS;
		IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();

		CLIENT_HANDLER = new IClientHandler.ClientHandler(forgeBus, modBus);
		DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> CLIENT_HANDLER::initialize);

		ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, CONFIG_CLIENT_SPEC);
	}

	public static Component component(String name) {
		return new TranslatableComponent(MOD_ID + "." + name);
	}

	static {
		Pair<ClientConfig, ForgeConfigSpec> clientSpecPair = new ForgeConfigSpec.Builder().configure(ClientConfig::new);
		CONFIG_CLIENT = clientSpecPair.getLeft();
		CONFIG_CLIENT_SPEC = clientSpecPair.getRight();
	}
}
