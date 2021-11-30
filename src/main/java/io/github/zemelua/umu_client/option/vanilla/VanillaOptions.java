package io.github.zemelua.umu_client.option.vanilla;

import io.github.zemelua.umu_client.UMUClient;
import net.minecraft.client.*;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundSource;

public final class VanillaOptions {
	public static final EnumerationVanillaOption<GraphicsStatus> GRAPHICS = new EnumerationVanillaOption<>(
			GraphicsStatus.values(), GraphicsStatus.FANCY, options -> options.graphicsMode,
			(options, value) -> options.graphicsMode = value, new TranslatableComponent("options.graphics"),
			UMUClient.component("option.video.graphics.description"), value -> new TranslatableComponent(value.getKey())
	);
	public static final RangeVanillaOption FRAMERATE_LIMIT = new RangeVanillaOption.Builder()
			.defaultValue(120.D)
			.interVal(1.0D)
			.name("framerateLimit")
			.description("video.framerate_limit")
			.valueFormatter((value, option, small) -> value.equals(option.getMax())
					? new TranslatableComponent("options.framerateLimit.max")
					: new TextComponent(String.valueOf(value.intValue()))
			).build(Option.FRAMERATE_LIMIT);
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

	public static final RangeVanillaOption RENDER_DISTANCE = new RangeVanillaOption.Builder()
			.defaultValue(Minecraft.getInstance().is64Bit() ? 12.0D : 8.0D)
			.interVal(1.0D)
			.name("renderDistance")
			.description("video.render_distance")
			.valueFormatter((value, options, small) -> small
					? new TranslatableComponent(Integer.toString(value.intValue()))
					: new TranslatableComponent("options.chunks", value.intValue())
			).build(Option.RENDER_DISTANCE);
	public static final RangeVanillaOption GAMMA = percentOption("gamma", "video.gamma")
			.valueFormatter((value, options, small) -> {
				int percent = (int) (Option.GAMMA.toPct(value) * 100.0D);
				if (small) return new TextComponent(String.valueOf(percent));

				if (percent == 0) {
					return new TranslatableComponent("options.gamma.min");
				} else if (percent == 100) {
					return new TranslatableComponent("options.gamma.max");
				} else {
					return new TranslatableComponent("umu_client.screen.options.unit.percent_add_value", percent);
				}
			}).build(Option.GAMMA);
	public static final EnumerationVanillaOption<AmbientOcclusionStatus> AMBIENT_OCCLUSION = new EnumerationVanillaOption<>(
			AmbientOcclusionStatus.values(), AmbientOcclusionStatus.MAX, options -> options.ambientOcclusion,
			(options, value) -> options.ambientOcclusion = value, new TranslatableComponent("options.ao"),
			UMUClient.component("option.video.ambient_occlusion.description"), value -> new TranslatableComponent(value.getKey())
	);
	public static final RangeVanillaOption MIPMAP_LEVELS = new RangeVanillaOption.Builder()
			.defaultValue(4.0D)
			.interVal(1.0D)
			.name("mipmapLevels")
			.description("video.mipmap_levels")
			.valueFormatter((value, options, small) -> {
				int valueInt = value.intValue();

				return valueInt == 0
						? CommonComponents.OPTION_OFF
						: new TextComponent(Integer.toString(valueInt));
			}).build(Option.MIPMAP_LEVELS);
	public static final SwitchVanillaOption ENTITY_SHADOWS = new SwitchVanillaOption(
			true, options -> options.entityShadows, ((options, value) -> options.entityShadows = value),
			new TranslatableComponent("options.entityShadows"),
			UMUClient.component("option.video.entity_shadows.description")
	);
	public static final RangeVanillaOption ENTITY_DISTANCE_SCALING = new RangeVanillaOption.Builder()
			.defaultValue(0.5D)
			.interVal(0.01D)
			.name("entityDistanceScaling")
			.description("video.entity_distance_scaling")
			.valueFormatter((value, options, small) -> {
				int percent = (int) (Option.ENTITY_DISTANCE_SCALING.toPct(value) * 100.0D);

				return small
						? new TextComponent(String.valueOf(percent))
						: new TranslatableComponent("umu_client.screen.options.unit.percent_value", percent);
			}).build(Option.ENTITY_DISTANCE_SCALING);
	public static final EnumerationVanillaOption<ParticleStatus> PARTICLES = new EnumerationVanillaOption<>(
			ParticleStatus.values(), ParticleStatus.ALL, options -> options.particles,
			(options, value) -> options.particles = value, new TranslatableComponent("options.particles"),
			UMUClient.component("option.video.particles.description"), value -> new TranslatableComponent(value.getKey())
	);
	public static final EnumerationVanillaOption<AttackIndicatorStatus> ATTACK_INDICATOR = new EnumerationVanillaOption<>(
			AttackIndicatorStatus.values(), AttackIndicatorStatus.CROSSHAIR, options -> options.attackIndicator,
			(options, value) -> options.attackIndicator = value, new TranslatableComponent("options.attackIndicator"),
			UMUClient.component("option.video.attack_indicator.description"), value -> new TranslatableComponent(value.getKey())
	);
	public static final RangeVanillaOption FOV_EFFECTS_SCALE = percentOption("fovEffectScale", "video.fov_effects_scale").build(Option.FOV_EFFECTS_SCALE);
	public static final RangeVanillaOption SCREEN_EFFECTS_SCALE = percentOption("screenEffectScale", "video.screen_effects_scale").build(Option.SCREEN_EFFECTS_SCALE);

	public static final SwitchVanillaOption VIEW_BOBBING = new SwitchVanillaOption(
			true, options -> options.bobView, (options, value) -> options.bobView = value,
			new TranslatableComponent("options.viewBobbing"),
			UMUClient.component("option.video.view_bobbing.description")
	);

	public static final RangeVanillaOption MASTER_VOLUME = percentOption().build(SoundSource.MASTER);
	public static final RangeVanillaOption MUSIC_VOLUME = percentOption().build(SoundSource.MUSIC);
	public static final RangeVanillaOption RECORD_VOLUME = percentOption().build(SoundSource.RECORDS);
	public static final RangeVanillaOption WEATHER_VOLUME = percentOption().build(SoundSource.WEATHER);
	public static final RangeVanillaOption BLOCK_VOLUME = percentOption().build(SoundSource.BLOCKS);
	public static final RangeVanillaOption HOSTILE_VOLUME = percentOption().build(SoundSource.HOSTILE);
	public static final RangeVanillaOption NEUTRAL_VOLUME = percentOption().build(SoundSource.NEUTRAL);
	public static final RangeVanillaOption PLAYER_VOLUME = percentOption().build(SoundSource.PLAYERS);
	public static final RangeVanillaOption AMBIENT_VOLUME = percentOption().build(SoundSource.AMBIENT);
	public static final RangeVanillaOption VOICE_VOLUME = percentOption().build(SoundSource.VOICE);

	private static RangeVanillaOption.Builder percentOption() {
		return new RangeVanillaOption.Builder()
				.defaultValue(1.0D)
				.interVal(0.01D)
				.valueFormatter((value, option, small) -> {
					int percent = (int) (option.toPercent(value) * 100.0D);

					return small
							? new TextComponent(Integer.toString(percent))
							: percent == 0
							? CommonComponents.OPTION_OFF
							: new TranslatableComponent("umu_client.screen.options.unit.percent_value", percent);
				});
	}

	private static RangeVanillaOption.Builder percentOption(String vanillaName, String modName) {
		return percentOption()
				.name("options." + vanillaName)
				.description("option." + modName + ".description");
	}


}
