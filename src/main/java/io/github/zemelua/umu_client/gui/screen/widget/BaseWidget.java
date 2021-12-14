package io.github.zemelua.umu_client.gui.screen.widget;

import com.mojang.blaze3d.vertex.PoseStack;
import io.github.zemelua.umu_client.util.Rect2i;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;

public class BaseWidget implements Widget, NarratableEntry, GuiEventListener {
	protected static final int BACKGROUND_DISABLED_COLOR = 0x60000000;
	protected static final int BACKGROUND_BASE_COLOR = 0x90000000;
	protected static final int BACKGROUND_HOVERED_COLOR = 0x90404040;
	protected static final int TEXT_COLOR = 0xFFFFFFFF;

	protected final Rect2i rect;
	protected final Component label;
	protected final Font font;
	protected final SoundManager speaker;

	protected boolean active;
	protected boolean hovered;
	protected boolean hasHovered;
	protected double fadingDepth;

	public BaseWidget(Rect2i rect, Component label) {
		this.rect = rect;
		this.label = label;

		Minecraft minecraft = Minecraft.getInstance();
		this.font = minecraft.font;
		this.speaker = minecraft.getSoundManager();

		this.active = true;
		this.hovered = false;
		this.hasHovered = false;
	}

	@Override
	public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		this.hovered = this.active && this.rect.contains(mouseX, mouseY);
		if (this.hovered && !this.hasHovered) this.hasHovered = true;

		if (this.hasHovered) {
			if (this.hovered) {
				this.fadingDepth += partialTicks / 2.45D;
			} else {
				this.fadingDepth -= partialTicks / 2.45D;
			}
		}

		this.fadingDepth = Mth.clamp(this.fadingDepth, 0.0D, 1.0D);

		this.drawBackGround(matrixStack, mouseX, mouseY, partialTicks);
		this.drawLabel(matrixStack, mouseX, mouseY, partialTicks);
	}

	public boolean isHovered() {
		return this.hovered;
	}

	protected void drawBackGround(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		int startX = this.rect.getX();
		int startY = this.rect.getY();
		int endX = this.rect.getLimitX();
		int endY = this.rect.getLimitY();
		int color = this.getBackgroundColor();

		this.drawRect(matrixStack, startX, startY, endX, endY, color);
	}

	protected void drawLabel(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		int centerX = this.rect.getMiddleX();
		int centerY = this.rect.getMiddleY();
		int color = this.getTextColor();

		this.drawTextCenter(matrixStack, this.label, centerX, centerY, color);
	}

	@Override
	public boolean isMouseOver(double mouseX, double mouseY) {
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
		return this.active ? (this.hovered ? BACKGROUND_HOVERED_COLOR : BACKGROUND_BASE_COLOR) : BACKGROUND_DISABLED_COLOR;
	}

	protected int getTextColor() {
		return this.active ? 0xFFFFFFFF : 0x90FFFFFF;
	}
}
