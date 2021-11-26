package io.github.zemelua.umu_client.option;

import com.mojang.blaze3d.vertex.PoseStack;
import io.github.zemelua.umu_client.config.ClientConfig;
import io.github.zemelua.umu_client.gui.screen.widget.OptionWidget;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;

import java.util.function.Function;

public class RangeOption<T extends Number> extends IOption.BaseOption<T> implements IRangeOption<T> {
	private final T maxValue;
	private final T minValue;
	private final Function<Double, T> converter;

	public RangeOption(T defaultValue, T maxValue, T minValue, Function<Double, T> converter,
					   Function<ClientConfig, ConfigValue<T>> cache, Component name, Component description) {
		super(defaultValue, cache, name, description);

		this.maxValue = maxValue;
		this.minValue = minValue;
		this.converter = converter;
	}

	@Override
	public OptionWidget<T, ? extends IOption<T>> createWidget(int startX, int startY, int sizeX, int sizeY) {
		return new Widget<>(new Rect2i(startX, startY, sizeX, sizeY), this);
	}

	@Override
	public T getMax() {
		return this.maxValue;
	}

	@Override
	public T getMin() {
		return this.minValue;
	}

	public void setModifiedValue(Double value) {
		this.setModifiedValue(this.converter.apply(value));
	}

	protected static class Widget<T extends Number, O extends IOption<T> & IRangeOption<T>> extends OptionWidget<T, O> {
		private double thumbPos;

		public Widget(Rect2i rect, O option) {
			super(rect, option);

			this.loadThumbPos();
		}

		@Override
		protected void drawValue(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
			if (this.getSliderSize() > 0) {
				this.drawSlider(matrixStack, mouseX, mouseY, partialTicks);
				this.drawThumb(matrixStack, mouseX, mouseY, partialTicks);
			}

			this.drawCompact(matrixStack, mouseX, mouseY, partialTicks);
		}

		@Override
		protected int getValueWidth() {
			return this.getSliderSize() + 40;
		}

		protected void drawCompact(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
			String valueText = this.option.getModifiedValue().toString();
			int drawX = this.rect.getX() + this.rect.getWidth() - 6 - this.font.width(valueText) - this.getSliderSize()
					- this.getSliderSize() / 90 * 4;
			int drawY = this.rect.getY() + this.rect.getHeight() / 2 - 4;

			this.drawText(matrixStack, valueText, drawX, drawY, 0xFFFFFFFF);
		}

		protected void drawSlider(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
			int startX = this.rect.getX() + this.rect.getWidth() - this.getSliderSize() - 6;
			int startY = this.rect.getY() + this.rect.getHeight() / 2;
			int endX = startX + this.getSliderSize();
			int endY = startY + 1;
			int color = 0xFFFFFFFF;

			this.drawRect(matrixStack, startX, startY, endX, endY, color);
		}

		protected void drawThumb(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
			this.loadThumbPos();

			int thumbStartX = (int) (this.rect.getX() + this.rect.getWidth() - this.getSliderSize() - 6
					+ this.thumbPos * this.getSliderSize());
			int thumbStartY = this.rect.getY() + this.rect.getHeight() / 2 - 5;
			int thumbEndX = thumbStartX + 2;
			int thumbEndY = thumbStartY + 10;
			int color = 0xFFFFFFFF;

			this.drawRect(matrixStack, thumbStartX, thumbStartY, thumbEndX, thumbEndY, color);
		}

		@Override
		public boolean mouseClicked(double mouseX, double mouseY, int button) {
			if (button == 0 && this.rect.contains((int) mouseX, (int) mouseY)) {
				this.setValueFromMouse(mouseX);

				return true;
			}

			return false;
		}

		@Override
		public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
			if (button == 0 && this.rect.contains((int) mouseX, (int) mouseY)) {
				this.setValueFromMouse(mouseX);

				return true;
			}

			return false;
		}

		private void setValueFromMouse(double mouseX) {
			this.setValue((mouseX - ((double) this.rect.getX() + this.rect.getWidth() - this.getSliderSize() - 6))
					/ this.getSliderSize());
		}

		private void setValue(double newValue) {
			double valuePercent = Mth.clamp(newValue, 0.0D, 1.0D);

			this.option.setModifiedValue(this.option.getMin().doubleValue() + valuePercent
					* (this.option.getMax().doubleValue() - this.option.getMin().doubleValue()));
		}

		private void loadThumbPos() {
			this.thumbPos = (this.option.getModifiedValue().doubleValue() - this.option.getMin().doubleValue())
					/ (this.option.getMax().doubleValue() - this.option.getMin().doubleValue());
		}

		protected int getSliderSize() {
			if (!this.hasHovered) return 0;

			if (this.hovered) {
				return Math.min(90, (int) (this.touchedTick * 36));
			} else {
				return Math.min(90, Math.max(0, (int) ((-this.leftTick + 5.0D) * 36)));
			}
		}
	}
}
