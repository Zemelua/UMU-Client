package io.github.zemelua.umu_client.option;

import net.minecraft.network.chat.Component;

public interface IOption<T> {
	Component getName();

	Component getDescription();

	T getValue();

	void setValue(T value);

	void reset();
}
