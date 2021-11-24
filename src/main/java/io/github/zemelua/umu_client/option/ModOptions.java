package io.github.zemelua.umu_client.option;

import io.github.zemelua.umu_client.UMUClient;
import net.minecraft.network.chat.TextComponent;

public final class ModOptions {
	public static final IOption<Boolean> DYNAMIC_LIGHT = new SwitchOption(
			UMUClient.CONFIG_CLIENT.getEnableDynamicLight(), new TextComponent("dynamiclight"), new TextComponent("des")
	);
}
