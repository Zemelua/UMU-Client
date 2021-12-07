package io.github.zemelua.umu_client;

import io.github.zemelua.umu_client.gui.screen.ScreenManager;
import io.github.zemelua.umu_client.renderer.world.DynamicLightRenderer;
import io.github.zemelua.umu_client.renderer.world.FallingStarRenderer;
import io.github.zemelua.umu_client.renderer.world.ViewTiltRenderer;
import net.minecraft.client.Minecraft;
import net.minecraftforge.eventbus.api.IEventBus;

@SuppressWarnings("ClassCanBeRecord")
public class ClientHandler {
	public static final DynamicLightRenderer DYNAMIC_LIGHT_RENDERER = new DynamicLightRenderer(Minecraft.getInstance());
	public static final ViewTiltRenderer VIEW_TILT_RENDERER = new ViewTiltRenderer(Minecraft.getInstance());
	public static final FallingStarRenderer FALLING_STAR_RENDERER = new FallingStarRenderer(Minecraft.getInstance());
	public static final ScreenManager SCREEN_MANAGER = new ScreenManager(Minecraft.getInstance());

	private final IEventBus forgeBus;
	@SuppressWarnings({"FieldCanBeLocal", "unused"})
	private final IEventBus modBus;

	public ClientHandler(IEventBus forgeBus, IEventBus modBus) {
		this.forgeBus = forgeBus;
		this.modBus = modBus;
	}

	public void initialize() {
		this.forgeBus.addListener(DYNAMIC_LIGHT_RENDERER::onClientTick);
		this.forgeBus.addListener(VIEW_TILT_RENDERER::onCameraSetup);
		this.forgeBus.addListener(FALLING_STAR_RENDERER::onClientTick);
		this.forgeBus.addListener(SCREEN_MANAGER::onScreenOpen);
	}
}
