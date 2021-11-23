package io.github.zemelua.umu_client;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

@Mod(UMUClient.MOD_ID)
public class UMUClient {
	public static final String MOD_ID = "umu_client";
	public static final Logger LOGGER = LogManager.getLogger();
	public static final Marker MARKER = MarkerManager.getMarker("UMU_CLIENT");

	public static IClientHandler CLIENT_HANDLER = new IClientHandler.DummyClientHandler();

	public UMUClient() {
		IEventBus forgeBus = MinecraftForge.EVENT_BUS;
		IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();

		CLIENT_HANDLER = new IClientHandler.ClientHandler(forgeBus, modBus);
		DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> CLIENT_HANDLER::initialize);
	}
}
