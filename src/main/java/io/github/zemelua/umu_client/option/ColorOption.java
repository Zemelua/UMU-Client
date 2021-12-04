package io.github.zemelua.umu_client.option;

import com.mojang.blaze3d.vertex.PoseStack;
import io.github.zemelua.umu_client.UMUClient;
import io.github.zemelua.umu_client.config.ModConfig;
import io.github.zemelua.umu_client.gui.screen.widget.OptionWidget;
import io.github.zemelua.umu_client.util.Rect2i;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.function.BooleanSupplier;
import java.util.function.Function;

public class ColorOption extends RangeOption {
	private final Function<Integer, Integer> colorGetter;

	public ColorOption(Integer defaultValue, Function<Integer, Integer> colorGetter,
					   Function<ModConfig, ForgeConfigSpec.ConfigValue<Integer>> cache, Component name, Component description,
					   BooleanSupplier isEnable) {
		super(defaultValue, 0, 255, 1, cache, name, description, (value, options, small) -> TextComponent.EMPTY, isEnable);

		this.colorGetter = colorGetter;
	}

	@Override
	public OptionWidget<Integer, ? extends IOption<Integer>> createWidget(Rect2i rect) {
		return new Widget(rect, this);
	}

	public int getColor() {
		return this.colorGetter.apply(this.getValue());
	}

	public int getColor(int value) {
		return this.colorGetter.apply(value);
	}

	public static class Builder {
		private Integer defaultValue = 0;
		private Function<Integer, Integer> colorGetter = value -> this.defaultValue;
		private Component name = TextComponent.EMPTY;
		private Component description = TextComponent.EMPTY;
		private BooleanSupplier isEnable = () -> true;

		public Builder defaultValue(Integer defaultValue) {
			this.defaultValue = defaultValue;
			return this;
		}

		public Builder colorGetter(Function<Integer, Integer> colorGetter) {
			this.colorGetter = colorGetter;
			return this;
		}

		public Builder name(String name) {
			this.name = UMUClient.component("option." + name);
			return this;
		}

		public Builder description(String description) {
			this.description = UMUClient.component("option." + description + ".description");
			return this;
		}

		public Builder isEnable(BooleanSupplier isEnable) {
			this.isEnable = isEnable;
			return this;
		}

		public ColorOption build(Function<ModConfig, ForgeConfigSpec.ConfigValue<Integer>> cache) {

			return new ColorOption(this.defaultValue, this.colorGetter, cache, this.name, this.description, this.isEnable);
		}
	}

	protected static class Widget extends RangeOption.Widget<Integer, ColorOption> {
		public Widget(Rect2i rect, ColorOption option) {
			super(rect, option, Double::intValue);
		}

		@Override
		protected void drawValue(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
			if (this.getSliderSize() > 0) {
				this.drawSlider(matrixStack, mouseX, mouseY, partialTicks);
				this.drawThumb(matrixStack, mouseX, mouseY, partialTicks);
			} else {
				this.drawCompact(matrixStack, mouseX, mouseY, partialTicks);
			}
		}

		@Override
		protected void drawCompact(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
			if (this.getSliderSize() > 0) return;

			int startX = this.rect.getX() + this.rect.getWidth() - 16;
			int startY = this.rect.getY() + this.rect.getHeight() / 2 - 5;
			int endX = startX + 10;
			int endY = startY + 10;
			int color = 0xFFFFFFFF;

			this.drawRectOutline(matrixStack, startX, startY, endX, endY, color);
			this.drawRect(matrixStack, startX + 1, startY + 1, endX - 1, endY - 1, this.option.getColor(this.modifiableValue));
		}

		@Override
		protected void drawSlider(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
			if (this.getSliderSize() <= 0) return;

			int startX = this.rect.getX() + this.rect.getWidth() - this.getSliderSize() - 6;
			int startY = this.rect.getY() + this.rect.getHeight() / 2 - 5;
			int endX = startX + this.getSliderSize();
			int endY = startY + 10;
			int color = 0xFFFFFFFF;

			this.drawRectOutline(matrixStack, startX, startY, endX, endY, color);

			int gradationSize = this.getSliderSize() - 2;
			for (int i = 0; i < gradationSize; i++) {
				int colorGradation = this.option.getColor((int) ((float) i / (float) gradationSize * 255));

				this.drawRect(matrixStack, startX + 1 + i, startY + 1, startX + 2 + i, endY - 1, colorGradation);
			}
		}

		@Override
		protected int getValueWidth() {
			return this.getSliderSize() + 20;
		}
	}
}
