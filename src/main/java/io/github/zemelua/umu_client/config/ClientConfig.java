package io.github.zemelua.umu_client.config;

import io.github.zemelua.umu_client.option.ModOptions;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.*;

public class ClientConfig {
	private static final boolean ENABLE_DYNAMIC_LIGHT_DEFAULT = true;

	private final BooleanValue dynamicLightEnable;
	private final IntValue dynamicLightBrightness;
	private final IntValue themeColor;

	public ClientConfig(ForgeConfigSpec.Builder builder) {
		builder.push("video");

		this.dynamicLightEnable = builder.comment(ModOptions.DYNAMIC_LIGHT.getDescription().getString())
				.define(ModOptions.DYNAMIC_LIGHT.getName().getString(), ENABLE_DYNAMIC_LIGHT_DEFAULT);
		this.dynamicLightBrightness = builder.comment(ModOptions.DYNAMIC_LIGHT_BRIGHTNESS.getDescription().getString())
				.defineInRange(ModOptions.DYNAMIC_LIGHT_BRIGHTNESS.getName().getString(), 15, 1, 15);
		this.themeColor = builder.comment("")
				.defineInRange("theme_color", 46 / 255, 0, 255);
	}

	public BooleanValue getDynamicLightEnable() {
		return this.dynamicLightEnable;
	}

	public IntValue getDynamicLightBrightness() {
		return this.dynamicLightBrightness;
	}

	public IntValue getThemeColor() {
		return this.themeColor;
	}
}
