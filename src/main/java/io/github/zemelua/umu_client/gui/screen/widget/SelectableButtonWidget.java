package io.github.zemelua.umu_client.gui.screen.widget;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.network.chat.Component;

public class SelectableButtonWidget extends SimpleButtonWidget {
	private boolean selected;

	public SelectableButtonWidget(Rect2i rect, Component label, Runnable onPressed) {
		super(rect, label, onPressed);
	}

	@Override
	protected void drawLabel(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		super.drawLabel(matrixStack, mouseX, mouseY, partialTicks);

		if (this.enabled && this.selected) {
			int startX = this.rect.getX();
			int startY = this.rect.getY() + this.rect.getHeight() - 1;
			int endX = startX + this.rect.getWidth();
			int endY = startY + 1;
			int color = 0xFF94E4D3;

			this.drawRect(matrixStack, startX, startY, endX, endY, color);
		}
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}
}
