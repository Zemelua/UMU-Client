package io.github.zemelua.umu_client.sound;

import com.google.common.collect.ImmutableMap;
import io.github.zemelua.umu_client.option.ModOptions;
import io.github.zemelua.umu_client.option.RangeOption;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;

public final class SoundManager {
	private static final ImmutableMap<SoundEvent, RangeOption> SOUND_EVENT_OPTION_MAP = ImmutableMap.<SoundEvent, RangeOption>builder()
			.put(SoundEvents.AMETHYST_BLOCK_PLACE, ModOptions.BLOCK_PLACE_VOLUME)
			.put(SoundEvents.AMETHYST_CLUSTER_PLACE, ModOptions.BLOCK_PLACE_VOLUME)
			.put(SoundEvents.ANCIENT_DEBRIS_PLACE, ModOptions.BLOCK_PLACE_VOLUME)
			.put(SoundEvents.ANVIL_PLACE, ModOptions.BLOCK_PLACE_VOLUME)
			.put(SoundEvents.ARMOR_STAND_PLACE, ModOptions.BLOCK_PLACE_VOLUME)
			.build();

	public static float getVolume(SoundEvent soundEvent) {
		RangeOption option =  SOUND_EVENT_OPTION_MAP.get(soundEvent);

		if (option != null) {
			return option.getValue() * 0.01F;
		}

		return 1.0F;
	}
}
