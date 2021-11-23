package io.github.zemelua.umu_client.gui.screen.widget;

import com.mojang.blaze3d.vertex.PoseStack;
import io.github.zemelua.umu_client.option.VanillaRangeOption;
import net.minecraft.client.renderer.Rect2i;

public class VanillaRangeOptionWidget extends OptionWidget<VanillaRangeOption> {
	public VanillaRangeOptionWidget(Rect2i rect, VanillaRangeOption option) {
		super(rect, option);
	}

	@Override
	protected void drawValue(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {

	}
}
