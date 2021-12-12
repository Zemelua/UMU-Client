package io.github.zemelua.umu_client.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import io.github.zemelua.umu_client.ClientHandler;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.client.renderer.LevelRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;

@Mixin(LevelRenderer.class)
public abstract class LevelRendererMixin {
	@Shadow @Nullable private ClientLevel level;

	@Inject(at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;setShaderColor(FFFF)V", ordinal = 3),
			method = "renderSky(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/math/Matrix4f;FLjava/lang/Runnable;)V")
	@SuppressWarnings("SpellCheckingInspection")
	private void renderSky(PoseStack matrixStack, Matrix4f projectionMatrix, float partialTicks, Runnable fogSetup, CallbackInfo callback) {
		if (this.level == null) return;

		matrixStack.popPose();

		FogRenderer.setupNoFog();

		ClientHandler.MOD_SKY_RENDERER.renderFallingStars(matrixStack, Tesselator.getInstance().getBuilder(), partialTicks);

		fogSetup.run();

		matrixStack.pushPose();
		matrixStack.mulPose(Vector3f.YP.rotationDegrees(-90.0F));
		matrixStack.mulPose(Vector3f.XP.rotationDegrees(this.level.getTimeOfDay(partialTicks) * 360.0F));
	}
}
