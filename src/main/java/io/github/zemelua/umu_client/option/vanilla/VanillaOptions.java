package io.github.zemelua.umu_client.option.vanilla;

import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.platform.Window;
import io.github.zemelua.umu_client.UMUClient;
import io.github.zemelua.umu_client.option.ModOptions;
import net.minecraft.ChatFormatting;
import net.minecraft.client.*;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.client.gui.components.ChatComponent;
import net.minecraft.client.renderer.GpuWarnlistManager;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.sounds.SoundEngine;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.ChatVisiblity;

import java.util.stream.Stream;

public final class VanillaOptions {
	public static final RangeVanillaOption BIOME_BLEND_RADIUS;
	public static final RangeVanillaOption CHAT_HEIGHT_FOCUSED;
	public static final RangeVanillaOption CHAT_HEIGHT_UNFOCUSED;
	public static final RangeVanillaOption CHAT_OPACITY;
	public static final RangeVanillaOption CHAT_SCALE;
	public static final RangeVanillaOption CHAT_WIDTH;
	public static final RangeVanillaOption CHAT_LINE_SPACING;
	public static final RangeVanillaOption CHAT_DELAY;
	public static final RangeVanillaOption FOV;
	public static final RangeVanillaOption FOV_EFFECTS_SCALE;
	public static final RangeVanillaOption SCREEN_EFFECTS_SCALE;
	public static final RangeVanillaOption FRAMERATE_LIMIT;
	public static final RangeVanillaOption GAMMA;
	public static final RangeVanillaOption MIPMAP_LEVELS;
	public static final RangeVanillaOption MOUSE_WHEEL_SENSITIVITY;
	public static final SwitchVanillaOption RAW_MOUSE_INPUT;
	public static final RangeVanillaOption RENDER_DISTANCE;
	public static final RangeVanillaOption SIMULATION_DISTANCE;
	public static final RangeVanillaOption ENTITY_DISTANCE_SCALING;
	public static final RangeVanillaOption SENSITIVITY;
	public static final RangeVanillaOption TEXT_BACKGROUND_OPACITY;
	public static final EnumerationVanillaOption<AmbientOcclusionStatus> AMBIENT_OCCLUSION;
	public static final EnumerationVanillaOption<PrioritizeChunkUpdates> PRIORITIZE_CHUNK_UPDATES;
	public static final EnumerationVanillaOption<AttackIndicatorStatus> ATTACK_INDICATOR;
	public static final EnumerationVanillaOption<ChatVisiblity> CHAT_VISIBILITY;
	public static final EnumerationVanillaOption<GraphicsStatus> GRAPHICS;
	public static final RangeVanillaOption GUI_SCALE;
	public static final EnumerationVanillaOption<String> AUDIO_DEVICE;
	public static final EnumerationVanillaOption<HumanoidArm> MAIN_HAND;
	public static final EnumerationVanillaOption<NarratorStatus> NARRATOR;
	public static final EnumerationVanillaOption<ParticleStatus> PARTICLES;
	public static final EnumerationVanillaOption<CloudStatus> RENDER_CLOUDS;
	public static final SwitchVanillaOption TEXT_BACKGROUND;
	public static final SwitchVanillaOption AUTO_JUMP;
	public static final SwitchVanillaOption AUTO_SUGGESTIONS;
	public static final SwitchVanillaOption CHAT_COLOR;
	public static final SwitchVanillaOption HIDE_MATCHED_NAMES;
	public static final SwitchVanillaOption CHAT_LINKS;
	public static final SwitchVanillaOption CHAT_LINKS_PROMPT;
	public static final SwitchVanillaOption DISCRETE_MOUSE_SCROLL;
	public static final SwitchVanillaOption ENABLE_VSYNC;
	public static final SwitchVanillaOption ENTITY_SHADOWS;
	public static final SwitchVanillaOption FORCE_UNICODE_FONT;
	public static final SwitchVanillaOption INVERT_MOUSE;
	public static final SwitchVanillaOption REALMS_NOTIFICATIONS;
	public static final SwitchVanillaOption ALLOW_SERVER_LISTING;
	public static final SwitchVanillaOption REDUCED_DEBUG_INFO;
	public static final SwitchVanillaOption SHOW_SUBTITLES;
	public static final SwitchVanillaOption TOGGLE_CROUCH;
	public static final SwitchVanillaOption TOGGLE_SPRINT;
	public static final SwitchVanillaOption TOUCHSCREEN;
	public static final SwitchVanillaOption USE_FULLSCREEN;
	public static final SwitchVanillaOption VIEW_BOBBING;
	public static final SwitchVanillaOption DARK_MOJANG_STUDIOS_BACKGROUND_COLOR;
	public static final SwitchVanillaOption HIDE_LIGHTNING_FLASH;
	public static final SwitchVanillaOption AUTOSAVE_INDICATOR;

