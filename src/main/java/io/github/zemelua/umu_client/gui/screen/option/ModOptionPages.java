package io.github.zemelua.umu_client.gui.screen.option;

import com.google.common.collect.ImmutableList;
import io.github.zemelua.umu_client.UMUClient;
import io.github.zemelua.umu_client.option.IOption;
import io.github.zemelua.umu_client.option.ModOptions;

public class ModOptionPages {
	public static final ImmutableList<ModOptionPage> VIDEO = ImmutableList.<ModOptionPage>builder()
			.add(new ModOptionPage(UMUClient.component("screen.options.page.dynamic_light"), ImmutableList.<ImmutableList<IOption<?>>>builder()
							.add(ImmutableList.<IOption<?>>builder()
									.add(ModOptions.DYNAMIC_LIGHT)
									.add(ModOptions.DYNAMIC_LIGHT_BRIGHTNESS)
									.build()
							).build()
					)
			).add(new ModOptionPage(UMUClient.component("screen.options.page.visual"), ImmutableList.<ImmutableList<IOption<?>>>builder()
							.add(ImmutableList.<IOption<?>>builder()
									.add(ModOptions.THEME_COLOR)
									.build()
							).build()
					)
			).build();
}
