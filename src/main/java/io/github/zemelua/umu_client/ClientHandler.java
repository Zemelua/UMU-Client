package io.github.zemelua.umu_client;

import io.github.zemelua.umu_client.renderer.world.DynamicLightRenderer;
import net.minecraft.client.Minecraft;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.IEventBus;

public class ClientHandler {
	private final IEventBus forgeBus;
	private final IEventBus modBus;

	private final DynamicLightRenderer dynamicLightRenderer;

	public ClientHandler(IEventBus forgeBus, IEventBus modBus) {
		this.forgeBus = forgeBus;
		this.modBus = modBus;

		Minecraft minecraft = Minecraft.getInstance();

		this.dynamicLightRenderer = new DynamicLightRenderer(minecraft);
	}

	public void initialize() {
		this.forgeBus.addListener(this::onClientTick);
	}

	public void onClientTick(final TickEvent.ClientTickEvent event) {
		if (event.phase == TickEvent.Phase.START) {
			this.dynamicLightRenderer.tick();
		}
	}
}
