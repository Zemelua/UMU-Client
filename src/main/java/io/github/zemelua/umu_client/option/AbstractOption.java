package io.github.zemelua.umu_client.option;

import io.github.zemelua.umu_client.UMUClient;
import io.github.zemelua.umu_client.config.ModConfig;
import net.minecraft.network.chat.Component;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.function.BooleanSupplier;
import java.util.function.Function;

public abstract class AbstractOption<T> implements IOption<T> {
	private final T defaultValue;
	private final Function<ModConfig, ForgeConfigSpec.ConfigValue<T>> cache;
	private final Component name;
	private final Component description;
	private final BooleanSupplier isEnable;

	public AbstractOption(T defaultValue, Function<ModConfig, ForgeConfigSpec.ConfigValue<T>> cache,
						  Component name, Component description, BooleanSupplier isEnable) {
		this.defaultValue = defaultValue;
		this.cache = cache;
		this.name = name;
		this.description = description;
		this.isEnable = isEnable;
	}

	@Override
	public T getValue() {
		return this.configValue().get();
	}

	@Override
	public void setValue(T value) {
		this.configValue().set(value);
		this.configValue().save();
	}

	@Override
	public T getDefaultValue() {
		return this.defaultValue;
	}

	@Override
	public Component getName() {
		return this.name;
	}

	@Override
	public Component getDescription() {
		return this.description;
	}

	@Override
	public boolean isEnable() {
		return this.isEnable.getAsBoolean();
	}

	private ForgeConfigSpec.ConfigValue<T> configValue() {
		return this.cache.apply(UMUClient.CONFIG);
	}
}
