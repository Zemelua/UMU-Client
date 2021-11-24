package io.github.zemelua.umu_client.option;

import com.mojang.blaze3d.vertex.PoseStack;
import io.github.zemelua.umu_client.gui.screen.widget.OptionWidget;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.network.chat.Component;

public class SwitchOption implements IOption<Boolean> {
	private final boolean defaultValue;
	private final Component name;
	private final Component description;

	private boolean value;

	public SwitchOption(boolean defaultValue, Component name, Component description) {
		this.defaultValue = defaultValue;
		this.name = name;
		this.description = description;

		this.reset();
	}

	@Override
	public Boolean getValue() {
		return this.value;
	}

	@Override
	public void setValue(Boolean value) {
		this.value = value;
	}

	@Override
	public void reset() {
		this.value = this.defaultValue;
	}

	@Override
	public Component getName() {
		return this.name;
	}

	@Override
	public Component getDescription() {
		return this.description;
	}

	@Override
	public OptionWidget<SwitchOption> createWidget(int startX, int startY) {
		return new Widget(new Rect2i(startX, startY, 200, 18), this);
	}

	private static class Widget extends OptionWidget<SwitchOption> {
		public Widget(Rect2i rect, SwitchOption option) {
			super(rect, option);
		}

		@Override
		protected void drawValue(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
			int startX = this.rect.getX() + this.rect.getWidth() - 16;
			int startY = this.rect.getY() + this.rect.getHeight() / 2 - 5;
			int endX = startX + 10;
			int endY = startY + 10;
			int color = this.option.getValue() ? 0xFF94E4D3 : 0xFFFFFFFF;

			this.drawRectOutline(matrixStack, startX, startY, endX, endY, color);
			if (this.option.getValue()) {
				this.drawRect(matrixStack, startX + 2, startY + 2, endX - 2, endY - 2, color);
			}
		}

		@Override
		public boolean mouseClicked(double mouseX, double mouseY, int button) {
			if (button == 0 && this.rect.contains((int) mouseX, (int) mouseY)) {
				this.option.setValue(!this.option.getValue());
				this.playClickSound();

				return true;
			}

			return false;
		}
	}
}
