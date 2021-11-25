package io.github.zemelua.umu_client.option;

import io.github.zemelua.umu_client.UMUClient;
import io.github.zemelua.umu_client.config.ClientConfig;
import io.github.zemelua.umu_client.gui.screen.widget.OptionWidget;
import net.minecraft.network.chat.Component;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;

import java.util.function.Function;

public interface IOption<T> {
	T getValue();

	void setValue(T value);

	Component getName();

	Component getDescription();

	OptionWidget<T, ? extends IOption<T>> createWidget(int startX, int startY, int sizeX, int sizeY);

	void load();

	void save();

	abstract class BaseOption<T> implements IOption<T> {
		private final T defaultValue;
		private final Function<ClientConfig, ConfigValue<T>> cache;
		private final Component name;
		private final Component description;

		private T value;

		public BaseOption(T defaultValue, Function<ClientConfig, ConfigValue<T>> cache, Component name, Component description) {
			this.defaultValue = defaultValue;
			this.cache = cache;
			this.name = name;
			this.description = description;

			this.value = this.defaultValue;
		}

		@Override
		public T getValue() {
			return this.value;
		}

		@Override
		public void setValue(T value) {
			this.value = value;
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
		public void load() {
			this.value = this.cache.apply(UMUClient.CONFIG_CLIENT).get();
		}

		@Override
		public void save() {
			this.cache.apply(UMUClient.CONFIG_CLIENT).set(this.value);
		}

		public T getDefaultValue() {
			return this.defaultValue;
		}
	}
}
