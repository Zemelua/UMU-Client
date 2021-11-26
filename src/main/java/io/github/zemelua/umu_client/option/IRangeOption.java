package io.github.zemelua.umu_client.option;

import net.minecraft.network.chat.Component;

public interface IRangeOption<T extends Number> {
	T getMax();

	T getMin();

	void setModifiedValue(Double value);

	Component getModifiedValueAsText(boolean compact);
}
