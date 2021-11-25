package io.github.zemelua.umu_client.option;

import com.mojang.blaze3d.vertex.PoseStack;
import io.github.zemelua.umu_client.config.ClientConfig;
import io.github.zemelua.umu_client.gui.screen.widget.OptionWidget;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.network.chat.Component;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;

import java.util.function.Function;

public class RangeOption extends IOption.BaseOption<Integer> implements IRangeOption<Integer> {
	private final Integer maxValue;
	private final Integer minValue;

	public RangeOption(Integer defaultValue, Integer maxValue, Integer minValue,
			Function<ClientConfig, ConfigValue<Integer>> cache, Component name, Component description) {
		super(defaultValue, cache, name, description);

		this.maxValue = maxValue;
		this.minValue = minValue;
	}

	@Override
	public OptionWidget<Integer, IOption<Integer>> createWidget(int startX, int startY, int sizeX, int sizeY) {
		return new Widget(new Rect2i(startX, startY, sizeX, sizeY), this);
	}

	@Override
	public Integer getMax() {
		return this.maxValue;
	}

	@Override
	public Integer getMin() {
		return this.minValue;
	}

	protected static class Widget extends OptionWidget<Integer, IOption<Integer>> {
		private double thumbPos;

		public Widget(Rect2i rect, IOption<Integer> option) {
			super(rect, option);

			this.loadThumbPos();
		}

		@Override
		protected void drawValue(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
			this.drawInt(matrixStack, mouseX, mouseY, partialTicks);
			if (this.hovered) {
				this.drawSlider(matrixStack, mouseX, mouseY, partialTicks);
			}
		}

		@Override
		protected int getValueWidth() {
			return this.getSliderSize() + 40;
		}

		private void drawInt(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
			String valueText = this.option.getValue().toString();
			int drawX = this.rect.getX() + this.rect.getWidth() - 6 - this.font.width(valueText) - this.getSliderSize()
					+ (this.hovered ? -4 : 0);
			int drawY = this.rect.getY() + this.rect.getHeight() / 2 - 4;

			this.drawText(matrixStack, valueText, drawX, drawY, 0xFFFFFFFF);
		}

		private void drawSlider(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
			int startX = this.rect.getX() + this.rect.getWidth() - this.getSliderSize() - 6;
			int startY = this.rect.getY() + this.rect.getHeight() / 2;
			int endX = startX + this.getSliderSize();
			int endY = startY + 1;
			int color = 0xFFFFFFFF;

			this.drawRect(matrixStack, startX, startY, endX, endY, color);

			this.loadThumbPos();
			int thumbStartX = (int) (startX + this.thumbPos * this.getSliderSize());
			int thumbStartY = startY - 5;
			int thumbEndX = thumbStartX + 2;
			int thumbEndY = thumbStartY + 10;

			this.drawRect(matrixStack, thumbStartX, thumbStartY, thumbEndX, thumbEndY, color);
		}

		private void loadThumbPos() {
			if (this.option instanceof RangeOption optionRange) {
				this.thumbPos = (double) (this.modifiedValue - optionRange.getMin())
						/ (double) (optionRange.getMax() - optionRange.getMin());
			}
		}

		private int getSliderSize() {
			if (this.hovered) {
				return Math.min(90, (int) (this.touchedTick * 18));
			} else {
				return Math.max(0, (int) ((-this.leftTick + 5.0D) * 24));
			}
		}
	}
}
