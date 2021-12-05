package io.github.zemelua.umu_client.gui.screen;

import io.github.zemelua.umu_client.gui.screen.option.ModOptionsSubScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.SoundOptionsScreen;
import net.minecraft.client.gui.screens.VideoSettingsScreen;
import net.minecraftforge.client.event.ScreenOpenEvent;

import java.util.Optional;

@SuppressWarnings("ClassCanBeRecord")
public final class ScreenManager {
	@SuppressWarnings({"FieldCanBeLocal", "unused"})
	private final Minecraft minecraft;

	public ScreenManager(Minecraft minecraft) {
		this.minecraft = minecraft;
	}

	public void onScreenOpen(final ScreenOpenEvent event) {
		if (event.getScreen() == null) return;

		Optional<Screen> screen = this.createReplaceScreen(event.getScreen());
		screen.ifPresent(event::setScreen);
	}

	private Optional<Screen> createReplaceScreen(Screen original) {
		if (original.getClass() == VideoSettingsScreen.class) {
			return Optional.of(ModOptionsSubScreen.videoOptionsScreen(null));
		} else if (original.getClass() == SoundOptionsScreen.class) {
			// return Optional.of(ModOptionsSubScreen.soundOptionsScreen(null));
			return Optional.empty();
		}

		return Optional.empty();
	}
}
