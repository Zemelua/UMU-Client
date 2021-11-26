package io.github.zemelua.umu_client.option.forge;

import io.github.zemelua.umu_client.option.IOption;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.network.chat.Component;

import java.util.function.BiConsumer;
import java.util.function.Function;

public abstract class BaseVanillaOption<T> implements IOption<T> {
	protected final Options options = Minecraft.getInstance().options;

	private final T defaultValue;
	private final Function<Options, T> getter;
	private final BiConsumer<Options, T> setter;
	private final Component name;
	private final Component description;

	private T modifiableValue;

	protected BaseVanillaOption(T defaultValue, Function<Options, T> getter, BiConsumer<Options, T> setter,
								Component name, Component description) {
		this.defaultValue = defaultValue;
		this.getter = getter;
		this.setter = setter;
		this.name = name;
		this.description = description;

		this.modifiableValue = this.defaultValue;
	}

	@Override
	public T getValue() {
		return this.getter.apply(this.options);
	}

	@Override
	public void setValue(T value) {
		this.setter.accept(this.options, value);
		this.options.save();
	}

	@Override
	public T getModifiedValue() {
		return this.modifiableValue;
	}

	@Override
	public void setModifiedValue(T value) {
		this.modifiableValue = value;
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
	public void reset() {
		this.setModifiedValue(this.defaultValue);
		this.save();
	}
}
