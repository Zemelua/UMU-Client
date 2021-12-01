package io.github.zemelua.umu_client.option.enums;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;

public enum DynamicLightMode implements IHasComponent {
	SELF_ITEM("self_item"),
	ALL_ITEMS("all_items"),
	ALL_ITEMS_AND_ENTITIES("all_items_and_entities");

	private final String name;

	DynamicLightMode(String name) {
		this.name = name;
	}

	@Override
	public Component getComponent() {
		return new TranslatableComponent("umu_client.option.video.dynamic_light_mode." + this.name);
	}

	public boolean renderItems() {
		return this == ALL_ITEMS || this == ALL_ITEMS_AND_ENTITIES;
	}

	public boolean renderEntities() {
		return this == ALL_ITEMS_AND_ENTITIES;
	}

	public boolean renderOnlySelf() {
		return this == SELF_ITEM;
	}

	public boolean renderNotOnlySelf() {
		return this.renderItems() || this.renderEntities();
	}
}
