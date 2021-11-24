package io.github.zemelua.umu_client.option;

import io.github.zemelua.umu_client.gui.screen.widget.OptionWidget;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.ProgressOption;
import net.minecraft.network.chat.Component;

public class VanillaRangeOption implements IOption<Double> {
	private final ProgressOption option;
	private final Options options;
	private final Component description;

	public VanillaRangeOption(ProgressOption option, Component description) {
		this.option = option;
		this.options = Minecraft.getInstance().options;
		this.description = description;
	}

	@Override
	public Component getName() {
		return this.option.getMessage(this.options);
	}

	@Override
	public Component getDescription() {
		return this.description;
	}

	@Override
	public OptionWidget<IOption<Double>> createWidget(int startX, int startY) {
		return null;
	}

	@Override
	public Double getValue() {
		return this.option.get(this.options);
	}

	@Override
	public void setValue(Double value) {
		this.option.set(this.options, value);
	}

	@Override
	public void reset() {

	}
}
