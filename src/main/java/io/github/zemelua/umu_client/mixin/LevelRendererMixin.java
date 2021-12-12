package io.github.zemelua.umu_client.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexBuffer;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import io.github.zemelua.umu_client.ClientHandler;
import io.github.zemelua.umu_client.option.ModOptions;
import io.github.zemelua.umu_client.renderer.world.DramaticSkyRenderer;
import io.github.zemelua.umu_client.sound.DetailedSoundSource;
import io.github.zemelua.umu_client.sound.SoundManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;

@Mixin(LevelRenderer.class)
public abstract class LevelRendererMixin {
	@Shadow @Final private Minecraft minecraft;

	private final VertexBuffer colorfulStarsBuffer = LevelRendererMixin.createColorfulStars();

	@Shadow @Nullable private ClientLevel level;
	@Shadow @Nullable private VertexBuffer starBuffer;
	@Shadow @Nullable private VertexBuffer darkBuffer;

	private static VertexBuffer createColorfulStars() {
		Tesselator tesselator = Tesselator.getInstance();
		BufferBuilder builder = tesselator.getBuilder();
		RenderSystem.setShader(GameRenderer::getPositionColorShader);

		VertexBuffer buffer = new VertexBuffer();
		DramaticSkyRenderer.drawStars(builder);
		builder.end();
		buffer.upload(builder);

		return buffer;
	}

	@Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/ClientLevel;getStarBrightness(F)F",
			ordinal = 0),
			method = "renderSky(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/math/Matrix4f;FLjava/lang/Runnable;)V",
			cancellable = true)
	@SuppressWarnings("SpellCheckingInspection")
	private void renderSky(PoseStack matrixStack, Matrix4f projectionMatrix, float partialTicks, Runnable fogSetup, CallbackInfo callback) {
		// 1897行目（星のレンダリング）以下を全て置き換えます。

		if (ModOptions.ENABLE_DRAMATIC_SKY.getValue()) {
			if (this.level == null) return;
			if (this.starBuffer == null) return;
			if (this.darkBuffer == null) return;
			if (GameRenderer.getPositionShader() == null) return;
			if (GameRenderer.getPositionColorShader() == null) return;
			if (this.minecraft.player == null) return;
			if (RenderSystem.getShader() == null) return;

			float starBrightness = this.level.getStarBrightness(partialTicks) * (1.0F - this.level.getRainLevel(partialTicks));
			if (starBrightness > 0.0F) {
				matrixStack.popPose();

				FogRenderer.setupNoFog();

				BufferBuilder builder = Tesselator.getInstance().getBuilder();
				ClientHandler.MOD_SKY_RENDERER.renderFallingStars(matrixStack, builder, partialTicks);

				fogSetup.run();

				matrixStack.pushPose();
				matrixStack.mulPose(Vector3f.YP.rotationDegrees(-90.0F));
				matrixStack.mulPose(Vector3f.XP.rotationDegrees(this.level.getTimeOfDay(partialTicks) * 360.0F));

				FogRenderer.setupNoFog();
				RenderSystem.setShaderColor(starBrightness, starBrightness, starBrightness, starBrightness);
				this.colorfulStarsBuffer.drawWithShader(matrixStack.last().pose(), projectionMatrix, GameRenderer.getPositionColorShader());
				fogSetup.run();
			}

			matrixStack.popPose();
			RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
			RenderSystem.disableBlend();
			RenderSystem.disableTexture();
			RenderSystem.setShaderColor(0.0F, 0.0F, 0.0F, 1.0F);

			double height = this.minecraft.player.getEyePosition(partialTicks).y() - this.level.getLevelData().getHorizonHeight(this.level);

			if (height < 0.0D) {
				matrixStack.pushPose();
				matrixStack.translate(0.0D, 12.0D, 0.0D);
				this.darkBuffer.drawWithShader(matrixStack.last().pose(), projectionMatrix, RenderSystem.getShader());
				matrixStack.popPose();
			}

			Vec3 skyColor = this.level.getSkyColor(this.minecraft.gameRenderer.getMainCamera().getPosition(), partialTicks);
			float red = (float) skyColor.x();
			float blue = (float) skyColor.y();
			float green = (float) skyColor.z();

			if (this.level.effects().hasGround()) {
				RenderSystem.setShaderColor(red * 0.2F + 0.04F, blue * 0.2F + 0.04F, green * 0.6F + 0.1F, 1.0F);
			} else {
				RenderSystem.setShaderColor(red, blue, green, 1.0F);
			}

			RenderSystem.enableTexture();
			RenderSystem.depthMask(true);

			callback.cancel();
		}
	}

	@Inject(at = @At(value = "HEAD"),
			method = "levelEvent(Lnet/minecraft/world/entity/player/Player;ILnet/minecraft/core/BlockPos;I)V",
			cancellable = true)
	@SuppressWarnings("SpellCheckingInspection")
	public void levelEvent(Player player, int eventId, BlockPos pos, int extraData, CallbackInfo callback) {
		if (eventId == 2001) {
			if (this.level != null) {
				BlockState blockState = Block.stateById(extraData);

				if (!blockState.isAir()) {
					SoundType soundType = blockState.getSoundType(this.level, pos, null);
					SoundEvent soundEvent = soundType.getBreakSound();
					float volume = (soundType.getVolume() + 1.0F) / 2.0F;
					float pitch = soundType.getPitch() * 0.8F;

					SoundManager.setDetailedSource(DetailedSoundSource.BLOCKS_BREAK);
					this.level.playLocalSound(pos, soundEvent, SoundSource.BLOCKS, volume, pitch, false);
				}

				this.level.addDestroyBlockEffect(pos, blockState);
			}

			callback.cancel();
		}
	}
}
