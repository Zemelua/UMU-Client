package io.github.zemelua.umu_client.option;

import io.github.zemelua.umu_client.UMUClient;
import io.github.zemelua.umu_client.config.ModConfig;
import io.github.zemelua.umu_client.option.enums.DynamicLightMode;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.common.ForgeConfigSpec;

import java.awt.*;
import java.util.function.Function;

public final class ModOptions {
	public static final SwitchOption ENABLE_DYNAMIC_LIGHT;
	public static final EnumerationOption<DynamicLightMode> DYNAMIC_LIGHT_MODE;
	public static final SwitchOption ENABLE_FALLING_STARS;
	public static final ColorOption THEME_COLOR;

	private static RangeOption percentOption(Function<ModConfig, ForgeConfigSpec.ConfigValue<Integer>> cache, String name) {
		return new RangeOption.Builder()
				.defaultValue(100)
				.minValue(0)
				.maxValue(100)
				.interVal(1)
				.name(name)
				.valueFormatter((value, options, small) -> small
						? new TextComponent(value.toString())
						: value == 0
						? CommonComponents.OPTION_OFF
						: new TranslatableComponent("umu_client.screen.options.unit.percent_value", value))
				.build(cache);
	}

	public static Component percentUnit(int value) {
		return UMUClient.component("screen.options.unit.percent", value);
	}

	public static Component pixelUnit(int value) {
		return UMUClient.component("screen.options.unit.pixel", value);
	}

	public static Component secondUnit(double value) {
		return UMUClient.component("screen.options.unit.second", String.format("%.1f", value));
	}

	public static Component fpsUnit(int value) {
		return UMUClient.component("screen.options.unit.fps", value);
	}

	public static Component chunkUnit(int value) {
		return UMUClient.component("screen.options.unit.chunk", value);
	}

	static {
		ENABLE_DYNAMIC_LIGHT = new SwitchOption.Builder()
				.defaultValue(true)
				.name("video.enable_dynamic_light")
				.description("video.enable_dynamic_light")
				.build(ModConfig::getEnableDynamicLight);
		DYNAMIC_LIGHT_MODE = new EnumerationOption.Builder<DynamicLightMode>()
				.values(DynamicLightMode.values())
				.defaultValue(DynamicLightMode.ALL)
				.name("video.dynamic_light_mode")
				.description("video.dynamic_light_mode")
				.isEnable(ENABLE_DYNAMIC_LIGHT::getValue)
				.build(ModConfig::getDynamicLightMode);
		ENABLE_FALLING_STARS = new SwitchOption.Builder()
				.defaultValue(true)
				.name("video.enable_falling_stars")
				.description("video.enable_falling_stars")
				.build(ModConfig::getEnableFallingStars);
		THEME_COLOR = new ColorOption.Builder()
				.defaultValue(118)
				.colorGetter(value -> Color.HSBtoRGB((float) value / 255.0F, 0.35F, 0.89F))
				.name("video.theme_color")
				.description("video.theme_color")
				.build(ModConfig::getThemeColor);
	}
}
