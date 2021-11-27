package io.github.zemelua.umu_client.option;

import com.mojang.blaze3d.vertex.PoseStack;
import io.github.zemelua.umu_client.config.ClientConfig;
import io.github.zemelua.umu_client.gui.screen.widget.OptionWidget;
import io.github.zemelua.umu_client.util.Rect2i;
import net.minecraft.network.chat.Component;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;

import java.util.function.Function;

public class SwitchOption extends IOption.BaseOption<Boolean> {
	public SwitchOption(Boolean defaultValue, Function<ClientConfig, ConfigValue<Boolean>> cache, Component name, Component description) {
		super(defaultValue, cache, name, description);
	}

	@Override
	public OptionWidget<Boolean, ? extends IOption<Boolean>> createWidget(Rect2i rect) {
		return new Widget(rect, this);
	}

	public static class Widget extends OptionWidget<Boolean, IOption<Boolean>> {
		public Widget(Rect2i rect, IOption<Boolean> option) {
			super(rect, option);
		}

		@Override
		protected void drawValue(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
			int startX = this.rect.getLimitX() - 16;
			int startY = this.rect.getMiddleY() - 5;
			int endX = startX + 10;
			int endY = startY + 10;
			int color = 0xFFFFFFFF;

			this.drawRectOutline(matrixStack, startX, startY, endX, endY, color);
			if (this.modifiableValue) {
				this.drawRect(matrixStack, startX + 2, startY + 2, endX - 2, endY - 2, color);
			}
		}

		@Override
		protected int getValueWidth() {
			return 30;
		}

		@Override
		public boolean mouseClicked(double mouseX, double mouseY, int button) {
			if (button == 0 && this.rect.contains((int) mouseX, (int) mouseY)) {
				this.modifiableValue = !this.modifiableValue;
				this.playClickSound();

				return true;
			}

			return false;
		}
	}
}
