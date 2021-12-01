package io.github.zemelua.umu_client.gui.screen.widget;

import com.mojang.blaze3d.vertex.PoseStack;
import io.github.zemelua.umu_client.option.IOption;
import io.github.zemelua.umu_client.option.ModOptions;
import io.github.zemelua.umu_client.util.Rect2i;
import net.minecraft.network.chat.FormattedText;

public abstract class OptionWidget<T, O extends IOption<T>> extends BaseWidget {
	protected final O option;

	protected T modifiableValue;

	public OptionWidget(Rect2i rect, O option) {
		super(rect, option.getName());

		this.option = option;
		this.modifiableValue = this.option.getValue();
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
		int labelColor = this.getLabelColor();

		this.drawText(matrixStack, label.getString(), drawX, drawY, labelColor);
		this.drawValue(matrixStack, mouseX, mouseY, partialTicks);
	}

	protected abstract void drawValue(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks);

	protected abstract int getValueWidth();

	protected int getValueColor() {
		return this.isEnabled() ? 0xFFFFFFFF : 0x90FFFFFF;
	}

	protected int getLabelColor() {
		return this.isEnabled()
				? (this.isChanged() ? ModOptions.THEME_COLOR.getColor() : 0xFFFFFFFF)
				: 0x90FFFFFF;
	}


	public void load() {
		this.modifiableValue = this.option.getValue();
	}

	public void save() {
		this.option.setValue(this.modifiableValue);
	}

	public void reset() {
		this.option.reset();
		this.load();
	}

	public boolean isChanged() {
		return !this.modifiableValue.equals(this.option.getValue());
	}

	@Override
	protected boolean isEnabled() {
		return this.option.isEnable();
	}
}
