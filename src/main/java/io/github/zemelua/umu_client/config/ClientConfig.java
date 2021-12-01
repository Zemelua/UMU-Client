package io.github.zemelua.umu_client.config;

import io.github.zemelua.umu_client.option.IOption;
import io.github.zemelua.umu_client.option.IRangeOption;
import io.github.zemelua.umu_client.option.ModOptions;
import io.github.zemelua.umu_client.option.enums.DynamicLightMode;
import io.github.zemelua.umu_client.option.enums.IHasComponent;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.EnumValue;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;

public class ClientConfig {
	private static final boolean ENABLE_DYNAMIC_LIGHT_DEFAULT = true;

	private final BooleanValue dynamicLightEnable;
	private final EnumValue<DynamicLightMode> dynamicLightMode;
	private final IntValue dynamicLightRenderDistance;
	private final IntValue themeColor;

	private final IntValue blockAmbientVolume;
	private final IntValue blockPlaceVolume;
	private final IntValue blockBreakVolume;

	public ClientConfig(ForgeConfigSpec.Builder builder) {
		builder.push("video");

		this.dynamicLightEnable = builder.comment(ModOptions.DYNAMIC_LIGHT_ENABLE.getDescription().getString())
				.define(ModOptions.DYNAMIC_LIGHT_ENABLE.getName().getString(), ENABLE_DYNAMIC_LIGHT_DEFAULT);
		this.dynamicLightMode = defineEnum(builder, ModOptions.DYNAMIC_LIGHT_MODE);
		this.dynamicLightRenderDistance = defineInRange(builder, ModOptions.DYNAMIC_LIGHT_RENDER_DISTANCE);
		this.themeColor = builder.comment("")
				.defineInRange("theme_color", 46 / 255, 0, 255);

		this.blockAmbientVolume = defineInRange(builder, ModOptions.BLOCK_AMBIENT_VOLUME);
		this.blockPlaceVolume = defineInRange(builder, ModOptions.BLOCK_PLACE_VOLUME);
		this.blockBreakVolume = defineInRange(builder, ModOptions.BLOCK_BREAK_VOLUME);
	}

	public BooleanValue getDynamicLightEnable() {
		return this.dynamicLightEnable;
	}

	public EnumValue<DynamicLightMode> getDynamicLightMode() {
		return this.dynamicLightMode;
	}

	public IntValue getDynamicLightRenderDistance() {
		return this.dynamicLightRenderDistance;
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

	private static <T extends IOption<E>, E extends Enum<E> & IHasComponent> EnumValue<E> defineEnum(
			ForgeConfigSpec.Builder builder, T parent) {
		return builder.defineEnum(parent.getName().getString(), parent.getDefaultValue());
	}
}
