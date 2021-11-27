package io.github.zemelua.umu_client.gui.screen.widget;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import io.github.zemelua.umu_client.util.Rect2i;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;

public abstract class BaseWidget implements Widget, NarratableEntry, GuiEventListener {
	protected static final int BACKGROUND_DISABLED_COLOR = 0x60000000;
	protected static final int BACKGROUND_BASE_COLOR = 0x90000000;
	protected static final int BACKGROUND_HOVERED_COLOR = 0x90404040;
	protected static final int TEXT_COLOR = 0xFFFFFFFF;

	protected final Rect2i rect;
	protected final Component label;
	protected final Font font;
	protected final SoundManager speaker;

	protected boolean enabled;
	protected boolean hovered;
	protected boolean hasHovered;

	protected double touchedTick;
	protected double leftTick;

	public BaseWidget(Rect2i rect, Component label) {
		this.rect = rect;
		this.label = label;

		Minecraft minecraft = Minecraft.getInstance();
		this.font = minecraft.font;
		this.speaker = minecraft.getSoundManager();

		this.enabled = true;
		this.hovered = false;
		this.hasHovered = false;
	}

	@Override
	public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		this.hovered = this.rect.contains(mouseX, mouseY);

		if (this.hovered) {
			this.touchedTick += partialTicks;
			this.leftTick = 0.0D;

			if (!this.hasHovered) this.hasHovered = true;
		} else {
			this.leftTick += partialTicks;
			this.touchedTick = 0.0D;
		}

		this.drawBackGround(matrixStack, mouseX, mouseY, partialTicks);
		this.drawLabel(matrixStack, mouseX, mouseY, partialTicks);
	}

	protected void drawBackGround(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		int startX = this.rect.getX();
		int startY = this.rect.getY();
		int endX = this.rect.getLimitX();
		int endY = this.rect.getLimitY();
		int backGroundColor = this.getBackgroundColor();

		this.drawRect(matrixStack, startX, startY, endX, endY, backGroundColor);
	}

	protected void drawLabel(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		int labelColor = this.enabled ? 0xFFFFFFFF : 0x90FFFFFF;
		int centerX = this.rect.getX() + this.rect.getWidth() / 2;
		int centerY = this.rect.getY() + this.rect.getHeight() / 2;

		this.drawTextCenter(matrixStack, this.label, centerX, centerY, labelColor);
	}

	@Override
	public boolean isMouseOver(double mouseX, double mouseY) {
		if (this.rect.contains((int) mouseX, (int) mouseY) && !this.hasHovered) this.hasHovered = true;

		return this.rect.contains((int) mouseX, (int) mouseY);
	}

	@Override
	public NarrationPriority narrationPriority() {
		return this.hovered ? NarrationPriority.HOVERED : NarrationPriority.NONE;
	}

	@Override
	public void updateNarration(NarrationElementOutput p_169152_) {
	}

	protected void drawRect(PoseStack matrixStack, int startX, int startY, int endX, int endY, int color) {
		GuiComponent.fill(matrixStack, startX, startY, endX, endY, color);
	}

	protected void drawRectOutline(PoseStack matrixStack, int startX, int startY, int endX, int endY, int color) {
		this.drawRect(matrixStack, startX, startY, endX, startY + 1, color);
		this.drawRect(matrixStack, startX, endY - 1, endX, endY, color);
		this.drawRect(matrixStack, startX, startY, startX + 1, endY, color);
		this.drawRect(matrixStack, endX - 1, startY, endX, endY, color);
	}

	protected void drawText(PoseStack matrixStack, Component text, int drawX, int drawY, int color) {
		this.font.draw(matrixStack, text, drawX, drawY, color);
	}

	protected void drawText(PoseStack matrixStack, String text, int drawX, int drawY, int color) {
		this.font.draw(matrixStack, text, drawX, drawY, color);
	}

	protected void drawTextCenter(PoseStack matrixStack, Component text, int drawX, int drawY, int color) {
		this.drawText(matrixStack, text, drawX - this.font.width(text.getVisualOrderText()) / 2, drawY - 4, color);
	}

	protected void playClickSound() {
		this.speaker.play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
	}

	protected int getBackgroundColor() {
		return this.enabled ? (hovered ? BACKGROUND_HOVERED_COLOR : BACKGROUND_BASE_COLOR) : BACKGROUND_DISABLED_COLOR;
	}
}