	static {
		BIOME_BLEND_RADIUS = new RangeVanillaOption.Builder()
				.defaultValue(2.0D)
				.interVal(1.0D)
				.name("options.biomeBlendRadius")
				.description("video.biome_blend_radius")
				.valueFormatter(((value, option, small) -> {
					int radius = (int) (value * 2 + 1);

					return small ? new TextComponent(Integer.toString(radius))
							: new TranslatableComponent("options.biomeBlendRadius." + radius);
				}))
				.build(Option.BIOME_BLEND_RADIUS);
		CHAT_HEIGHT_FOCUSED = new RangeVanillaOption.Builder()
				.defaultValue(1.0D)
				.interVal(0.01D)
				.name("options.chat.height.focused")
				.description("chat.height_focused")
				.valueFormatter(((value, option, small) -> {
					int height = ChatComponent.getHeight(option.toPercent(value));

					return small ? new TextComponent(Integer.toString(height))
							: ModOptions.pixelUnit(ChatComponent.getHeight(height));
				}))
				.build(Option.CHAT_HEIGHT_FOCUSED);
		CHAT_HEIGHT_UNFOCUSED = new RangeVanillaOption.Builder()
				.defaultValue(0.44366196D)
				.interVal(0.01D)
				.name("options.chat.height.unfocused")
				.description("chat.height_unfocused")
				.valueFormatter(((value, option, small) -> {
					int height = ChatComponent.getHeight(option.toPercent(value));

					return small ? new TextComponent(Integer.toString(height))
							: ModOptions.pixelUnit(ChatComponent.getHeight(height));
				}))
				.build(Option.CHAT_HEIGHT_UNFOCUSED);
		CHAT_OPACITY = new RangeVanillaOption.Builder()
				.defaultValue(1.0D)
				.interVal(0.01D)
				.name("options.chat.opacity")
				.description("chat.opacity")
				.valueFormatter(((value, option, small) -> {
					int opacity = (int) ((option.toPercent(value) * 0.9D + 0.1D) * 100.0D);

					return small ? new TextComponent(Integer.toString(opacity))
							: ModOptions.percentUnit(opacity);
				}))
				.build(Option.CHAT_OPACITY);
		CHAT_SCALE = new RangeVanillaOption.Builder()
				.defaultValue(1.0D)
				.interVal(0.01D)
				.name("options.chat.scale")
				.description("chat.scale")
				.valueFormatter(((value, option, small) -> {
					int percent = (int) (option.toPercent(value) * 100.0D);

					return small ? new TextComponent(Integer.toString(percent))
							: percent == 0
							? CommonComponents.OPTION_OFF
							: ModOptions.percentUnit(percent);
				}))
				.build(Option.CHAT_SCALE);
		CHAT_WIDTH = new RangeVanillaOption.Builder()
				.defaultValue(1.0D)
				.interVal(0.01D)
				.name("options.chat.width")
				.description("chat.width")
				.valueFormatter(((value, option, small) -> {
					int height = ChatComponent.getHeight(option.toPercent(value));

					return small ? new TextComponent(Integer.toString(height))
							: ModOptions.pixelUnit(ChatComponent.getHeight(height));
				}))
				.build(Option.CHAT_WIDTH);
		CHAT_LINE_SPACING = new RangeVanillaOption.Builder()
				.defaultValue(0.0D)
				.interVal(0.01D)
				.name("options.chat.line_spacing")
				.description("chat.line_spacing")
				.valueFormatter(((value, option, small) -> {
					int percent = (int) (option.toPercent(value) * 100.0D);

					return small ? new TextComponent(Integer.toString(percent))
							: ModOptions.percentUnit(percent);
				}))
				.build(Option.CHAT_LINE_SPACING);
		CHAT_DELAY = new RangeVanillaOption.Builder()
				.defaultValue(0.0D)
				.interVal(0.1D)
				.name("options.chat.delay_instant")
				.description("chat.delay")
				.valueFormatter(((value, option, small) -> {
					String seconds = String.format("%.1f", value);

					return small ? new TextComponent(seconds)
							: value <= 0.0D
							? new TranslatableComponent("gui.none")
							: ModOptions.secondUnit(value);
				}))
				.build(Option.CHAT_DELAY);
		FOV = new RangeVanillaOption.Builder()
				.defaultValue(70.0D)
				.interVal(1.0D)
				.name("options.fov")
				.description("video.fov")
				.valueFormatter(((value, option, small) -> {
					int valueInt = value.intValue();

					if (!small && valueInt == option.getDefaultValue()) {
						return new TranslatableComponent("option.fov.min");
					} else if (!small && valueInt == option.getMaxValue()) {
						return new TranslatableComponent("option.fov.max");
					} else {
						return new TextComponent(Integer.toString(valueInt));
					}
				}))
				.build(Option.FOV);
		FOV_EFFECTS_SCALE = new RangeVanillaOption.Builder()
				.defaultValue(1.0D)
				.interVal(0.01D)
				.name("options.fovEffectScale")
				.description(new TranslatableComponent("options.fovEffectScale.tooltip"))
				.valueFormatter(((value, option, small) -> {
					int percent = (int) (option.toPercent(value) * 100.0D);

					return small ? new TextComponent(Integer.toString(percent))
							: percent <= 0
							? CommonComponents.OPTION_OFF
							: ModOptions.percentUnit(percent);
				}))
				.build(Option.FOV_EFFECTS_SCALE);
		SCREEN_EFFECTS_SCALE = new RangeVanillaOption.Builder()
				.defaultValue(1.0D)
				.interVal(0.01D)
				.name("options.screenEffectScale")
				.description(new TranslatableComponent("options.screenEffectScale.tooltip"))
				.valueFormatter(((value, option, small) -> {
					int percent = (int) (option.toPercent(value) * 100.0D);

					return small ? new TextComponent(Integer.toString(percent))
							: percent <= 0
							? CommonComponents.OPTION_OFF
							: ModOptions.percentUnit(percent);
				}))
				.build(Option.SCREEN_EFFECTS_SCALE);
		FRAMERATE_LIMIT = new RangeVanillaOption.Builder()
				.defaultValue(120.0D)
				.interVal(10.0D)
				.name("options.framerateLimit")
				.description("video.framerate_limit")
				.valueFormatter(((value, option, small) -> {
					int valueInt = value.intValue();

					return small ? new TextComponent(Integer.toString(valueInt))
							: valueInt == option.getMaxValue().intValue()
							? new TranslatableComponent("options.framerateLimit.max")
							: ModOptions.fpsUnit(valueInt);
				}))
				.build(Option.FRAMERATE_LIMIT);
		GAMMA = new RangeVanillaOption.Builder()
				.defaultValue(0.5D)
				.interVal(0.01D)
				.name("options.gamma")
				.description("video.gamma")
				.valueFormatter(((value, option, small) -> {
					int percent = (int) (option.toPercent(value) * 100.0D);

					if (!small && percent == option.getMinValue().intValue()) {
						return new TranslatableComponent("options.gamma.min");
					} else if (!small && percent == option.getDefaultValue().intValue()) {
						return new TranslatableComponent("options.gamma.default");
					} else if (!small && percent == option.getMaxValue().intValue()) {
						return new TranslatableComponent("options.gamma.max");
					} else {
						return new TextComponent(Integer.toString(percent));
					}
				}))
				.build(Option.GAMMA);
		MIPMAP_LEVELS = new RangeVanillaOption.Builder()
				.defaultValue(4.0D)
				.interVal(1.0D)
				.name("options.mipmapLevels")
				.description("video.mipmap_levels")
				.valueFormatter(((value, option, small) -> {
					int valueInt = value.intValue();

					return !small && valueInt == 0
							? CommonComponents.OPTION_OFF
							: new TextComponent(Integer.toString(valueInt));
				}))
				.build(Option.MIPMAP_LEVELS);
		MOUSE_WHEEL_SENSITIVITY = new RangeVanillaOption.Builder()
				.defaultValue(1.0D)
				.interVal(0.01D)
				.name("options.mouseWheelSensitivity")
				.description("control.mouse_wheel_sensitivity")
				.valueFormatter(((value, option, small) -> {
					String sensitivity = String.format("%.2f", option.toPercent(value));

					return new TextComponent(sensitivity);
				}))
				.build(Option.MOUSE_WHEEL_SENSITIVITY);
		RAW_MOUSE_INPUT = new SwitchVanillaOption.Builder()
				.defaultValue(true)
				.name("options.rawMouseInput")
				.description("control.raw_mouse_input")
				.build((options -> options.rawMouseInput), (options, value) -> {
					options.rawMouseInput = value;
					Minecraft.getInstance().getWindow().updateRawMouseInput(value);
				});
		RENDER_DISTANCE = new RangeVanillaOption.Builder()
				.defaultValue(Minecraft.getInstance().is64Bit() ? 12.0D : 8.0D)
				.interVal(1.0D)
				.name("options.renderDistance")
				.description("video.render_distance")
				.valueFormatter(((value, option, small) -> {
					int valueInt = value.intValue();

					return small
							? new TextComponent(Integer.toString(valueInt))
							: ModOptions.chunkUnit(valueInt);
				}))
				.build(Option.RENDER_DISTANCE);
		SIMULATION_DISTANCE = new RangeVanillaOption.Builder()
				.defaultValue(Minecraft.getInstance().is64Bit() ? 12.0D : 8.0D)
				.interVal(1.0D)
				.name("options.simulationDistance")
				.description("video.simulation_distance")
				.valueFormatter(((value, option, small) -> {
					int valueInt = value.intValue();

					return small
							? new TextComponent(Integer.toString(valueInt))
							: ModOptions.chunkUnit(valueInt);
				}))
				.build(Option.SIMULATION_DISTANCE);
		ENTITY_DISTANCE_SCALING = new RangeVanillaOption.Builder()
				.defaultValue(1.0D)
				.interVal(0.25D)
				.name("options.entityDistanceScaling")
				.description("video.entity_distance_scaling")
				.valueFormatter(((value, option, small) -> {
					int percent = (int) (option.toPercent(value) * 100.0D);

					return small
							? new TextComponent(Integer.toString(percent))
							: ModOptions.percentUnit(percent);
				}))
				.build(Option.ENTITY_DISTANCE_SCALING);
		SENSITIVITY = new RangeVanillaOption.Builder()
				.defaultValue(0.5D)
				.interVal(0.01D)
				.name("options.sensitivity")
				.description("control.mouse_sensitivity")
				.valueFormatter(((value, option, small) -> {
					int percent = (int) (option.toPercent(value) * 200.0D);

					if (small) {
						return new TextComponent(Integer.toString(percent));
					} else if (percent == option.getMinValue().intValue()) {
						return new TranslatableComponent("options.sensitivity.min");
					} else if (percent == option.getMaxValue().intValue()) {
						return new TranslatableComponent("options.sensitivity.max");
					} else {
						return ModOptions.percentUnit(percent);
					}
				}))
				.build(Option.SENSITIVITY);
		TEXT_BACKGROUND_OPACITY = new RangeVanillaOption.Builder()
				.defaultValue(0.5D)
				.interVal(0.01D)
				.name("options.accessibility.text_background_opacity")
				.description("accessibility.text_background_opacity")
				.valueFormatter(((value, option, small) -> {
					int percent = (int) (option.toPercent(value) * 100.0D);

					return small ? new TextComponent(Integer.toString(percent))
							: ModOptions.percentUnit(percent);
				}))
				.build(Option.TEXT_BACKGROUND_OPACITY);
		AMBIENT_OCCLUSION = new EnumerationVanillaOption.Builder<AmbientOcclusionStatus>()
				.defaultValue(AmbientOcclusionStatus.MAX)
				.values(AmbientOcclusionStatus.values())
				.name("options.ao")
				.description("video.ambient_occlusion")
				.valueFormatter(value -> new TranslatableComponent(value.getKey()))
				.build(options -> options.ambientOcclusion, (options, value) -> {
					options.ambientOcclusion = value;
					Minecraft.getInstance().levelRenderer.allChanged();
				});
		PRIORITIZE_CHUNK_UPDATES = new EnumerationVanillaOption.Builder<PrioritizeChunkUpdates>()
				.defaultValue(PrioritizeChunkUpdates.NONE)
				.values(PrioritizeChunkUpdates.values())
				.name("options.prioritizeChunkUpdates")
				.description(new TranslatableComponent(PrioritizeChunkUpdates.NONE.getKey())
						.append(new TextComponent(": "))
						.append(new TranslatableComponent("options.prioritizeChunkUpdates.none.tooltip"))
						.append(new TextComponent("\n"))
						.append(new TranslatableComponent(PrioritizeChunkUpdates.PLAYER_AFFECTED.getKey()))
						.append(new TextComponent(": "))
						.append(new TranslatableComponent("options.prioritizeChunkUpdates.byPlayer.tooltip"))
						.append(new TextComponent("\n"))
						.append(new TranslatableComponent(PrioritizeChunkUpdates.NEARBY.getKey()))
						.append(new TextComponent(": "))
						.append(new TranslatableComponent("options.prioritizeChunkUpdates.nearby.tooltip"))
				)
				.valueFormatter(value -> new TranslatableComponent(value.getKey()))
				.build(options -> options.prioritizeChunkUpdates, (options, value) -> options.prioritizeChunkUpdates = value);
		ATTACK_INDICATOR = new EnumerationVanillaOption.Builder<AttackIndicatorStatus>()
				.defaultValue(AttackIndicatorStatus.CROSSHAIR)
				.values(AttackIndicatorStatus.values())
				.name("options.attackIndicator")
				.description("video.attack_indicator")
				.valueFormatter(value -> new TranslatableComponent(value.getKey()))
				.build(options -> options.attackIndicator, (options, value) -> options.attackIndicator = value);
		CHAT_VISIBILITY = new EnumerationVanillaOption.Builder<ChatVisiblity>()
				.defaultValue(ChatVisiblity.FULL)
				.values(ChatVisiblity.values())
				.name("options.chat.visibility")
				.description("chat.visibility")
				.valueFormatter(value -> new TranslatableComponent(value.getKey()))
				.build(options -> options.chatVisibility, (options, value) -> options.chatVisibility = value);
		GRAPHICS = new EnumerationVanillaOption<>(GraphicsStatus.FANCY, GraphicsStatus.values(),
				options -> options.graphicsMode,
				(options, value) -> {
					Minecraft minecraft = Minecraft.getInstance();
					GpuWarnlistManager gpuWarnlistManager = minecraft.getGpuWarnlistManager();
					if (value == GraphicsStatus.FABULOUS && gpuWarnlistManager.willShowWarning()) {
						gpuWarnlistManager.showWarning();
					} else {
						options.graphicsMode = value;
						minecraft.levelRenderer.allChanged();
					}
				}, new TranslatableComponent("options.graphics"),
				new TranslatableComponent(GraphicsStatus.FAST.getKey())
						.append(new TextComponent(": "))
						.append(new TranslatableComponent("options.graphics.fast.tooltip"))
						.append(new TextComponent("\n"))
						.append(new TranslatableComponent(GraphicsStatus.FANCY.getKey()))
						.append(new TextComponent(": "))
						.append(new TranslatableComponent("options.graphics.fancy.tooltip"))
						.append(new TextComponent("\n"))
						.append(new TranslatableComponent(GraphicsStatus.FABULOUS.getKey()))
						.append(new TextComponent(": "))
						.append(new TranslatableComponent("options.graphics.fabulous.tooltip")),
				value -> {
					MutableComponent component = new TranslatableComponent(value.getKey());
					return value == GraphicsStatus.FABULOUS ? component.withStyle(ChatFormatting.ITALIC) : component;
				}
		) {
			@Override
			public GraphicsStatus[] getValues() {
				return Minecraft.getInstance().getGpuWarnlistManager().isSkippingFabulous()
						? Stream.of(GraphicsStatus.values()).filter(value -> value != GraphicsStatus.FABULOUS).toArray(GraphicsStatus[]::new)
						: GraphicsStatus.values();
			}
		};
		GUI_SCALE = new RangeVanillaOption(0.0D, 0.0D,
				(double) Minecraft.getInstance().getWindow().calculateScale(0, Minecraft.getInstance().isEnforceUnicode()),
				1.0D, options -> (double) options.guiScale, (options, value) -> options.guiScale = value.intValue(),
				new TranslatableComponent("options.guiScale"), UMUClient.component("option.video.gui_scale.description"),
				((value, option, small) -> {
					int valueInt = value.intValue();

					return !small && valueInt == 0
							? new TranslatableComponent("options.guiScale.auto")
							: new TextComponent(Integer.toString(valueInt));
				})
		) {
			@Override
			public Double getMaxValue() {
				return (double) Minecraft.getInstance().getWindow().calculateScale(0, Minecraft.getInstance().isEnforceUnicode());
			}
		};
		AUDIO_DEVICE = new EnumerationVanillaOption<>(
				"", Stream.concat(Stream.of(""),
				Minecraft.getInstance().getSoundManager().getAvailableSoundDevices().stream()).toArray(String[]::new),
				options -> options.soundDevice,
				(options, value) -> {
					options.soundDevice = value;
					SoundManager soundManager = Minecraft.getInstance().getSoundManager();
					soundManager.reload();
					soundManager.play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
				}, new TranslatableComponent("options.audioDevice"), UMUClient.component("option.video.audio_device.description"),
				value -> {
					if (value.equals("")) {
						return new TranslatableComponent("options.audioDevice.default");
					} else if (value.startsWith("OpenAL Soft on ")) {
						return new TextComponent(value.substring(SoundEngine.OPEN_AL_SOFT_PREFIX_LENGTH));
					}

					return new TextComponent(value);
				}
		) {
			@Override
			public String[] getValues() {
				return Stream.concat(Stream.of(""),
						Minecraft.getInstance().getSoundManager().getAvailableSoundDevices().stream()).toArray(String[]::new);
			}
		};
		MAIN_HAND = new EnumerationVanillaOption.Builder<HumanoidArm>()
				.defaultValue(HumanoidArm.RIGHT)
				.values(HumanoidArm.values())
				.name("options.mainHand")
				.description("skin.mainHand")
				.valueFormatter(HumanoidArm::getName)
				.build(options -> options.mainHand, (options, value) -> {
					options.mainHand = value;
					options.broadcastOptions();
				});
		NARRATOR = new EnumerationVanillaOption.Builder<NarratorStatus>()
				.defaultValue(NarratorStatus.OFF)
				.values(NarratorStatus.values())
				.name("options.narrator")
				.description("accessibility.narrator")
				.valueFormatter(NarratorStatus::getName)
				.build(options -> options.narratorStatus, (options, value) -> {
					options.narratorStatus = value;
					NarratorChatListener.INSTANCE.updateNarratorStatus(value);
				});
		PARTICLES = new EnumerationVanillaOption.Builder<ParticleStatus>()
				.defaultValue(ParticleStatus.ALL)
				.values(ParticleStatus.values())
				.name("options.particles")
				.description("video.particles")
				.valueFormatter(value -> new TranslatableComponent(value.getKey()))
				.build(options -> options.particles, (options, value) -> options.particles = value);
		RENDER_CLOUDS = new EnumerationVanillaOption.Builder<CloudStatus>()
				.defaultValue(CloudStatus.FANCY)
				.values(CloudStatus.values())
				.name("options.renderClouds")
				.description("video.render_clouds")
				.valueFormatter(value -> new TranslatableComponent(value.getKey()))
				.build(options -> options.renderClouds, (options, value) -> {
					options.renderClouds = value;
					if (Minecraft.useShaderTransparency()) {
						RenderTarget renderTarget = Minecraft.getInstance().levelRenderer.getCloudsTarget();

						if (renderTarget != null) {
							renderTarget.clear(Minecraft.ON_OSX);
						}
					}
				});
		TEXT_BACKGROUND = new SwitchVanillaOption.Builder()
				.defaultValue(true)
				.name("options.accessibility.text_background")
				.description("accessibility.text_background")
				.build(options -> options.backgroundForChatOnly, (options, value) -> options.backgroundForChatOnly = value);
		AUTO_JUMP = new SwitchVanillaOption.Builder()
				.defaultValue(true)
				.name("options.autoJump")
				.description("control.auto_jump")
				.build(options -> options.autoJump, (options, value) -> options.autoJump = value);
		AUTO_SUGGESTIONS = new SwitchVanillaOption.Builder()
				.defaultValue(true)
				.name("options.autoSuggestCommands")
				.description("chat.auto_suggest_commands")
				.build(options -> options.autoSuggestions, (options, value) -> options.autoSuggestions = value);
		CHAT_COLOR = new SwitchVanillaOption.Builder()
				.defaultValue(true)
				.name("options.chat.color")
				.description("chat.color")
				.build(options -> options.chatColors, (options, value) -> options.chatColors = value);
		HIDE_MATCHED_NAMES = new SwitchVanillaOption.Builder()
				.defaultValue(true)
				.name("options.hideMatchedNames")
				.description("chat.hide_matched_names")
				.build(options -> options.hideMatchedNames, (options, value) -> options.hideMatchedNames = value);
		CHAT_LINKS = new SwitchVanillaOption.Builder()
				.defaultValue(true)
				.name("options.chat.links")
				.description("chat.links")
				.build(options -> options.chatLinks, (options, value) -> options.chatLinks = value);
		CHAT_LINKS_PROMPT = new SwitchVanillaOption.Builder()
				.defaultValue(true)
				.name("options.chat.links.prompt")
				.description("chat.links_prompt")
				.build(options -> options.chatLinksPrompt, (options, value) -> options.chatLinksPrompt = value);
		DISCRETE_MOUSE_SCROLL = new SwitchVanillaOption.Builder()
				.defaultValue(false)
				.name("options.discrete_mouse_scroll")
				.description("control.discrete_mouse_scroll")
				.build(options -> options.discreteMouseScroll, (options, value) -> options.discreteMouseScroll = value);
		ENABLE_VSYNC = new SwitchVanillaOption.Builder()
				.defaultValue(true)
				.name("options.vsync")
				.description("video.enable_vsync")
				.build(options -> options.enableVsync, (options, value) -> {
					options.enableVsync = value;
					Minecraft.getInstance().getWindow().updateVsync(options.enableVsync);
				});
		ENTITY_SHADOWS = new SwitchVanillaOption.Builder()
				.defaultValue(true)
				.name("options.entityShadows")
				.description("video.render_entity_shadows")
				.build(options -> options.entityShadows, (options, value) -> options.entityShadows = value);
		FORCE_UNICODE_FONT = new SwitchVanillaOption.Builder()
				.defaultValue(false)
				.name("options.forceUnicodeFont")
				.description("language.force_unicode_font")
				.build(options -> options.forceUnicodeFont, (options, value) -> {
					options.forceUnicodeFont = value;
					Minecraft minecraft = Minecraft.getInstance();
						minecraft.selectMainFont(options.forceUnicodeFont);
						minecraft.resizeDisplay();
				});
		INVERT_MOUSE = new SwitchVanillaOption.Builder()
				.defaultValue(false)
				.name("options.invertMouse")
				.description("control.invert_mouse_y")
				.build(options -> options.invertYMouse, (options, value) -> options.invertYMouse = value);
		REALMS_NOTIFICATIONS = new SwitchVanillaOption.Builder()
				.defaultValue(true)
				.name("options.realmsNotifications")
				.description("online.enable_realms_notifications")
				.build(options -> options.realmsNotifications, (options, value) -> options.realmsNotifications = value);
		ALLOW_SERVER_LISTING = new SwitchVanillaOption.Builder()
				.defaultValue(true)
				.name("options.allowServerListing")
				.description(new TranslatableComponent("options.allowServerListing.tooltip"))
				.build(options -> options.allowServerListing, (options, value) -> {
					options.allowServerListing = value;
					options.broadcastOptions();
				});
		REDUCED_DEBUG_INFO = new SwitchVanillaOption.Builder()
				.defaultValue(false)
				.name("options.reducedDebugInfo")
				.description("chat.reduce_debug_info")
				.build(options -> options.reducedDebugInfo, (options, value) -> options.reducedDebugInfo = value);
		SHOW_SUBTITLES = new SwitchVanillaOption.Builder()
				.defaultValue(false)
				.name("options.showSubtitles")
				.description("sound.show_subtitles")
				.build(options -> options.showSubtitles, (options, value) -> options.showSubtitles = value);
		TOGGLE_CROUCH = new SwitchVanillaOption.Builder()
				.defaultValue(false)
				.name("key.sneak")
				.description("control.toggle_crouch")
				.build(options -> options.toggleCrouch, (options, value) -> options.toggleCrouch = value);
		TOGGLE_SPRINT = new SwitchVanillaOption.Builder()
				.defaultValue(false)
				.name("key.sprint")
				.description("control.toggle_sprint")
				.build(options -> options.toggleSprint, (options, value) -> options.toggleSprint = value);
		TOUCHSCREEN = new SwitchVanillaOption.Builder()
				.defaultValue(false)
				.name("options.touchscreen")
				.description("control.touchscreen")
				.build(options -> options.touchscreen, (options, value) -> options.touchscreen = value);
		USE_FULLSCREEN = new SwitchVanillaOption.Builder()
				.defaultValue(false)
				.name("options.fullscreen")
				.description("video.use_fullscreen")
				.build(options -> options.fullscreen, (options, value) -> {
					options.fullscreen = value;
					Window window = Minecraft.getInstance().getWindow();
					if (window.isFullscreen() != options.fullscreen) {
						window.toggleFullScreen();
						options.fullscreen = window.isFullscreen();
					}
				});
		VIEW_BOBBING = new SwitchVanillaOption.Builder()
				.defaultValue(true)
				.name("options.viewBobbing")
				.description("video.view_bobbing")
				.build(options -> options.bobView, (options, value) -> options.bobView = value);
		DARK_MOJANG_STUDIOS_BACKGROUND_COLOR = new SwitchVanillaOption.Builder()
				.defaultValue(false)
				.name("options.darkMojangStudiosBackgroundColor")
				.description(new TranslatableComponent("options.darkMojangStudiosBackgroundColor.tooltip"))
				.build(options -> options.darkMojangStudiosBackground, (options, value) -> options.darkMojangStudiosBackground = value);
		HIDE_LIGHTNING_FLASH = new SwitchVanillaOption.Builder()
				.defaultValue(false)
				.name("options.hideLightningFlashes")
				.description(new TranslatableComponent("options.hideLightningFlashes.tooltip"))
				.build(options -> options.hideLightningFlashes, (options, value) -> options.hideLightningFlashes = value);
		AUTOSAVE_INDICATOR = new SwitchVanillaOption.Builder()
				.name("options.autosaveIndicator")
				.description("video.hide_auto_save_indicator")
				.build(options -> options.showAutosaveIndicator, (options, value) -> options.showAutosaveIndicator = value);
	}
}
