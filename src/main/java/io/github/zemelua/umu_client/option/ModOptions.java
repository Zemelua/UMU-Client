package io.github.zemelua.umu_client.option;

import net.minecraft.network.chat.TextComponent;

public final class ModOptions {
	public static final IOption<Boolean> DYNAMIC_LIGHT = new SwitchOption(
			true, new TextComponent("dynamiclight"), new TextComponent("des")
	);
}
