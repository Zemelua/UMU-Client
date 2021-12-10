package io.github.zemelua.umu_client.mixin;

import io.github.zemelua.umu_client.sound.DetailedSoundSource;
import io.github.zemelua.umu_client.sound.SoundManager;
import net.minecraft.client.resources.sounds.AbstractSoundInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractSoundInstance.class)
public abstract class AbstractSoundInstanceMixin {
	private final int detailedSoundSource = SoundManager.getDetailedSource();

	@Inject(at = @At("RETURN"), method = "getVolume()F", cancellable = true)
	private void getVolume(CallbackInfoReturnable<Float> callback) {
		callback.setReturnValue(callback.getReturnValueF() * DetailedSoundSource.getVolume(this.detailedSoundSource));
	}
}
