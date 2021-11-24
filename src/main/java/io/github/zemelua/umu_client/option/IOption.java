package io.github.zemelua.umu_client.option;

import io.github.zemelua.umu_client.gui.screen.widget.OptionWidget;
import net.minecraft.network.chat.Component;

public interface IOption<T> {
	T getValue();

	void setValue(T value);

	void reset();

	Component getName();

	Component getDescription();

	OptionWidget<? extends IOption<T>> createWidget(int startX, int startY);
}
