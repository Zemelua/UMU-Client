package io.github.zemelua.umu_client.option.enums;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;

public enum DynamicLightMode implements IOptionEnum {
	ONLY_SELF("only_self"),
	ALL("all");

	private final String name;

	DynamicLightMode(String name) {
		this.name = name;
	}

	@Override
	public Component getName() {
		return new TranslatableComponent("umu_client.option.video.dynamic_light_mode.value." + this.name);
	}
}
