package io.github.zemelua.umu_client.renderer.world;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class DynamicLightRenderer {
	private final Minecraft minecraft;

	public DynamicLightRenderer(Minecraft minecraft) {
		this.minecraft = minecraft;
	}

	public void tick() {
		Level world = this.minecraft.level;
		Player player = this.minecraft.player;
		if (world == null || player == null) return;

		world.getChunkSource().getLightEngine().checkBlock(player.blockPosition());
	}
}
