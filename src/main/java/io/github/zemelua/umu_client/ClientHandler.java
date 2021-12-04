package io.github.zemelua.umu_client;

import io.github.zemelua.umu_client.gui.screen.ScreenManager;
import net.minecraft.client.gui.screens.Screen;
import net.minecraftforge.client.event.ScreenOpenEvent;
import net.minecraftforge.eventbus.api.IEventBus;

import java.util.Optional;

public class ClientHandler {
	private final IEventBus forgeBus;
	private final IEventBus modBus;

	public ClientHandler(IEventBus forgeBus, IEventBus modBus) {
		this.forgeBus = forgeBus;
		this.modBus = modBus;
	}

	public void initialize() {
		this.forgeBus.addListener(ClientHandler::onGuiOpen);
	}

	private static void onGuiOpen(final ScreenOpenEvent event) {
		if (event.getScreen() == null) return;

		Optional<Screen> screen = ScreenManager.createReplaceScreen(event.getScreen());
		screen.ifPresent(event::setScreen);
	}
}
