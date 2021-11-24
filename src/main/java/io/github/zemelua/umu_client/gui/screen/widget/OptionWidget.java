package io.github.zemelua.umu_client.gui.screen.widget;

import com.mojang.blaze3d.vertex.PoseStack;
import io.github.zemelua.umu_client.option.IOption;
import net.minecraft.client.renderer.Rect2i;

public abstract class OptionWidget<T extends IOption<?>> extends BaseWidget {
	protected final T option;

	public OptionWidget(Rect2i rect, T option) {
		super(rect, option.getName());

		this.option = option;
	}

	@Override
	protected void drawLabel(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		int labelColor = this.enabled ? 0xFFFFFFFF : 0x90FFFFFF;
		int drawX = this.rect.getX() + 6;
		int drawY = this.rect.getY() + this.rect.getHeight() / 2 - 4;

		this.drawText(matrixStack, this.label, drawX, drawY, labelColor);
		this.drawValue(matrixStack, mouseX, mouseY, partialTicks);
	}

	protected abstract void drawValue(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks);
}
