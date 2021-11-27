package io.github.zemelua.umu_client.option.vanilla;

import io.github.zemelua.umu_client.gui.screen.widget.OptionWidget;
import io.github.zemelua.umu_client.option.IOption;
import io.github.zemelua.umu_client.option.SwitchOption;
import io.github.zemelua.umu_client.util.Rect2i;
import net.minecraft.client.Options;
import net.minecraft.network.chat.Component;

import java.util.function.BiConsumer;
import java.util.function.Function;

public class SwitchVanillaOption extends BaseVanillaOption<Boolean> {
	public SwitchVanillaOption(Boolean defaultValue, Function<Options, Boolean> getter, BiConsumer<Options, Boolean> setter,
							   Component name, Component description) {
		super(defaultValue, getter, setter, name, description);
	}

	@Override
	public OptionWidget<Boolean, ? extends IOption<Boolean>> createWidget(Rect2i rect) {
		return new SwitchOption.Widget(rect, this);
	}
}
