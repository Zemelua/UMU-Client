package io.github.zemelua.umu_client.gui.screen.option;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import io.github.zemelua.umu_client.gui.screen.widget.OptionWidget;
import io.github.zemelua.umu_client.gui.screen.widget.SelectableButtonWidget;
import io.github.zemelua.umu_client.gui.screen.widget.SimpleButtonWidget;
import io.github.zemelua.umu_client.option.IOption;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;

import java.util.Collection;
import java.util.stream.Stream;

public class ModOptionsSubScreen extends Screen {
	private final Screen root;
	private final ImmutableList<ModOptionPage> pages;

	private int currentPage;

	protected ModOptionsSubScreen(Screen root, ImmutableList<ModOptionPage> pages) {
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
				new Rect2i(this.width - 211, this.height - 30, 65, 20),
				new TranslatableComponent("screen.options.buttons.undo"),
				this::undo
		));
		this.addRenderableWidget(new SimpleButtonWidget(
				new Rect2i(this.width - 142, this.height - 30, 65, 20),
				new TranslatableComponent("screen.options.buttons.apply"),
				this::apply
		));
		this.addRenderableWidget(new SimpleButtonWidget(
				new Rect2i(this.width - 73, this.height - 30, 65, 20),
				new TranslatableComponent("gui.done"),
				this::onClose
		));
	}

	private void initPageButtons() {
		int x = 6;
		int y = 6;
		int width = (this.width - 6) / 5 - 6;
		int height = 18;

		for (int i = 0; i < this.pages.size(); i++) {
			ModOptionPage page = this.pages.get(i);

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
		int x = 6;
		int y = 28;

		for (ImmutableList<IOption<?>> group : this.pages.get(this.currentPage).getGroups()) {
			for (IOption<?> option : group) {
				OptionWidget<?, ? extends IOption<?>> widget = option.createWidget(x, y, this.width / 2 - 8, 20);

				this.addRenderableWidget(widget);

				y += 20;
			}

			y += 4;
		}
	}

	@Override
	public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		super.renderBackground(matrixStack);
		super.render(matrixStack, mouseX, mouseY, partialTicks);
	}

	private void setPage(int index) {
		this.currentPage = index;
		this.init();
	}

	private void apply() {
		this.getAllOptions().forEach(IOption::save);
	}

	private void undo() {
		this.getAllOptions().forEach(IOption::load);
	}

	private Stream<IOption<?>> getAllOptions() {
		return this.pages.stream()
				.flatMap(page -> page.getGroups().stream()
						.flatMap(Collection::stream)
				);
	}

	@Override
	public void onClose() {
		if (this.minecraft == null) return;

		this.minecraft.setScreen(this.root);
	}

	public static ModOptionsSubScreen videoOptionsScreen(Screen root) {
		return new ModOptionsSubScreen(root, ModOptionPages.VIDEO);
	}
}
