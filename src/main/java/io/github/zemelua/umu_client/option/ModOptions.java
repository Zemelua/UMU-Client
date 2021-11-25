package io.github.zemelua.umu_client.option;

import io.github.zemelua.umu_client.UMUClient;
import io.github.zemelua.umu_client.config.ClientConfig;

public final class ModOptions {
	public static final SwitchOption DYNAMIC_LIGHT = new SwitchOption(
			true, ClientConfig::getDynamicLightEnable,
			UMUClient.component("option.video.dynamic_light_enable"),
			UMUClient.component("option.video.dynamic_light_enable.description")
	);
	public static final RangeOption DYNAMIC_LIGHT_BRIGHTNESS = new RangeOption(
			15, 1, 15, ClientConfig::getDynamicLightBrightness,
			UMUClient.component("option.video.dynamic_light_brightness"),
			UMUClient.component("option.video.dynamic_light_brightness.description")
	);
}
