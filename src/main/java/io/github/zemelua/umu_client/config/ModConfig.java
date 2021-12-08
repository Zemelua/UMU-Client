package io.github.zemelua.umu_client.config;

import io.github.zemelua.umu_client.option.IEnumerationOption;
import io.github.zemelua.umu_client.option.IOption;
import io.github.zemelua.umu_client.option.IRangeOption;
import io.github.zemelua.umu_client.option.ModOptions;
import io.github.zemelua.umu_client.option.enums.DynamicLightMode;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.EnumValue;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;

public class ModConfig {
	private final BooleanValue enableDynamicLight;
	private final EnumValue<DynamicLightMode> dynamicLightMode;
	private final BooleanValue enableFallingStars;
	private final IntValue themeColor;

	public ModConfig(ForgeConfigSpec.Builder builder) {
		builder.push("video");

		this.enableDynamicLight = ModConfig.define(builder, ModOptions.ENABLE_DYNAMIC_LIGHT);
		this.dynamicLightMode = ModConfig.defineEnum(builder, ModOptions.DYNAMIC_LIGHT_MODE);
		this.enableFallingStars = ModConfig.define(builder, ModOptions.ENABLE_FALLING_STARS);
		this.themeColor = ModConfig.defineInRange(builder, ModOptions.THEME_COLOR);
	}

	public BooleanValue getEnableDynamicLight() {
		return this.enableDynamicLight;
	}

	public EnumValue<DynamicLightMode> getDynamicLightMode() {
		return this.dynamicLightMode;
	}

	public BooleanValue getEnableFallingStars() {
		return this.enableFallingStars;
	}

	public IntValue getThemeColor() {
		return this.themeColor;
	}

	private static <T extends IOption<Boolean>> BooleanValue define(ForgeConfigSpec.Builder builder, T parent) {
		return builder.define(parent.getName().getString(), parent.getDefaultValue().booleanValue());
	}

	private static <T extends IOption<Integer> & IRangeOption<Integer>> IntValue defineInRange(
			ForgeConfigSpec.Builder builder, T parent) {
		return builder.defineInRange(parent.getName().getString(), parent.getDefaultValue(), parent.getMinValue(), parent.getMaxValue());
	}

	private static <T extends IOption<E> & IEnumerationOption<E>, E extends Enum<E>> EnumValue<E> defineEnum(
			ForgeConfigSpec.Builder builder, T parent) {
		return builder.defineEnum(parent.getName().getString(), parent.getDefaultValue());
	}
}
