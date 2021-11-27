package io.github.zemelua.umu_client.option;

import io.github.zemelua.umu_client.UMUClient;
import io.github.zemelua.umu_client.option.vanilla.RangeVanillaOption;
import io.github.zemelua.umu_client.option.vanilla.SwitchVanillaOption;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Option;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;

public final class VanillaOptions {
	public static final RangeVanillaOption FRAMERATE_LIMIT = new RangeVanillaOption(
			120D, Option.FRAMERATE_LIMIT,
			new TranslatableComponent("options.framerateLimit"),
			UMUClient.component("option.video.framerate_limit.description"),
			(value, option, small) -> value.equals(option.getMax())
					? new TranslatableComponent("options.framerateLimit.max")
					: new TextComponent(String.valueOf(value.intValue()))
	);
	public static final SwitchVanillaOption VSYNC_ENABLE = new SwitchVanillaOption(
			true, options -> options.enableVsync, ((options, value) -> {
		options.enableVsync = value;
		Minecraft.getInstance().getWindow().updateVsync(options.enableVsync);
	}),
			new TranslatableComponent("options.vsync"),
			UMUClient.component("option.video.vsync_enable.description")
	);
	public static final SwitchVanillaOption FULLSCREEN_ENABLE = new SwitchVanillaOption(
			false, options -> options.fullscreen, ((options, value) -> {
		options.fullscreen = value;
		Minecraft minecraft = Minecraft.getInstance();
		if (minecraft.getWindow().isFullscreen() != options.fullscreen) {
			minecraft.getWindow().toggleFullScreen();
			options.fullscreen = minecraft.getWindow().isFullscreen();
		}
	}),
			new TranslatableComponent("options.fullscreen"),
			UMUClient.component("option.video.fullscreen_enable.description")
	);

	public static final RangeVanillaOption RENDER_DISTANCE = new RangeVanillaOption(
			Minecraft.getInstance().is64Bit() ? 12D : 8D, Option.RENDER_DISTANCE,
			new TranslatableComponent("options.renderDistance"),
			UMUClient.component("option.video.render_distance.description"),
			(value, options, small) -> new TranslatableComponent("options.chunks", value)
	);
	public static final RangeVanillaOption GAMMA = new RangeVanillaOption(
			1.0D, Option.GAMMA,
			new TranslatableComponent("options.gamma"),
			UMUClient.component("option.video.gamma.description"),
			(value, options, small) -> {
				int valueInt = (int) (value * 100);
				if (small) return new TextComponent(String.valueOf(valueInt));

				if (valueInt == 0) {
					return new TranslatableComponent("options.gamma.min");
				} else if (valueInt == 100) {
					return new TranslatableComponent("options.gamma.max");
				} else {
					return new TranslatableComponent("umu_client.screen.options.unit.percent_add_value", valueInt);
				}
			}
	);
	public static final RangeVanillaOption MIPMAP_LEVELS = new RangeVanillaOption(
			4D, Option.MIPMAP_LEVELS,
			new TranslatableComponent("options.mipmapLevels"),
			UMUClient.component("option.video.mipmap_level.description"),
			(value, options, small) -> value == 0
					? CommonComponents.OPTION_OFF
					: new TextComponent(value.toString())
	);
	public static final SwitchVanillaOption ENTITY_SHADOWS = new SwitchVanillaOption(
			true, options -> options.entityShadows, ((options, value) -> options.entityShadows = value),
			new TranslatableComponent("options.entityShadows"),
			UMUClient.component("option.video.entity_shadows.description")
	);
	public static final RangeVanillaOption ENTITY_DISTANCE_SCALING = new RangeVanillaOption(
			0.5D, Option.ENTITY_DISTANCE_SCALING,
			new TranslatableComponent("options.entityDistanceScaling"),
			UMUClient.component("option.video.entity_distance_scaling.description"),
			(value, options, small) -> {
				int valueInt = (int) (value * 100);

				return small
						? new TextComponent(String.valueOf(valueInt))
						: new TranslatableComponent("umu_client.screen.options.unit.percent_value", valueInt);
			}
	);
	public static final RangeVanillaOption FOV_EFFECTS_SCALE = new RangeVanillaOption(
			1.0D, Option.FOV_EFFECTS_SCALE,
			new TranslatableComponent("options.fovEffectScale"),
			UMUClient.component("option.video.fov_effects_scale.description"),
			(value, options, small) -> {
				int valueInt = (int) (value * 100);
				if (small) return new TextComponent(String.valueOf(valueInt));

				return valueInt == 0
						? CommonComponents.OPTION_OFF
						: new TranslatableComponent("umu_client.screen.options.unit.percent_value", valueInt);
			}
	);
	public static final RangeVanillaOption SCREEN_EFFECTS_SCALE = new RangeVanillaOption(
			1.0D, Option.SCREEN_EFFECTS_SCALE,
			new TranslatableComponent("options.screenEffectScale"),
			UMUClient.component("option.video.screen_effects_scale.description"),
			(value, options, small) -> {
				int valueInt = (int) (value * 100);
				if (small) return new TextComponent(String.valueOf(valueInt));

				return valueInt == 0
						? CommonComponents.OPTION_OFF
						: new TranslatableComponent("umu_client.screen.options.unit.percent_value", valueInt);
			}
	);
}
