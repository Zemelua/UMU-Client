package io.github.zemelua.umu_client.gui.screen.widget;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.network.chat.Component;

public class BaseWidget implements Widget, NarratableEntry, GuiEventListener {
	protected final Rect2i rect;
	protected final Component label;
	protected final Font font;
	protected final SoundManager speaker;

	protected boolean enabled;
	protected boolean hovered;

	public BaseWidget(Rect2i rect, Component label) {
		this.rect = rect;
		this.label = label;

		Minecraft minecraft = Minecraft.getInstance();
		this.font = minecraft.font;
		this.speaker = minecraft.getSoundManager();
	}

	@Override
	public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		this.hovered = this.rect.contains(mouseX, mouseY);

		this.drawBackGround(matrixStack, mouseX, mouseY, partialTicks);
		this.drawLabel(matrixStack, mouseX, mouseY, partialTicks);
	}

	protected void drawBackGround(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		int backGroundColor = this.enabled ? (hovered ? 0xE0000000 : 0x90000000) : 0x60000000;
		int limitX = this.rect.getX() + this.rect.getWidth();
		int limitY = this.rect.getY() + this.rect.getHeight();

		GuiComponent.fill(matrixStack, this.rect.getX(), limitX, limitY, this.rect.getHeight(), backGroundColor);
	}

	protected void drawLabel(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		int labelColor = this.enabled ? 0xFFFFFFFF : 0x90FFFFFF;
		int centerX = this.rect.getX() + (this.rect.getWidth() / 2);
		int centerY = this.rect.getY() + (this.rect.getHeight() / 2);

		GuiComponent.drawCenteredString(matrixStack, this.font, this.label, centerX, centerY, labelColor);
	}

	@Override
	public NarrationPriority narrationPriority() {
		return this.hovered ? NarrationPriority.HOVERED : NarrationPriority.NONE;
	}

	@Override
	public void updateNarration(NarrationElementOutput p_169152_) {
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
}
