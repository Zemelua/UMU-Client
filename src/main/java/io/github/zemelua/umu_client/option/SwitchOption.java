package io.github.zemelua.umu_client.option;

import com.mojang.blaze3d.vertex.PoseStack;
import io.github.zemelua.umu_client.UMUClient;
import io.github.zemelua.umu_client.config.ClientConfig;
import io.github.zemelua.umu_client.gui.screen.widget.OptionWidget;
import io.github.zemelua.umu_client.util.Rect2i;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;

import java.util.function.BooleanSupplier;
import java.util.function.Function;

public class SwitchOption extends IOption.BaseOption<Boolean> {
	public SwitchOption(Boolean defaultValue, Function<ClientConfig, ConfigValue<Boolean>> cache, Component name, Component description,
						BooleanSupplier isEnable) {
		super(defaultValue, cache, name, description, isEnable);
	}

	@Override
	public OptionWidget<Boolean, ? extends IOption<Boolean>> createWidget(Rect2i rect) {
		return new Widget(rect, this);
	}

	public static class Builder {
		private Boolean defaultValue = true;
		private Component name = TextComponent.EMPTY;
		private Component description = TextComponent.EMPTY;
		private BooleanSupplier isEnable = () -> true;

		public Builder defaultValue(Boolean defaultValue) {
			this.defaultValue = defaultValue;
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

		public Builder isEnable(BooleanSupplier isEnable) {
			this.isEnable = isEnable;
			return this;
		}

		public SwitchOption build(Function<ClientConfig, ConfigValue<Boolean>> cache) {
			return new SwitchOption(this.defaultValue, cache, this.name, this.description, this.isEnable);
		}
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
			int color = this.getValueColor();

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
