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

	T getModifiedValue();

	void setModifiedValue(T value);

	Component getName();

	Component getDescription();

	OptionWidget<T, ? extends IOption<T>> createWidget(int startX, int startY, int sizeX, int sizeY);

	default void load() {
		this.setModifiedValue(this.getValue());
	}

	default void save() {
		this.setValue(this.getModifiedValue());
	}

	void reset();

	boolean isChanged();

	abstract class BaseOption<T> implements IOption<T> {
		private final T defaultValue;
		private final Function<ClientConfig, ConfigValue<T>> cache;
		private final Component name;
		private final Component description;

		private T modifiedValue;

		public BaseOption(T defaultValue, Function<ClientConfig, ConfigValue<T>> cache, Component name, Component description) {
			this.defaultValue = defaultValue;
			this.cache = cache;
			this.name = name;
			this.description = description;

			this.modifiedValue = this.defaultValue;
		}

		@Override
		public T getValue() {
			return this.cache.apply(UMUClient.CONFIG_CLIENT).get();
		}

		@Override
		public void setValue(T value) {
			this.cache.apply(UMUClient.CONFIG_CLIENT).set(value);
		}

		@Override
		public T getModifiedValue() {
			return this.modifiedValue;
		}

		@Override
		public void setModifiedValue(T modifiedValue) {
			this.modifiedValue = modifiedValue;
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
//			this.cache.apply(UMUClient.CONFIG_CLIENT).clearCache();
//			this.load();
//			this.save();

			this.setModifiedValue(this.defaultValue);
			this.save();
		}

		@Override
		public boolean isChanged() {
			return !this.getValue().equals(this.getModifiedValue());
		}

		public T getDefaultValue() {
			return this.defaultValue;
		}
	}
}
