package io.github.zemelua.umu_client.option;

import io.github.zemelua.umu_client.gui.screen.widget.OptionWidget;
import net.minecraft.network.chat.Component;

public class RangeOption implements IOption<Integer> {
	private final Component name;
	private final Component description;

	public RangeOption(Component name, Component description) {
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
	public OptionWidget<IOption<Integer>> createWidget(int startX, int startY) {
		return null;
	}

	@Override
	public Integer getValue() {
		return null;
	}

	@Override
	public void setValue(Integer value) {

	}

	@Override
	public void reset() {

	}

	@Override
	public void save() {

	}
}
