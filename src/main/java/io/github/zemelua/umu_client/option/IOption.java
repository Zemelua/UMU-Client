package io.github.zemelua.umu_client.option;

import io.github.zemelua.umu_client.gui.screen.widget.OptionWidget;
import io.github.zemelua.umu_client.util.Rect2i;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;

public interface IOption<T> {
	T getValue();

	void setValue(T value);

	default void reset() {
		this.setValue(this.getDefaultValue());
	}

	T getDefaultValue();

	Component getName();

	Component getDescription();

	OptionWidget<T, ? extends IOption<T>> createWidget(Rect2i rect);

	default Component valueFormat(T value, boolean small) {
		return new TextComponent(value.toString());
	}

	boolean isEnable();
}
