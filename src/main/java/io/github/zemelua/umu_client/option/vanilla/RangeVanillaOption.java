package io.github.zemelua.umu_client.option.vanilla;

import io.github.zemelua.umu_client.UMUClient;
import io.github.zemelua.umu_client.gui.screen.widget.OptionWidget;
import io.github.zemelua.umu_client.option.IOption;
import io.github.zemelua.umu_client.option.IRangeOption;
import io.github.zemelua.umu_client.option.RangeOption;
import io.github.zemelua.umu_client.option.RangeOption.Widget.IValueFormatter;
import io.github.zemelua.umu_client.util.Rect2i;
import net.minecraft.client.Options;
import net.minecraft.client.ProgressOption;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundSource;

import java.util.function.BiConsumer;
import java.util.function.Function;

public class RangeVanillaOption extends BaseVanillaOption<Double> implements IRangeOption<Double> {
	private final Double minValue;
	private final Double maxValue;
	private final Double interval;
	private final IValueFormatter<Double, RangeVanillaOption> valueFormatter;

	protected RangeVanillaOption(Double defaultValue, Double minValue, Double maxValue, Double interval,
								 Function<Options, Double> getter, BiConsumer<Options, Double> setter,
								 Component name, Component description, IValueFormatter<Double, RangeVanillaOption> valueFormatter) {
		super(defaultValue, getter, setter, name, description);

		this.minValue = minValue;
		this.maxValue = maxValue;
		this.interval = interval;
		this.valueFormatter = valueFormatter;
	}

	@Override
	public Double getMaxValue() {
		return this.maxValue;
	}

	@Override
	public Double getMinValue() {
		return this.minValue;
	}

	@Override
	public Double getInterval() {
		return this.interval;
	}

	@Override
	public OptionWidget<Double, ? extends IOption<Double>> createWidget(Rect2i rect) {
		return new RangeOption.Widget<>(rect, this, Double::doubleValue);
	}

	@Override
	public Component valueFormat(Double value, boolean small) {
		return this.valueFormatter.format(value, this, small);
	}

	public static class Builder {
		private Double defaultValue = 0.0D;
		private Double minValue = 0.0D;
		private Double maxValue = 0.0D;
		private Double interval = 0.0D;
		private Component name = TextComponent.EMPTY;
		private Component description = TextComponent.EMPTY;
		private IValueFormatter<Double, RangeVanillaOption> valueFormatter = (value, options, small) -> new TextComponent(value.toString());

		public Builder defaultValue(Double defaultValue) {
			this.defaultValue = defaultValue;
			return this;
		}

		public Builder minValue(Double minValue) {
			this.minValue = minValue;
			return this;
		}

		public Builder maxValue(Double maxValue) {
			this.maxValue = maxValue;
			return this;
		}

		public Builder interVal(Double interval) {
			this.interval = interval;
			return this;
		}

		public Builder name(String name) {
			this.name = new TranslatableComponent(name);
			return this;
		}

		public Builder description(String description) {
			this.description = UMUClient.component("option." + description + ".description");
			return this;
		}

		public Builder description(Component description) {
			this.description = description;
			return this;
		}

		public Builder valueFormatter(IValueFormatter<Double, RangeVanillaOption> valueFormatter) {
			this.valueFormatter = valueFormatter;
			return this;
		}

		public RangeVanillaOption build(Function<Options, Double> getter, BiConsumer<Options, Double> setter) {
			return new RangeVanillaOption(this.defaultValue, this.minValue, this.maxValue, this.interval,
					getter, setter, this.name, this.description, this.valueFormatter);
		}

		public RangeVanillaOption build(ProgressOption parent) {
			return new RangeVanillaOption(this.defaultValue, parent.getMinValue(), parent.getMaxValue(), this.interval,
					parent::get, parent::set, this.name, this.description, this.valueFormatter);
		}

		public RangeVanillaOption build(SoundSource soundSource) {
			return this.minValue(0.0D)
					.maxValue(1.0D)
					.name("soundCategory." + soundSource.getName())
					.description("sound." + soundSource.getName() + "_volume")
					.build(options -> (double) options.getSoundSourceVolume(soundSource),
							(options, value) -> options.setSoundCategoryVolume(soundSource, value.floatValue()));
		}
	}
}
