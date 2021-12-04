package io.github.zemelua.umu_client.gui.screen;

import io.github.zemelua.umu_client.gui.screen.option.ModOptionsSubScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.SoundOptionsScreen;
import net.minecraft.client.gui.screens.VideoSettingsScreen;

import java.util.Optional;

public final class ScreenManager {
	public static Optional<Screen> createReplaceScreen(Screen original) {
		if (original.getClass() == VideoSettingsScreen.class) {
			return Optional.of(ModOptionsSubScreen.videoOptionsScreen(null));
		} else if (original.getClass() == SoundOptionsScreen.class) {
			// return Optional.of(ModOptionsSubScreen.soundOptionsScreen(null));
			return Optional.empty();
		}

		return Optional.empty();
	}
}
