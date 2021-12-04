package io.github.zemelua.umu_client;

import io.github.zemelua.umu_client.gui.screen.ScreenManager;
import io.github.zemelua.umu_client.renderer.dynamic_light.DynamicLightRenderer;
import net.minecraft.client.gui.screens.Screen;
import net.minecraftforge.client.event.ScreenOpenEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.IEventBus;

import java.util.Optional;

@SuppressWarnings("ClassCanBeRecord")
public class ClientHandler {
	private final IEventBus forgeBus;
	@SuppressWarnings({"FieldCanBeLocal", "unused"})
	private final IEventBus modBus;

	public ClientHandler(IEventBus forgeBus, IEventBus modBus) {
		this.forgeBus = forgeBus;
		this.modBus = modBus;
	}

	public void initialize() {
		this.forgeBus.addListener(this::onClientTick);
		this.forgeBus.addListener(this::onGuiOpen);
	}

	private void onClientTick(final TickEvent.ClientTickEvent event) {
		if (event.phase == TickEvent.Phase.END) {
			DynamicLightRenderer.INSTANCE.updateLights();
		}
	}

	private void onGuiOpen(final ScreenOpenEvent event) {
		if (event.getScreen() == null) return;

		Optional<Screen> screen = ScreenManager.createReplaceScreen(event.getScreen());
		screen.ifPresent(event::setScreen);
	}
}
