package io.github.zemelua.umu_client.gui.screen.option;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import io.github.zemelua.umu_client.UMUClient;
import io.github.zemelua.umu_client.gui.screen.widget.OptionWidget;
import io.github.zemelua.umu_client.gui.screen.widget.SelectableButtonWidget;
import io.github.zemelua.umu_client.gui.screen.widget.SimpleButtonWidget;
import io.github.zemelua.umu_client.option.IOption;
import io.github.zemelua.umu_client.util.Rect2i;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class ModOptionsSubScreen extends Screen {
	@Nullable private final Screen root;
	private final ImmutableList<OptionPages.Page> pages;

	private final List<OptionWidget<?, ?>> optionWidgets = new ArrayList<>();

	private int currentPage;

	protected ModOptionsSubScreen(@Nullable Screen root, ImmutableList<OptionPages.Page> pages) {
		super(new TextComponent("UMU Client Options"));

		this.root = root;
		this.pages = pages;

		this.currentPage = 0;
	}

	@Override
	protected void init() {
		super.init();

		this.clearWidgets();

		this.initPageButtons();
		this.initOptionWidgets();

		this.addRenderableWidget(new SimpleButtonWidget(
				new Rect2i(this.width - 142, this.height - 30, 65, 20),
				UMUClient.component("screen.options.button.apply"),
				this::apply
		));
		this.addRenderableWidget(new SimpleButtonWidget(
				new Rect2i(this.width - 73, this.height - 30, 65, 20),
				new TranslatableComponent("gui.done"),
				this::onClose
		));

		this.initUndoOrResetButton();
	}

	private void initPageButtons() {
		int x = 6;
		int y = 6;
		int width = (this.width - 6) / 5 - 6;
		int height = 18;

		for (int i = 0; i < this.pages.size(); i++) {
			OptionPages.Page page = this.pages.get(i);

			int finalI = i;
			SelectableButtonWidget button = new SelectableButtonWidget(
					new Rect2i(x, y, width, height), page.getTitle(), () -> this.setPage(finalI)
			);
			button.setSelected(this.currentPage == i);

			this.addRenderableWidget(button);

			x += width + 6;
		}
	}

	private void initOptionWidgets() {
		this.optionWidgets.clear();

		int x = 6;
		int y = 28;

		for (OptionPages.Group group : this.pages.get(this.currentPage).getGroups()) {
			for (IOption<?> option : group.getOptions()) {
				OptionWidget<?, ? extends IOption<?>> widget = option.createWidget(new Rect2i(x, y, this.width / 2 - 8, 20));

				this.addRenderableWidget(widget);
				this.optionWidgets.add(widget);

				y += 20;
			}

			y += 4;
		}
	}

	@Nullable
	private SimpleButtonWidget undoOrResetButton;

	private void initUndoOrResetButton() {
		if (this.undoOrResetButton != null) {
			this.removeWidget(this.undoOrResetButton);
		}

		if (this.getAllOptionWidgets().anyMatch(OptionWidget::isChanged)) {
			this.undoOrResetButton = new SimpleButtonWidget(
					new Rect2i(this.width - 211, this.height - 30, 65, 20),
					UMUClient.component("screen.options.button.undo"),
					this::undo
			);
		} else {
			this.undoOrResetButton = new SimpleButtonWidget(
					new Rect2i(this.width - 211, this.height - 30, 65, 20),
					UMUClient.component("screen.options.button.reset"),
					this::reset
			);
		}

		this.addRenderableWidget(this.undoOrResetButton);
	}

	@Override
	public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		super.renderBackground(matrixStack);
		super.render(matrixStack, mouseX, mouseY, partialTicks);
	}

	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		boolean result = super.mouseClicked(mouseX, mouseY, button);

		this.initUndoOrResetButton();

		return result;
	}

	private void setPage(int index) {
		this.undo();
		this.currentPage = index;
		this.init();
	}

	private void undo() {
		this.getAllOptionWidgets().forEach(OptionWidget::load);
	}

	private void apply() {
		this.getAllOptionWidgets().forEach(OptionWidget::save);
	}

	private void reset() {
		this.getAllOptionWidgets().forEach(OptionWidget::reset);
	}

	private Stream<OptionWidget<?, ?>> getAllOptionWidgets() {
		return this.optionWidgets.stream();
	}

	@Override
	public void onClose() {
		if (this.minecraft == null) return;

		this.minecraft.setScreen(this.root);
	}

	public static ModOptionsSubScreen videoOptionsScreen(@Nullable Screen root) {
		return new ModOptionsSubScreen(root, OptionPages.VIDEO);
	}

	public static ModOptionsSubScreen soundOptionsScreen(@Nullable Screen root) {
		return new ModOptionsSubScreen(root, OptionPages.SOUND);
	}
}
