package io.github.zemelua.umu_client;

import io.github.zemelua.umu_client.config.ModConfig;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

@Mod("umu_client")
public class UMUClient {
	public static final String MOD_ID = "umu_client";
	private static final Logger LOGGER = LogManager.getLogger();
	public static final Marker MARKER = MarkerManager.getMarker("UMU_CLIENT");

	public static final ModConfig CONFIG;
	public static final ForgeConfigSpec CONFIG_SPEC;

	public UMUClient() {
		IEventBus forgeBus = MinecraftForge.EVENT_BUS;
		IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();

		DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> new ClientHandler(forgeBus, modBus)::initialize);

		ModLoadingContext.get().registerConfig(net.minecraftforge.fml.config.ModConfig.Type.CLIENT, CONFIG_SPEC);
	}

	public static Component component(String name, Object... args) {
		return new TranslatableComponent(MOD_ID + "." + name, args);
	}

	static {
		Pair<ModConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(ModConfig::new);
		CONFIG = specPair.getLeft();
		CONFIG_SPEC = specPair.getRight();
	}
}
