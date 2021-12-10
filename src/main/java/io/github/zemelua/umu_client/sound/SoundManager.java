package io.github.zemelua.umu_client.sound;

import io.github.zemelua.umu_client.option.IOption;
import io.github.zemelua.umu_client.option.IRangeOption;

public class SoundManager {
	private static DetailedSoundSource DETAILED_SOURCE = DetailedSoundSource.EMPTY;

	public static <T extends IOption<N> & IRangeOption<N>, N extends Number> float getVolume(T option) {
		return (float) option.toPercent(option.getValue().doubleValue());
	}

	public static void setDetailedSource(DetailedSoundSource source) {
		SoundManager.DETAILED_SOURCE = source;
	}

	public static int getDetailedSource() {
		int argOrdinal = SoundManager.DETAILED_SOURCE.ordinal();
		SoundManager.DETAILED_SOURCE = DetailedSoundSource.EMPTY;

		return argOrdinal;
	}
}
