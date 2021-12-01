package io.github.zemelua.umu_client.option;

import com.mojang.blaze3d.vertex.PoseStack;
import io.github.zemelua.umu_client.UMUClient;
import io.github.zemelua.umu_client.config.ClientConfig;
import io.github.zemelua.umu_client.gui.screen.widget.OptionWidget;
import io.github.zemelua.umu_client.util.Rect2i;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.util.Mth;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;

import java.util.function.BooleanSupplier;
import java.util.function.Function;

public class RangeOption extends IOption.BaseOption<Integer> implements IRangeOption<Integer> {
	private final Integer minValue;
	private final Integer maxValue;
	private final Integer interval;
	private final Widget.IValueFormatter<Integer, RangeOption> valueFormatter;

	public RangeOption(Integer defaultValue, Integer minValue, Integer maxValue, Integer interval,
					   Function<ClientConfig, ConfigValue<Integer>> cache, Component name, Component description,
					   Widget.IValueFormatter<Integer, RangeOption> valueFormatter, BooleanSupplier isEnable) {
		super(defaultValue, cache, name, description, isEnable);

		this.minValue = minValue;
		this.maxValue = maxValue;
		this.interval = interval;
		this.valueFormatter = valueFormatter;
	}

	@Override
	public OptionWidget<Integer, ? extends IOption<Integer>> createWidget(Rect2i rect) {
		return new Widget<>(rect, this, Double::intValue);
	}

	@Override
	public Integer getMin() {
		return this.minValue;
	}

	@Override
	public Integer getMax() {
		return this.maxValue;
	}

	@Override
	public Integer getInterval() {
		return this.interval;
	}

	@Override
	public Component valueFormat(Integer value, boolean small) {
		return this.valueFormatter.format(value, this, small);
	}

	public static class Builder {
		private Integer defaultValue = 0;
		private Integer minValue = 0;
		private Integer maxValue = 0;
		private Integer interval = 0;
		private Component name = TextComponent.EMPTY;
		private Component description = TextComponent.EMPTY;
		private Widget.IValueFormatter<Integer, RangeOption> valueFormatter = (value, options, small) -> new TextComponent(value.toString());
		private BooleanSupplier isEnable = () -> true;

		public Builder defaultValue(Integer defaultValue) {
			this.defaultValue = defaultValue;
			return this;
		}

		public Builder minValue(Integer minValue) {
			this.minValue = minValue;
			return this;
		}

		public Builder maxValue(Integer maxValue) {
			this.maxValue = maxValue;
			return this;
		}

		public Builder interVal(Integer interval) {
			this.interval = interval;
			return this;
		}

		public Builder name(String name) {
			this.name = UMUClient.component("option." + name);
			this.description(name);
			return this;
		}

		public Builder description(String description) {
			this.description = UMUClient.component("option." + description + ".description");
			return this;
		}

		public Builder valueFormatter(Widget.IValueFormatter<Integer, RangeOption> valueFormatter) {
			this.valueFormatter = valueFormatter;
			return this;
		}

		public Builder isEnable(BooleanSupplier isEnable) {
			this.isEnable = isEnable;
			return this;
		}

		public RangeOption build(Function<ClientConfig, ConfigValue<Integer>> cache) {
			return new RangeOption(this.defaultValue, this.minValue, this.maxValue, this.interval, cache,
					this.name, this.description, this.valueFormatter, this.isEnable);
		}
	}

	public static class Widget<T extends Number, O extends IOption<T> & IRangeOption<T>> extends OptionWidget<T, O> {
		private final Function<Double, T> converter;

		private double thumbPos;

		public Widget(Rect2i rect, O option, Function<Double, T> converter) {
			super(rect, option);

			this.converter = converter;
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
			Component valueText = this.option.valueFormat(this.modifiableValue, this.getSliderSize() > 0);
			int drawX = this.rect.getLimitX() - 6 - this.font.width(valueText) - this.getSliderSize()
					- this.getSliderSize() / 90 * 4;
			int drawY = this.rect.getMiddleY() - 4;

			this.drawText(matrixStack, valueText, drawX, drawY, this.getValueColor());
		}

		protected void drawSlider(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
			int startX = this.rect.getX() + this.rect.getWidth() - this.getSliderSize() - 6;
			int startY = this.rect.getY() + this.rect.getHeight() / 2;
			int endX = startX + this.getSliderSize();
			int endY = startY + 1;
			int color = this.getValueColor();

			this.drawRect(matrixStack, startX, startY, endX, endY, color);
		}

		protected void drawThumb(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
			double thumbOffset = Mth.clamp((this.modifiableValue.doubleValue() - this.option.getMin().doubleValue())
					/ (this.option.getMax().doubleValue() - this.option.getMin().doubleValue()) * this.getSliderSize(),
					0, this.getSliderSize());
			int thumbStartX = (int) (this.rect.getLimitX() - 6 - this.getSliderSize() + thumbOffset);
			int thumbStartY = this.rect.getY() + this.rect.getHeight() / 2 - 5;
			int thumbEndX = thumbStartX + 2;
			int thumbEndY = thumbStartY + 10;
			int color = this.getValueColor();

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
			double interval = this.option.getInterval().doubleValue();
			double value = this.option.getMin().doubleValue()
					+ valuePercent * (this.option.getMax().doubleValue() - this.option.getMin().doubleValue());

			double valueRounded = Math.round(value / interval) * interval;

			this.modifiableValue = this.converter.apply(valueRounded);
		}

		private double getThumbPos() {
			double value = Mth.clamp(this.modifiableValue.doubleValue(),
					this.option.getMin().doubleValue(), this.option.getMax().doubleValue());
			double interval = this.option.getInterval().doubleValue();

			return Math.ceil(value / interval) * interval
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

		public interface IValueFormatter<T extends Number, O extends IOption<T> & IRangeOption<T>> {
			Component format(T value, O option, boolean small);
		}
	}
}
