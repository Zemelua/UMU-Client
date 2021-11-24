package io.github.zemelua.umu_client.gui.screen;

import io.github.zemelua.umu_client.gui.screen.option.ModOptionsSubScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.VideoSettingsScreen;

import java.util.Optional;

public final class ModScreenManager {
	public static Optional<Screen> createReplaceScreen(Screen original) {
		if (original.getClass() == VideoSettingsScreen.class) {
			return Optional.of(ModOptionsSubScreen.videoOptionsScreen(null));
		}

		return Optional.empty();
	}
}
