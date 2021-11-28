package io.github.zemelua.umu_client.option;

import io.github.zemelua.umu_client.UMUClient;
import io.github.zemelua.umu_client.config.ClientConfig;
import io.github.zemelua.umu_client.gui.screen.widget.OptionWidget;
import io.github.zemelua.umu_client.util.Rect2i;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;

import java.util.function.Function;

public interface IOption<T> {
	T getValue();

	void setValue(T value);

	void reset();

	T getDefaultValue();

	Component getName();

	Component getDescription();

	OptionWidget<T, ? extends IOption<T>> createWidget(Rect2i rect);

	default Component valueFormat(T value, boolean small) {
		return new TextComponent(value.toString());
	}

	abstract class BaseOption<T> implements IOption<T> {
		protected final T defaultValue;
		private final Function<ClientConfig, ConfigValue<T>> cache;
		private final Component name;
		private final Component description;

		public BaseOption(T defaultValue, Function<ClientConfig, ConfigValue<T>> cache, Component name, Component description) {
			this.defaultValue = defaultValue;
			this.cache = cache;
			this.name = name;
			this.description = description;
		}

		@Override
		public T getValue() {
			return this.cache.apply(UMUClient.CONFIG_CLIENT).get();
		}

		@Override
		public void setValue(T value) {
			this.cache.apply(UMUClient.CONFIG_CLIENT).set(value);
			this.cache.apply(UMUClient.CONFIG_CLIENT).save();
		}

		@Override
		public Component getName() {
			return this.name;
		}

		@Override
		public Component getDescription() {
			return this.description;
		}

		@Override
		public void reset() {
			this.setValue(this.defaultValue);
		}

		@Override
		public T getDefaultValue() {
			return this.defaultValue;
		}
	}
}
