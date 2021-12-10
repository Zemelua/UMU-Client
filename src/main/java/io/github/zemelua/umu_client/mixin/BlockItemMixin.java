package io.github.zemelua.umu_client.mixin;

import io.github.zemelua.umu_client.sound.DetailedSoundSource;
import io.github.zemelua.umu_client.sound.SoundManager;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockItem.class)
public class BlockItemMixin {
	@Inject(at = @At(value = "INVOKE", ordinal = 0,
			target = "Lnet/minecraft/world/level/Level;playSound(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/core/BlockPos;Lnet/minecraft/sounds/SoundEvent;Lnet/minecraft/sounds/SoundSource;FF)V"),
			method = "place(Lnet/minecraft/world/item/context/BlockPlaceContext;)Lnet/minecraft/world/InteractionResult;")
	@SuppressWarnings("SpellCheckingInspection")
	private void place(BlockPlaceContext context, CallbackInfoReturnable<InteractionResult> result) {
		SoundManager.setDetailedSource(DetailedSoundSource.BLOCKS_PLACE);
	}
}
