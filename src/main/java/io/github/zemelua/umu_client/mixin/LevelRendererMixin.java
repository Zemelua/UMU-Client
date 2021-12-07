package io.github.zemelua.umu_client.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import io.github.zemelua.umu_client.ClientHandler;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;

@Mixin(LevelRenderer.class)
public abstract class LevelRendererMixin {
	@Shadow @Nullable private ClientLevel level;

	@Shadow public abstract void levelEvent(Player p_109533_, int p_109534_, BlockPos p_109535_, int p_109536_);

	@Inject(at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;disableTexture()V", ordinal = 2),
			method = "renderSky(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/math/Matrix4f;FLjava/lang/Runnable;)V")
	private void renderSky(PoseStack matrixStack, Matrix4f projectionMatrix, float partialTicks, Runnable fogSetup, CallbackInfo callback) {
		if (this.level == null) return;

		matrixStack.popPose();
		matrixStack.pushPose();

		matrixStack.mulPose(Vector3f.YP.rotationDegrees(-90.0F));

		ClientHandler.FALLING_STAR_RENDERER.renderFallingStar(matrixStack.last().pose(), Tesselator.getInstance().getBuilder(), partialTicks);

		matrixStack.popPose();
		matrixStack.pushPose();
		matrixStack.mulPose(Vector3f.YP.rotationDegrees(-90.0F));
		matrixStack.mulPose(Vector3f.XP.rotationDegrees(this.level.getTimeOfDay(partialTicks) * 360.0F));
	}
}
