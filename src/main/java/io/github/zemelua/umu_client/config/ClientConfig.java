package io.github.zemelua.umu_client.config;

import io.github.zemelua.umu_client.option.IOption;
import io.github.zemelua.umu_client.option.IRangeOption;
import io.github.zemelua.umu_client.option.ModOptions;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.*;

public class ClientConfig {
	private static final boolean ENABLE_DYNAMIC_LIGHT_DEFAULT = true;

	private final BooleanValue dynamicLightEnable;
	private final IntValue dynamicLightBrightness;
	private final IntValue themeColor;

	private final IntValue blockAmbientVolume;
	private final IntValue blockPlaceVolume;
	private final IntValue blockBreakVolume;

	public ClientConfig(ForgeConfigSpec.Builder builder) {
		builder.push("video");

		this.dynamicLightEnable = builder.comment(ModOptions.DYNAMIC_LIGHT.getDescription().getString())
				.define(ModOptions.DYNAMIC_LIGHT.getName().getString(), ENABLE_DYNAMIC_LIGHT_DEFAULT);
		this.dynamicLightBrightness = builder.comment(ModOptions.DYNAMIC_LIGHT_BRIGHTNESS.getDescription().getString())
				.defineInRange(ModOptions.DYNAMIC_LIGHT_BRIGHTNESS.getName().getString(), 15, 1, 15);
		this.themeColor = builder.comment("")
				.defineInRange("theme_color", 46 / 255, 0, 255);

		this.blockAmbientVolume = defineInRange(builder, ModOptions.BLOCK_AMBIENT_VOLUME);
		this.blockPlaceVolume = defineInRange(builder, ModOptions.BLOCK_PLACE_VOLUME);
		this.blockBreakVolume = defineInRange(builder, ModOptions.BLOCK_BREAK_VOLUME);
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

	public IntValue getBlockAmbientVolume() {
		return this.blockAmbientVolume;
	}

	public IntValue getBlockPlaceVolume() {
		return blockPlaceVolume;
	}

	public IntValue getBlockBreakVolume() {
		return this.blockBreakVolume;
	}

	private static <T extends IOption<Integer> & IRangeOption<Integer>> IntValue defineInRange(
			ForgeConfigSpec.Builder builder, T parent) {
		return builder.defineInRange(parent.getName().getString(), parent.getDefaultValue(), parent.getMin(), parent.getMax());
	}
}
