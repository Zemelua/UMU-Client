package io.github.zemelua.umu_client.option;

public interface IRangeOption<T extends Number> {
	T getMax();

	T getMin();

	void setModifiedValue(Double value);
}
