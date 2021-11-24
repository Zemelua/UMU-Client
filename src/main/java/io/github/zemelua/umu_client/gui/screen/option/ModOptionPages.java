package io.github.zemelua.umu_client.gui.screen.option;

import com.google.common.collect.ImmutableList;
import io.github.zemelua.umu_client.UMUClient;
import io.github.zemelua.umu_client.option.IOption;
import io.github.zemelua.umu_client.option.ModOptions;

public class ModOptionPages {
	public static final ImmutableList<ModOptionPage> VIDEO = ImmutableList.<ModOptionPage>builder()
			.add(new ModOptionPage(UMUClient.component("screen.options.page.video"), ImmutableList.<ImmutableList<IOption<?>>>builder()
							.add(ImmutableList.<IOption<?>>builder()
									.add(ModOptions.DYNAMIC_LIGHT)
									.build()
							).build()
					)
			).build();
}
