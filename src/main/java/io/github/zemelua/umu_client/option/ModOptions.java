package io.github.zemelua.umu_client.option;

import io.github.zemelua.umu_client.UMUClient;
import io.github.zemelua.umu_client.config.ClientConfig;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.common.ForgeConfigSpec;

import java.awt.*;
import java.util.function.Function;

public final class ModOptions {
	public static final SwitchOption DYNAMIC_LIGHT = new SwitchOption(
			true, ClientConfig::getDynamicLightEnable,
			UMUClient.component("option.video.dynamic_light_enable"),
			UMUClient.component("option.video.dynamic_light_enable.description")
	);
	public static final RangeOption DYNAMIC_LIGHT_BRIGHTNESS = new RangeOption(
			15, 1, 15, 1, ClientConfig::getDynamicLightBrightness,
			UMUClient.component("option.video.dynamic_light_brightness"),
			UMUClient.component("option.video.dynamic_light_brightness.description"),
			(value, options, small) -> new TextComponent(value.toString())
	);

	public static final ColorOption THEME_COLOR = new ColorOption(
			118, value -> Color.HSBtoRGB((float) value / 255.0F, 0.35F, 0.89F), ClientConfig::getThemeColor,
			UMUClient.component("option.video.theme_color"),
			UMUClient.component("option.video.theme_color.description")
	);

	public static final RangeOption BLOCK_PLACE_VOLUME = percentOption(ClientConfig::getBlockPlaceVolume, "sound.block_place_volume");
	public static final RangeOption BLOCK_BREAK_VOLUME = percentOption(ClientConfig::getBlockBreakVolume, "sound.block_break_volume");
	public static final RangeOption BLOCK_AMBIENT_VOLUME = percentOption(ClientConfig::getBlockAmbientVolume, "sound.block_ambient_volume");

	private static RangeOption percentOption(Function<ClientConfig, ForgeConfigSpec.ConfigValue<Integer>> cache, String name) {
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
}
