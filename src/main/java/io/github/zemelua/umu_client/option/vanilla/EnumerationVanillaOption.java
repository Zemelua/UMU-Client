package io.github.zemelua.umu_client.option.vanilla;

import io.github.zemelua.umu_client.UMUClient;
import io.github.zemelua.umu_client.gui.screen.widget.OptionWidget;
import io.github.zemelua.umu_client.option.EnumerationOption;
import io.github.zemelua.umu_client.option.IEnumerationOption;
import io.github.zemelua.umu_client.option.IOption;
import io.github.zemelua.umu_client.util.Rect2i;
import net.minecraft.client.Options;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;

import javax.annotation.Nullable;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class EnumerationVanillaOption<T> extends BaseVanillaOption<T> implements IEnumerationOption<T> {
	private final T[] values;
	private final Function<T, Component> valueFormatter;

	protected EnumerationVanillaOption(T defaultValue, T[] values, Function<Options, T> getter, BiConsumer<Options, T> setter,
									   Component name, Component description, Function<T, Component> valueFormatter) {
		super(defaultValue, getter, setter, name, description);

		this.values = values;
		this.valueFormatter = valueFormatter;
	}

	@Override
	public T[] getValues() {
		return this.values;
	}

	@Override
	public OptionWidget<T, ? extends IOption<T>> createWidget(Rect2i rect) {
		return new EnumerationOption.Widget<>(rect, this);
	}

	@Override
	public Component valueFormat(T value, boolean small) {
		return this.valueFormatter.apply(value);
	}

	public static class Builder<T> {
		@Nullable private T defaultValue = null;
		@Nullable private T[] values = null;
		private Component name = TextComponent.EMPTY;
		private Component description = TextComponent.EMPTY;
		private Function<T, Component> valueFormatter = value -> new TextComponent(value.toString());

		public Builder<T> defaultValue(T defaultValue) {
			this.defaultValue = defaultValue;
			return this;
		}

		public Builder<T> values(T[] values) {
			this.values = values;
			return this;
		}

		public Builder<T> name(String name) {
			this.name = new TranslatableComponent(name);
			return this;
		}

		public Builder<T> description(String description) {
			this.description = UMUClient.component("option." + description + ".description");
			return this;
		}

		public Builder<T> description(Component description) {
			this.description = description;
			return this;
		}

		public Builder<T> valueFormatter(Function<T, Component> valueFormatter) {
			this.valueFormatter = valueFormatter;
			return this;
		}

		public EnumerationVanillaOption<T> build(Function<Options, T> getter, BiConsumer<Options, T> setter) {
			if (this.defaultValue == null) throw new IllegalStateException("Default value must not empty!");
			if (this.values == null) throw new IllegalStateException("Values must not empty!");

			return new EnumerationVanillaOption<>(this.defaultValue, this.values,
					getter, setter, this.name, this.description, this.valueFormatter);
		}
	}
}
