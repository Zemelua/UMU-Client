package io.github.zemelua.umu_client.gui.screen.option;

import com.google.common.collect.ImmutableList;
import io.github.zemelua.umu_client.option.IOption;
import net.minecraft.network.chat.Component;

public class ModOptionPage {
	private final Component title;
	private final ImmutableList<ImmutableList<IOption<?>>> optionGroups;

	public ModOptionPage(Component title, ImmutableList<ImmutableList<IOption<?>>> optionGroups) {
		this.title = title;
		this.optionGroups = optionGroups;
	}

	public Component getTitle() {
		return this.title;
	}

	public ImmutableList<ImmutableList<IOption<?>>> getGroups() {
		return optionGroups;
	}
}
