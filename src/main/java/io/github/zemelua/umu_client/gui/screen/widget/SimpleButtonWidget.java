package io.github.zemelua.umu_client.gui.screen.widget;

import net.minecraft.client.renderer.Rect2i;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;

public class SimpleButtonWidget extends BaseWidget {
	protected final Runnable onPressed;

	public SimpleButtonWidget(Rect2i rect, Component label, Runnable onPressed) {
		super(rect, label);

		this.onPressed = onPressed;
	}

	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		if (!this.enabled) return false;

		if (button == 0 && this.rect.contains((int) mouseX, (int) mouseY)) {
			this.onPressed.run();
			this.playClickSound();

			return true;
		}

		return false;
	}

	protected void playClickSound() {
		this.speaker.play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
	}
}
