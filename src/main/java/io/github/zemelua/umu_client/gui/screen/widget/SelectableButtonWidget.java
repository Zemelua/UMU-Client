package io.github.zemelua.umu_client.gui.screen.widget;

import com.mojang.blaze3d.vertex.PoseStack;
import io.github.zemelua.umu_client.option.ModOptions;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.network.chat.Component;

public class SelectableButtonWidget extends SimpleButtonWidget {
	private boolean selected;
	private boolean hasSelected;

	private double selectedTick;

	public SelectableButtonWidget(Rect2i rect, Component label, Runnable onPressed) {
		super(rect, label, onPressed);

		this.hasSelected = false;
		this.selected = false;
	}

	@Override
	protected void drawLabel(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		super.drawLabel(matrixStack, mouseX, mouseY, partialTicks);

		if (this.selected) {
			this.selectedTick += partialTicks;
		} else {
			this.selectedTick = 0.0D;
		}

		if (this.enabled && this.getUnderLineSize() > 0) {
			int startX = this.rect.getX() + this.rect.getWidth() / 2 - this.getUnderLineSize() / 2;
			int startY = this.rect.getY() + this.rect.getHeight() - 1;
			int endX = startX + this.getUnderLineSize();
			int endY = startY + 1;
			int color = ModOptions.THEME_COLOR.getColor();

			this.drawRect(matrixStack, startX, startY, endX, endY, color);
		}
	}

	protected int getUnderLineSize() {
		if (!this.hasSelected && this.selected) return this.rect.getWidth();

		return Math.min(this.rect.getWidth(), (int) (this.selectedTick * 36));
	}

	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		boolean result = super.mouseClicked(mouseX, mouseY, button);

		if (result && !this.hasSelected) this.hasSelected = true;

		return result;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}
}
