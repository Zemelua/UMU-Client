package io.github.zemelua.umu_client.mixin;

import io.github.zemelua.umu_client.UMUClient;
import io.github.zemelua.umu_client.renderer.world.DynamicLightRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.lighting.BlockLightEngine;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockLightEngine.class)
public class BlockLightEngineMixin {
	@Inject(at = @At("RETURN"), method = "getLightEmission(J)I", cancellable = true, remap = true)
	private void getLightEmission(long pos, CallbackInfoReturnable<Integer> lightCallback) {
		Player player = Minecraft.getInstance().player;
		DynamicLightRenderer dynamicLightRenderer = UMUClient.CLIENT_HANDLER.getDynamicLightRenderer();

		if (player != null && player.blockPosition().equals(BlockPos.of(pos))) {
			lightCallback.setReturnValue(14);
		}
	}
}
