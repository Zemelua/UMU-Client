package io.github.zemelua.umu_client.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;

public class ClientConfig {
	private static final boolean ENABLE_DYNAMIC_LIGHT_DEFAULT = true;

	private final ConfigValue<Boolean> enableDynamicLight;

	public ClientConfig(ForgeConfigSpec.Builder builder) {
		builder.push("video");

		this.enableDynamicLight = builder.comment("Enable dynamic light")
				.define("enable_dynamic_light", ENABLE_DYNAMIC_LIGHT_DEFAULT);
	}

	public ConfigValue<Boolean> getEnableDynamicLight() {
		return this.enableDynamicLight;
	}
}
