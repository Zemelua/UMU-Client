package io.github.zemelua.umu_client.option;

import io.github.zemelua.umu_client.gui.screen.widget.OptionWidget;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.network.chat.Component;

import java.util.function.BiConsumer;
import java.util.function.Function;

public class VanillaSwitchOption implements IOption<Boolean> {
	private final Function<Options, Boolean> getter;
	private final BiConsumer<Options, Boolean> setter;
	private final Options options;
	private final Component name;
	private final Component description;

	public VanillaSwitchOption(
			Function<Options, Boolean> getter, BiConsumer<Options, Boolean> setter,
			Component name, Component description
	) {
		this.getter = getter;
		this.setter = setter;
		this.options = Minecraft.getInstance().options;
		this.name = name;
		this.description = description;
	}

	@Override
	public Component getName() {
		return this.name;
	}

	@Override
	public Component getDescription() {
		return this.description;
	}

	@Override
	public OptionWidget<IOption<Boolean>> createWidget(int startX, int startY) {
		return null;
	}

	@Override
	public Boolean getValue() {
		return this.getter.apply(this.options);
	}

	@Override
	public void setValue(Boolean value) {
		this.setter.accept(this.options, value);
	}

	@Override
	public void reset() {

	}
}
