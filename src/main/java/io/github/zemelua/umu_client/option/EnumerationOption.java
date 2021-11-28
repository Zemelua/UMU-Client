package io.github.zemelua.umu_client.option;

import com.mojang.blaze3d.vertex.PoseStack;
import io.github.zemelua.umu_client.config.ClientConfig;
import io.github.zemelua.umu_client.gui.screen.widget.OptionWidget;
import io.github.zemelua.umu_client.option.enums.IHasComponent;
import io.github.zemelua.umu_client.util.Rect2i;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;

import java.util.function.Function;

public class EnumerationOption<T extends IHasComponent> extends IOption.BaseOption<T> implements IEnumerationOption<T> {
	private final T[] values;

	public EnumerationOption(T[] values, T defaultValue, Function<ClientConfig, ConfigValue<T>> cache,
							 Component name, Component description) {
		super(defaultValue, cache, name, description);
		this.values = values;
	}

	@Override
	public OptionWidget<T, ? extends IOption<T>> createWidget(Rect2i rect) {
		return new Widget<>(rect, this);
	}

	@Override
	public T[] getValues() {
		return this.values;
	}

	@Override
	public Component valueFormat(T value, boolean small) {
		return value.getComponent();
	}

	public static class Widget<T, O extends IOption<T> & IEnumerationOption<T>> extends OptionWidget<T, O> {
		private double fadingDepth;

		public Widget(Rect2i rect, O option) {
			super(rect, option);

			this.fadingDepth = 0.0D;
		}

		@Override
		protected void drawValue(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
			if (this.hasHovered) {
				if (this.hovered) {
					this.fadingDepth += partialTicks / 2.45D;
				} else {
					this.fadingDepth -= partialTicks / 2.45D;
				}
			}

			this.fadingDepth = Mth.clamp(this.fadingDepth, 0.0D, 1.0D);

			Component valueText = this.option.valueFormat(this.modifiableValue, fadingDepth == 0.0D);
			int valuesCount = this.option.getValues().length;
			int startXMin = this.rect.getLimitX() - 96;
			int startXMax = Math.max(startXMin, this.rect.getLimitX() - 6 - this.font.width(valueText));
			int startX = (int) (startXMax - (startXMax - startXMin) * this.fadingDepth);
			int startY = this.rect.getMiddleY() - 2;
			int middleXMin = this.rect.getLimitX() - 51;
			int middleXMax = Math.max(middleXMin, this.rect.getLimitX() - 6 - this.font.width(valueText) / 2);
			int middleX = (int) (middleXMax - (middleXMax - middleXMin) * this.fadingDepth);
			int middleY = this.rect.getMiddleY();
			int endX = this.rect.getLimitX() - 6;
			int endY = this.rect.getLimitY();
			int color = this.getValueColor();
			int fadingColor = (((int) (255 * fadingDepth) & 0xFF) << 24) | ((0xFF) << 16) | ((0xFF) << 8) | ((0xFF));
			int lineWidth = (endX - startX - (valuesCount - 1)) / valuesCount;

			this.drawTextCenter(matrixStack, valueText, middleX, middleY, color);

			this.drawArrowLeft(matrixStack, startX, startY, fadingColor);
			this.drawArrowRight(matrixStack, endX - (int) (3 * fadingDepth), startY, fadingColor);

			int lineX = startX;

			for (int i = 0; i < valuesCount; i++) {
				int lineColor = this.modifiableValue.equals(this.option.getValues()[i])
						? (((int) (255 * fadingDepth) & 0xFF) << 24)
						| ((ModOptions.THEME_COLOR.getColor() >> 16) & 0xFF) << 16
						| ((ModOptions.THEME_COLOR.getColor() >> 8) & 0xFF) << 8
						| ((ModOptions.THEME_COLOR.getColor()) & 0xFF)
						: fadingColor;

				this.drawRect(matrixStack, lineX, this.rect.getLimitY() - 1, lineX + lineWidth, endY, lineColor);

				lineX += lineWidth + 1;
			}

		}

		private void drawArrowLeft(PoseStack matrixStack, int startX, int startY, int color) {
			this.drawRect(matrixStack, startX + 2, startY, (int) (startX + 3 * fadingDepth), startY + 1, color);
			this.drawRect(matrixStack, startX + 1, startY + 1, (int) (startX + 2 * fadingDepth), startY + 2, color);
			this.drawRect(matrixStack, startX, startY + 2, (int) (startX + 1 * fadingDepth), startY + 3, color);
			this.drawRect(matrixStack, startX + 1, startY + 3, (int) (startX + 2 * fadingDepth), startY + 4, color);
			this.drawRect(matrixStack, startX + 2, startY + 4, (int) (startX + 3 * fadingDepth), startY + 5, color);
		}

		private void drawArrowRight(PoseStack matrixStack, int startX, int startY, int color) {
			this.drawRect(matrixStack, startX, startY, (int) (startX + 1 * fadingDepth), startY + 1, color);
			this.drawRect(matrixStack, startX + 1, startY + 1, (int) (startX + 2 * fadingDepth), startY + 2, color);
			this.drawRect(matrixStack, startX + 2, startY + 2, (int) (startX + 3 * fadingDepth), startY + 3, color);
			this.drawRect(matrixStack, startX + 1, startY + 3, (int) (startX + 2 * fadingDepth), startY + 4, color);
			this.drawRect(matrixStack, startX, startY + 4, (int) (startX + 1 * fadingDepth), startY + 5, color);
		}

		@Override
		public boolean mouseClicked(double mouseX, double mouseY, int button) {
			if (this.rect.contains((int) mouseX, (int) mouseY)) {
				int middleXMin = this.rect.getLimitX() - 51;
				int middleXMax = Math.max(middleXMin, this.rect.getLimitX() - 6
						- this.font.width(this.option.valueFormat(this.modifiableValue, fadingDepth == 0.0D)) / 2);
				int middleX = (int) (middleXMax - (middleXMax - middleXMin) * this.fadingDepth);

				int index = 0;
				for (int i = 0; i < this.option.getValues().length; i++) {
					if (this.option.getValues()[i].equals(this.modifiableValue)) index = i;
				}

				if (mouseX >= middleX) {
					if (index + 1 >= this.option.getValues().length) {
						this.modifiableValue = this.option.getValues()[0];
					} else {
						this.modifiableValue = this.option.getValues()[index + 1];
					}
				} else {
					if (index == 0) {
						this.modifiableValue = this.option.getValues()[this.option.getValues().length - 1];
					} else {
						this.modifiableValue = this.option.getValues()[index - 1];
					}
				}

				return true;
			}

			return false;
		}

		@Override
		protected int getValueWidth() {
			return (int) (90 * this.fadingDepth) + 20;
		}
	}
}
