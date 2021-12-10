package io.github.zemelua.umu_client.option;

import io.github.zemelua.umu_client.UMUClient;
import io.github.zemelua.umu_client.config.ModConfig;
import io.github.zemelua.umu_client.option.enums.DynamicLightMode;
import io.github.zemelua.umu_client.option.vanilla.VanillaOptions;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;

import java.awt.*;

public final class ModOptions {
	public static final SwitchOption ENABLE_DYNAMIC_LIGHT;
	public static final EnumerationOption<DynamicLightMode> DYNAMIC_LIGHT_MODE;
	public static final SwitchOption ENABLE_FALLING_STARS;
	public static final ColorOption THEME_COLOR;

	public static final RangeOption BLOCKS_PLACE_VOLUME;
	public static final RangeOption BLOCKS_HIT_VOLUME;
	public static final RangeOption BLOCKS_BREAK_VOLUME;
	public static final RangeOption BLOCKS_STEP_VOLUME;
	public static final RangeOption BLOCKS_FALL_VOLUME;
	public static final RangeOption BLOCKS_AMBIENT_VOLUME;

	private static RangeOption.Builder volumeOption() {
		return new RangeOption.Builder()
				.defaultValue(100)
				.minValue(0)
				.maxValue(100)
				.interVal(1)
				.valueFormatter((value, options, small) -> small
						? new TextComponent(value.toString())
						: value == 0
						? CommonComponents.OPTION_OFF
						: ModOptions.percentUnit(value)
				);
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

		BLOCKS_PLACE_VOLUME = ModOptions.volumeOption()
				.name("sound.block_place_volume")
				.description("sound.block_place_volume")
				.isEnable(() -> VanillaOptions.BLOCKS_VOLUME.getValue() > 0.0D)
				.build(ModConfig::getBlocksPlaceVolume);
		BLOCKS_HIT_VOLUME = ModOptions.volumeOption()
				.name("sound.block_hit_volume")
				.description("sound.block_hit_volume")
				.isEnable(() -> VanillaOptions.BLOCKS_VOLUME.getValue() > 0.0D)
				.build(ModConfig::getBlocksHitVolume);
		BLOCKS_BREAK_VOLUME = ModOptions.volumeOption()
				.name("sound.block_break_volume")
				.description("sound.block_break_volume")
				.isEnable(() -> VanillaOptions.BLOCKS_VOLUME.getValue() > 0.0D)
				.build(ModConfig::getBlocksBreakVolume);
		BLOCKS_STEP_VOLUME = ModOptions.volumeOption()
				.name("sound.block_step_volume")
				.description("sound.block_step_volume")
				.isEnable(() -> VanillaOptions.BLOCKS_VOLUME.getValue() > 0.0D)
				.build(ModConfig::getBlocksStepVolume);
		BLOCKS_FALL_VOLUME = ModOptions.volumeOption()
				.name("sound.block_fall_volume")
				.description("sound.block_fall_volume")
				.isEnable(() -> VanillaOptions.BLOCKS_VOLUME.getValue() > 0.0D)
				.build(ModConfig::getBlocksFallVolume);
		BLOCKS_AMBIENT_VOLUME = ModOptions.volumeOption()
				.name("sound.block_ambient_volume")
				.description("sound.block_ambient_volume")
				.isEnable(() -> VanillaOptions.BLOCKS_VOLUME.getValue() > 0.0D)
				.build(ModConfig::getBlocksAmbientVolume);
	}
}
