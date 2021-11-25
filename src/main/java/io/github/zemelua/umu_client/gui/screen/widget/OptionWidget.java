package io.github.zemelua.umu_client.gui.screen.widget;

import com.mojang.blaze3d.vertex.PoseStack;
import io.github.zemelua.umu_client.option.IOption;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.network.chat.FormattedText;

public abstract class OptionWidget<T, O extends IOption<T>> extends BaseWidget {
	protected final O option;
	protected T modifiedValue;

	public OptionWidget(Rect2i rect, O option) {
		super(rect, option.getName());

		this.option = option;

		this.modifiedValue = this.option.getValue();
	}

	@Override
	protected void drawLabel(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		FormattedText label = this.label;
		if (this.font.width(label) > (this.rect.getWidth() - this.getValueWidth())) {
			label = this.font.substrByWidth(label, this.rect.getWidth() - this.getValueWidth());
			label = FormattedText.composite(label, FormattedText.of("..."));
		}

		int drawX = this.rect.getX() + 6;
		int drawY = this.rect.getY() + this.rect.getHeight() / 2 - 4;
		int labelColor = this.enabled ? 0xFFFFFFFF : 0x90FFFFFF;

		this.drawText(matrixStack, label.getString(), drawX, drawY, labelColor);
		this.drawValue(matrixStack, mouseX, mouseY, partialTicks);
	}

	protected abstract void drawValue(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks);

	protected abstract int getValueWidth();
}
