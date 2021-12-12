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
	private final BooleanValue enableDramaticSky;
	private final IntValue themeColor;

	private final IntValue blocksPlaceVolume;
	private final IntValue blocksHitVolume;
	private final IntValue blocksBreakVolume;
	private final IntValue blocksStepVolume;
	private final IntValue blocksFallVolume;
	private final IntValue blocksAmbientVolume;

	public ModConfig(ForgeConfigSpec.Builder builder) {
		builder.push("video");
		this.enableDynamicLight = ModConfig.define(builder, ModOptions.ENABLE_DYNAMIC_LIGHT);
		this.dynamicLightMode = ModConfig.defineEnum(builder, ModOptions.DYNAMIC_LIGHT_MODE);
		this.enableDramaticSky = ModConfig.define(builder, ModOptions.ENABLE_DRAMATIC_SKY);
		this.themeColor = ModConfig.defineInRange(builder, ModOptions.THEME_COLOR);
		builder.pop();

		builder.push("sound");
		this.blocksPlaceVolume = ModConfig.defineInRange(builder, ModOptions.BLOCKS_PLACE_VOLUME);
		this.blocksHitVolume = ModConfig.defineInRange(builder, ModOptions.BLOCKS_HIT_VOLUME);
		this.blocksBreakVolume = ModConfig.defineInRange(builder, ModOptions.BLOCKS_BREAK_VOLUME);
		this.blocksStepVolume = ModConfig.defineInRange(builder, ModOptions.BLOCKS_STEP_VOLUME);
		this.blocksFallVolume = ModConfig.defineInRange(builder, ModOptions.BLOCKS_FALL_VOLUME);
		this.blocksAmbientVolume = ModConfig.defineInRange(builder, ModOptions.BLOCKS_AMBIENT_VOLUME);
		builder.pop();
	}

	public BooleanValue getEnableDynamicLight() {
		return this.enableDynamicLight;
	}

	public EnumValue<DynamicLightMode> getDynamicLightMode() {
		return this.dynamicLightMode;
	}

	public BooleanValue getEnableDramaticSky() {
		return this.enableDramaticSky;
	}

	public IntValue getThemeColor() {
		return this.themeColor;
	}

	public IntValue getBlocksPlaceVolume() {
		return this.blocksPlaceVolume;
	}

	public IntValue getBlocksHitVolume() {
		return this.blocksHitVolume;
	}

	public IntValue getBlocksBreakVolume() {
		return this.blocksBreakVolume;
	}

	public IntValue getBlocksStepVolume() {
		return this.blocksStepVolume;
	}

	public IntValue getBlocksFallVolume() {
		return this.blocksFallVolume;
	}

	public IntValue getBlocksAmbientVolume() {
		return this.blocksAmbientVolume;
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
