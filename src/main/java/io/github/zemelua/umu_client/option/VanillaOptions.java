package io.github.zemelua.umu_client.option;

import io.github.zemelua.umu_client.UMUClient;
import io.github.zemelua.umu_client.option.forge.RangeVanillaOption;
import io.github.zemelua.umu_client.option.forge.SwitchVanillaOption;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Option;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;

public final class VanillaOptions {
	public static final RangeVanillaOption<Integer> FRAMERATE_LIMIT = new RangeVanillaOption<>(
			120, Option.FRAMERATE_LIMIT, Double::intValue,
			new TranslatableComponent("options.framerateLimit"),
			UMUClient.component("option.video.framerate_limit.description"),
			(option, compact) -> option.getModifiedValue().equals(option.getMax()) && !compact
					? new TranslatableComponent("options.framerateLimit.max")
					: new TextComponent(option.getModifiedValue().toString())
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

	public static final RangeVanillaOption<Integer> RENDER_DISTANCE = new RangeVanillaOption<>(
			Minecraft.getInstance().is64Bit() ? 12 : 8, Option.RENDER_DISTANCE, Double::intValue,
			new TranslatableComponent("options.renderDistance"),
			UMUClient.component("option.video.render_distance.description"),
			(option, compact) -> !compact
					? new TranslatableComponent("options.chunks", option.getModifiedValue())
					: new TextComponent(option.getModifiedValue().toString())
	);
	public static final RangeVanillaOption<Double> GAMMA = new RangeVanillaOption<>(
			1.0D, Option.GAMMA, Double::doubleValue,
			new TranslatableComponent("options.gamma"),
			UMUClient.component("option.video.gamma.description"),
			(option, compact) -> {
				if (compact) return new TextComponent(String.valueOf(option.getModifiedValue().intValue()));

				if (option.getModifiedValue() == 0.0D) {
					return new TranslatableComponent("options.gamma.min");
				} else if (option.getModifiedValue() == 1.0D) {
					return new TranslatableComponent("options.gamma.max");
				} else {
					return new TranslatableComponent("umu_client.screen.options.unit.percent_add_value",
							(int) (option.getModifiedValue() * 100.0D));
				}
			}
	);
	public static final RangeVanillaOption<Integer> MIPMAP_LEVELS = new RangeVanillaOption<>(
			4, Option.MIPMAP_LEVELS, Double::intValue,
			new TranslatableComponent("options.mipmapLevels"),
			UMUClient.component("option.video.mipmap_level.description"),
			(option, compact) -> option.getModifiedValue() == 0.0D && !compact
					? CommonComponents.OPTION_OFF
					: new TextComponent(option.getModifiedValue().toString())
	);
	public static final SwitchVanillaOption ENTITY_SHADOWS = new SwitchVanillaOption(
			true, options -> options.entityShadows, ((options, value) -> options.entityShadows = value),
			new TranslatableComponent("options.entityShadows"),
			UMUClient.component("option.video.entity_shadows.description")
	);
	public static final RangeVanillaOption<Double> ENTITY_DISTANCE_SCALING = new RangeVanillaOption<>(
			1.0D, Option.ENTITY_DISTANCE_SCALING, value -> (((int) (value / 0.25D)) * 0.25),
			new TranslatableComponent("options.entityDistanceScaling"),
			UMUClient.component("option.video.entity_distance_scaling.description"),
			(option, compact) -> !compact
					? new TranslatableComponent("umu_client.screen.options.unit.percent_value", option.getModifiedValue() * 100.0D)
					: new TextComponent(String.valueOf((int) (option.getModifiedValue() * 100.0D)))
	);
	public static final RangeVanillaOption<Double> FOV_EFFECTS_SCALE = new RangeVanillaOption<>(
			1.0D, Option.FOV_EFFECTS_SCALE, Double::doubleValue,
			new TranslatableComponent("options.fovEffectScale"),
			UMUClient.component("option.video.fov_effects_scale.description"),
			(option, compact) -> {
				if (compact) return new TextComponent(String.valueOf((int) (option.getModifiedValue() * 100.0D)));

				return option.getModifiedValue() == 0.0D
						? CommonComponents.OPTION_OFF
						: new TranslatableComponent("umu_client.screen.options.unit.percent_value",
						String.valueOf((int) (option.getModifiedValue() * 100.0D)));
			}
	);
	public static final RangeVanillaOption<Double> SCREEN_EFFECTS_SCALE = new RangeVanillaOption<>(
			1.0D, Option.SCREEN_EFFECTS_SCALE, Double::doubleValue,
			new TranslatableComponent("options.screenEffectScale"),
			UMUClient.component("options.screenEffectScale.tooltip"),
			(option, compact) -> {
				if (compact) return new TextComponent(String.valueOf((int) (option.getModifiedValue() * 100.0D)));

				return option.getModifiedValue() == 0.0D
						? CommonComponents.OPTION_OFF
						: new TranslatableComponent("umu_client.screen.options.unit.percent_value",
						String.valueOf((int) (option.getModifiedValue() * 100.0D)));
			}
	);
}
