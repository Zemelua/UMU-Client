package io.github.zemelua.umu_client;

import io.github.zemelua.umu_client.renderer.world.DynamicLightRenderer;
import net.minecraft.client.Minecraft;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.IEventBus;

public interface IClientHandler {
	void initialize();

	DynamicLightRenderer getDynamicLightRenderer();

	class ClientHandler implements IClientHandler {
		private final IEventBus forgeBus;
		private final IEventBus modBus;

		private final DynamicLightRenderer dynamicLightRenderer;

		public ClientHandler(IEventBus forgeBus, IEventBus modBus) {
			this.forgeBus = forgeBus;
			this.modBus = modBus;

			Minecraft minecraft = Minecraft.getInstance();

			this.dynamicLightRenderer = new DynamicLightRenderer(minecraft);
		}

		@Override
		public void initialize() {
			this.forgeBus.addListener(this::onClientTick);
		}

		@Override
		public DynamicLightRenderer getDynamicLightRenderer() {
			return this.dynamicLightRenderer;
		}

		public void onClientTick(final TickEvent.ClientTickEvent event) {
			if (event.phase == TickEvent.Phase.START) {
				this.dynamicLightRenderer.tick();
			}
		}
	}

	class DummyClientHandler implements IClientHandler {
		@Override
		public void initialize() {
		}

		@Override
		public DynamicLightRenderer getDynamicLightRenderer() {
			throw new IllegalStateException("Client handler doesn't initialized!");
		}
	}
}
