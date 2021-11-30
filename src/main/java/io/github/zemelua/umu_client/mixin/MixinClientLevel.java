package io.github.zemelua.umu_client.mixin;

import io.github.zemelua.umu_client.sound.SoundManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientLevel.class)
public class MixinClientLevel {
	@SuppressWarnings("SpellCheckingInspection")
	@Inject(at = @At("HEAD"),
			method = "playLocalSound(DDDLnet/minecraft/sounds/SoundEvent;Lnet/minecraft/sounds/SoundSource;FFZ)V",
			cancellable = true
	)
	private void playLocalSound(double xPos, double yPos, double zPos, SoundEvent event, SoundSource category,
								float volume, float pitch, boolean delayed, CallbackInfo callback) {
		float optionVolume = SoundManager.getVolume(event);

		if (optionVolume != 1.0F) {
			Minecraft minecraft = Minecraft.getInstance();

			double distance = minecraft.gameRenderer.getMainCamera().getPosition().distanceToSqr(xPos, yPos, zPos);
			SimpleSoundInstance instance
					= new SimpleSoundInstance(event, category, volume * optionVolume, pitch, xPos, yPos, zPos);
			if (delayed && distance > 100.0D) {
				double distanceSqrt = Math.sqrt(distance) / 40.0D;
				minecraft.getSoundManager().playDelayed(instance, (int) (distanceSqrt * 20.0D));
			} else {
				minecraft.getSoundManager().play(instance);
			}

			callback.cancel();
		}
	}
}
