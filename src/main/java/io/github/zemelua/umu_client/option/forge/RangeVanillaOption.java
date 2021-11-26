package io.github.zemelua.umu_client.option.forge;

import io.github.zemelua.umu_client.gui.screen.widget.OptionWidget;
import io.github.zemelua.umu_client.option.IOption;
import io.github.zemelua.umu_client.option.IRangeOption;
import io.github.zemelua.umu_client.option.RangeOption;
import net.minecraft.client.ProgressOption;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;

import java.util.function.BiFunction;
import java.util.function.Function;

public class RangeVanillaOption<T extends Number> extends BaseVanillaOption<T> implements IRangeOption<T> {
	private final T maxValue;
	private final T minValue;
	private final Function<Double, T> converter;
	private final BiFunction<RangeVanillaOption<T>, Boolean, Component> valueAsText;

	public RangeVanillaOption(T defaultValue, ProgressOption option, Function<Double, T> converter,
							  Component name, Component description) {
		this(defaultValue, option, converter, name, description, (optionArg, compact)
				-> new TextComponent(optionArg.getModifiedValue().toString()));
	}

	public RangeVanillaOption(T defaultValue, ProgressOption option, Function<Double, T> converter,
							  Component name, Component description, BiFunction<RangeVanillaOption<T>, Boolean, Component> valueAsText) {
		super(defaultValue, options -> converter.apply(option.get(options)),
				(options, value) -> option.set(options, value.doubleValue()), name, description);

		this.maxValue = converter.apply(option.getMaxValue());
		this.minValue = converter.apply(option.getMinValue());
		this.converter = converter;
		this.valueAsText = valueAsText;
	}

	@Override
	public OptionWidget<T, ? extends IOption<T>> createWidget(int startX, int startY, int sizeX, int sizeY) {
		return new RangeOption.Widget<>(new Rect2i(startX, startY, sizeX, sizeY), this);
	}

	@Override
	public T getMax() {
		return this.maxValue;
	}

	@Override
	public T getMin() {
		return this.minValue;
	}

	@Override
	public void setModifiedValue(Double value) {
		this.setModifiedValue(converter.apply(value));
	}

	@Override
	public Component getModifiedValueAsText(boolean compact) {
		return this.valueAsText.apply(this, compact);
	}
}
