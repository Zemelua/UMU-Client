package io.github.zemelua.umu_client.option.vanilla;

import io.github.zemelua.umu_client.gui.screen.widget.OptionWidget;
import io.github.zemelua.umu_client.option.IOption;
import io.github.zemelua.umu_client.option.IRangeOption;
import io.github.zemelua.umu_client.option.RangeOption;
import io.github.zemelua.umu_client.util.Rect2i;
import net.minecraft.client.Options;
import net.minecraft.client.ProgressOption;
import net.minecraft.network.chat.Component;

import java.util.function.BiConsumer;
import java.util.function.Function;

public class RangeVanillaOption extends BaseVanillaOption<Double> implements IRangeOption<Double> {
	private final Double maxValue;
	private final Double minValue;
	private final RangeOption.Widget.IValueFormatter<Double, RangeVanillaOption> valueFormatter;

	public RangeVanillaOption(Double defaultValue, ProgressOption parent, Component name, Component description,
							  RangeOption.Widget.IValueFormatter<Double, RangeVanillaOption> valueFormatter) {
		this(defaultValue, parent.getMinValue(), parent.getMaxValue(), parent::get, parent::set, name, description, valueFormatter);
	}

	public RangeVanillaOption(Double defaultValue, Double minValue, Double maxValue, Function<Options, Double> getter,
							  BiConsumer<Options, Double> setter, Component name, Component description,
							  RangeOption.Widget.IValueFormatter<Double, RangeVanillaOption> valueFormatter) {
		super(defaultValue, getter, setter, name, description);
		this.maxValue = maxValue;
		this.minValue = minValue;
		this.valueFormatter = valueFormatter;
	}

	@Override
	public OptionWidget<Double, ? extends IOption<Double>> createWidget(Rect2i rect) {
		return new RangeOption.Widget<>(rect, this, Double::doubleValue);
	}

	@Override
	public Component valueFormat(Double value, boolean small) {
		return this.valueFormatter.format(value, this, small);
	}

	@Override
	public Double getMax() {
		return this.maxValue;
	}

	@Override
	public Double getMin() {
		return this.minValue;
	}
}
