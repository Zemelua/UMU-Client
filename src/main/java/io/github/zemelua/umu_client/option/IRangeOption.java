package io.github.zemelua.umu_client.option;

import net.minecraft.util.Mth;

public interface IRangeOption<T extends Number> {
	T getMax();

	T getMin();

	T getInterval();

	default double getRange() {
		return this.getMax().doubleValue() - this.getMin().doubleValue();
	}

	default double toPercent(double value) {
		double rounded = value;

		if (this.getInterval().doubleValue() > 0.0D) {
			rounded = (this.getInterval().doubleValue() * (float)Math.round(rounded / this.getInterval().doubleValue()));
		}

		rounded = Mth.clamp(rounded, this.getMin().doubleValue(), this.getMax().doubleValue());

		return Mth.clamp((rounded - this.getMin().doubleValue()) / this.getRange(), 0.0D, 1.0D);
	}
}
