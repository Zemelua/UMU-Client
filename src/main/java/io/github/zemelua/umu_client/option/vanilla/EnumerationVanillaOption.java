package io.github.zemelua.umu_client.option.vanilla;

import io.github.zemelua.umu_client.gui.screen.widget.OptionWidget;
import io.github.zemelua.umu_client.option.EnumerationOption;
import io.github.zemelua.umu_client.option.IEnumerationOption;
import io.github.zemelua.umu_client.option.IOption;
import io.github.zemelua.umu_client.util.Rect2i;
import net.minecraft.client.Options;
import net.minecraft.network.chat.Component;

import java.util.function.BiConsumer;
import java.util.function.Function;

public class EnumerationVanillaOption<T> extends BaseVanillaOption<T> implements IEnumerationOption<T> {
	private final T[] values;
	private final Function<T, Component> valueFormatter;

	public EnumerationVanillaOption(T[] values, T defaultValue, Function<Options, T> getter, BiConsumer<Options, T> setter,
									Component name, Component description, Function<T, Component> valueFormatter) {
		super(defaultValue, getter, setter, name, description);

		this.values = values;
		this.valueFormatter = valueFormatter;
	}

	@Override
	public OptionWidget<T, ? extends IOption<T>> createWidget(Rect2i rect) {
		return new EnumerationOption.Widget<>(rect, this);
	}

	@Override
	public Component valueFormat(T value, boolean small) {
		return this.valueFormatter.apply(value);
	}

	@Override
	public boolean isEnable() {
		return true;
	}

	@Override
	public T[] getValues() {
		return this.values;
	}
}
