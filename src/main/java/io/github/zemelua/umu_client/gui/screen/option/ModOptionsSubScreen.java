package io.github.zemelua.umu_client.gui.screen.option;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.TextComponent;

import java.util.ArrayList;
import java.util.List;

public class ModOptionsSubScreen extends Screen {
	private final Screen root;
	private final List<ModOptionPage> pages = new ArrayList<>();

	protected ModOptionsSubScreen(Screen root) {
		super(new TextComponent("UMU Client Options"));

		this.root = root;
	}

	@Override
	protected void init() {
		super.init();
	}
}
