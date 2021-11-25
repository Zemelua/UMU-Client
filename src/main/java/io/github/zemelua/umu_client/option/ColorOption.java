package io.github.zemelua.umu_client.option;

import com.mojang.blaze3d.vertex.PoseStack;
import io.github.zemelua.umu_client.config.ClientConfig;
import io.github.zemelua.umu_client.gui.screen.widget.OptionWidget;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.network.chat.Component;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;

import java.util.function.Function;

public class ColorOption extends RangeOption<Integer> {
	private final Function<Integer, Integer> colorGetter;

	public ColorOption(Integer defaultValue, Function<Integer, Integer> colorGetter,
					   Function<ClientConfig, ConfigValue<Integer>> cache, Component name, Component description) {
		super(defaultValue, 255, 0, cache, name, description);

		this.colorGetter = colorGetter;
	}

	@Override
	public OptionWidget<Integer, ? extends IOption<Integer>> createWidget(int startX, int startY, int sizeX, int sizeY) {
		return new Widget(new Rect2i(startX, startY, sizeX, sizeY), this);
	}

	public int getColor() {
		return this.colorGetter.apply(this.getValue());
	}

	public int getColor(int value) {
		return this.colorGetter.apply(value);
	}

	protected static class Widget extends RangeOption.Widget<Integer, ColorOption> {
		public Widget(Rect2i rect, ColorOption option) {
			super(rect, option);
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
			this.drawRect(matrixStack, startX + 1, startY + 1, endX - 1, endY - 1, this.option.getColor());
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
	}
}
