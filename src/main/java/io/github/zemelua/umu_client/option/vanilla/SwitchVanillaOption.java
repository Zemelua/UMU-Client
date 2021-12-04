package io.github.zemelua.umu_client.option.vanilla;

import io.github.zemelua.umu_client.UMUClient;
import io.github.zemelua.umu_client.gui.screen.widget.OptionWidget;
import io.github.zemelua.umu_client.option.IOption;
import io.github.zemelua.umu_client.option.SwitchOption;
import io.github.zemelua.umu_client.util.Rect2i;
import net.minecraft.client.Options;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;

import java.util.function.BiConsumer;
import java.util.function.Function;

public class SwitchVanillaOption extends BaseVanillaOption<Boolean> {
	protected SwitchVanillaOption(Boolean defaultValue, Function<Options, Boolean> getter, BiConsumer<Options, Boolean> setter, Component name, Component description) {
		super(defaultValue, getter, setter, name, description);
	}

	@Override
	public OptionWidget<Boolean, ? extends IOption<Boolean>> createWidget(Rect2i rect) {
		return new SwitchOption.Widget(rect, this);
	}

	public static class Builder {
		private Boolean defaultValue = true;
		private Component name = TextComponent.EMPTY;
		private Component description = TextComponent.EMPTY;

		public Builder defaultValue(Boolean defaultValue) {
			this.defaultValue = defaultValue;
			return this;
		}

		public Builder name(String name) {
			this.name = new TranslatableComponent(name);
			return this;
		}

		public Builder description(Component description) {
			this.description = description;
			return this;
		}

		public Builder description(String description) {
			this.description = UMUClient.component("option." + description + ".description");
			return this;
		}

		public SwitchVanillaOption build(Function<Options, Boolean> getter, BiConsumer<Options, Boolean> setter) {
			return new SwitchVanillaOption(this.defaultValue, getter, setter, this.name, this.description);
		}
	}
}
