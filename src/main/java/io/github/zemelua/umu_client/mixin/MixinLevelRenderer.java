package io.github.zemelua.umu_client.mixin;

import io.github.zemelua.umu_client.sound.DetailedSoundSource;
import io.github.zemelua.umu_client.sound.SoundManager;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;

@Mixin(LevelRenderer.class)
public abstract class MixinLevelRenderer {
	@Shadow @Nullable private ClientLevel level;

	@Inject(at = @At(value = "HEAD"),
			method = "levelEvent(Lnet/minecraft/world/entity/player/Player;ILnet/minecraft/core/BlockPos;I)V",
			cancellable = true)
	@SuppressWarnings("SpellCheckingInspection")
	public void levelEvent(Player player, int eventId, BlockPos pos, int extraData, CallbackInfo callback) {
		if (eventId == 2001) {
			if (this.level != null) {
				BlockState blockState = Block.stateById(extraData);

				if (!blockState.isAir()) {
					SoundType soundType = blockState.getSoundType(this.level, pos, null);
					SoundEvent soundEvent = soundType.getBreakSound();
					float volume = (soundType.getVolume() + 1.0F) / 2.0F;
					float pitch = soundType.getPitch() * 0.8F;

					SoundManager.setDetailedSource(DetailedSoundSource.BLOCKS_BREAK);
					this.level.playLocalSound(pos, soundEvent, SoundSource.BLOCKS, volume, pitch, false);
				}

				this.level.addDestroyBlockEffect(pos, blockState);
			}

			callback.cancel();
		}
	}
}
