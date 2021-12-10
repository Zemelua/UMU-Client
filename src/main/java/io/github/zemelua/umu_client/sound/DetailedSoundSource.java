package io.github.zemelua.umu_client.sound;

import io.github.zemelua.umu_client.option.ModOptions;
import io.github.zemelua.umu_client.option.RangeOption;

import javax.annotation.Nullable;

public enum DetailedSoundSource {
	BLOCKS_PLACE(ModOptions.BLOCKS_PLACE_VOLUME),
	BLOCKS_BREAK(ModOptions.BLOCKS_BREAK_VOLUME),
	BLOCKS_AMBIENT(ModOptions.BLOCKS_AMBIENT_VOLUME),
	EMPTY(null)
	;

	@Nullable private final RangeOption volumeOption;

	DetailedSoundSource(@Nullable RangeOption volumeOption) {
		this.volumeOption = volumeOption;
	}

	public static float getVolume(int ordinal) {
		return DetailedSoundSource.values()[ordinal].getVolume();
	}

	public float getVolume() {
		if (this.volumeOption == null) return 1.0F;

		return SoundManager.getVolume(this.volumeOption);
	}
}
