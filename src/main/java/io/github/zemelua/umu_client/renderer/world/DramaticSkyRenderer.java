package io.github.zemelua.umu_client.renderer.world;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import io.github.zemelua.umu_client.UMUClient;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec2;
import net.minecraftforge.event.TickEvent;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class DramaticSkyRenderer {
	private static final ResourceLocation FALLING_STAR_LOCATION = UMUClient.resource("textures/environment/falling_star.png");

	private final Minecraft minecraft;
	private final List<FallingStar> fallingStars;

	private Random random;
	@Nullable private MeteorShower shower;

	public DramaticSkyRenderer(Minecraft minecraft) {
		this.minecraft = minecraft;
		this.fallingStars = new ArrayList<>();
	}

	public void onClientTick(final TickEvent.ClientTickEvent event) {
		ClientLevel world = this.minecraft.level;
		if (world == null) return;
		long gameTime = world.getGameTime();
		long dayTime = world.getDayTime();
		this.random = new Random(gameTime);

		if (event.phase == TickEvent.Phase.END) {
			if (world.getStarBrightness(0.0F) <= 0.0F) {
				if (!this.fallingStars.isEmpty()) this.fallingStars.clear();
			} else {
				if (this.random.nextInt(3295) > 3293) {
					this.createStar();
				}
			}

			if (dayTime == 13000 && this.random.nextInt(72) > 70) {
				this.createShower();
			}

			if (this.shower != null) {
				this.shower.tick(dayTime);
			}
		}
	}

	public static void drawStars(BufferBuilder builder) {
		Random random = new Random(10842L);
		builder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);

		for (int i = 0; i < 1500; ++i) {
			float x = random.nextFloat() * 2.0F - 1.0F;
			float y = random.nextFloat() * 2.0F - 1.0F;
			float z = random.nextFloat() * 2.0F - 1.0F;
			float size = 0.15F + random.nextFloat() * 0.1F;
			float d = x * x + y * y + z * z;
			if (d < 1.0F && d > 0.01F) {
				d = (float) (1.0D / Math.sqrt(d));
				x *= d;
				y *= d;
				z *= d;
				double radian = random.nextDouble() * Math.PI * 2.0D;

				float[] color;
				switch (random.nextInt(9)) {
					case 0 -> color = new float[]{1.00F, 0.86F, 0.80F};
					case 1 -> color = new float[]{1.00F, 1.00F, 0.86F};
					case 2 -> color = new float[]{0.88F, 0.97F, 1.00F};
					default -> color = new float[]{1.00F, 1.00F, 1.00F};
				}

				Vector3f[] vertexes = DramaticSkyRenderer.calculateStarPos(x, y, z, size, radian);
				for (Vector3f vertex : vertexes) {
					builder.vertex(vertex.x(), vertex.y(), vertex.z()).color(color[0], color[1], color[2], 1.0F).endVertex();
				}
			}
		}
	}

	private void createShower() {
		long startTime = 13000 + this.random.nextLong(8000L) + 1L;
		long finishTime = Math.min(startTime + (long) (this.random.nextGaussian() * 4200L) + 1000L, 8400L);
		this.shower = this.new MeteorShower(startTime, finishTime);
	}

	private void createStar() {
		float x = this.random.nextFloat() * 2.0F - 1.0F;
		float y = this.random.nextFloat() * 2.0F - 1.0F;
		float z = this.random.nextFloat() * 2.0F - 1.0F;
		double d0 = x * x + y * y + z * z;

		if (d0 < 1.0D && d0 > 0.01D) {
			d0 = 1.0D / Math.sqrt(d0);
			x *= d0;
			y *= d0;
			z *= d0;

			this.fallingStars.add(new FallingStar(x, y, z));
		}

	}

	public void renderFallingStars(PoseStack matrixStack, BufferBuilder builder, float partialTicks) {
		RenderSystem.enableTexture();
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderTexture(0, FALLING_STAR_LOCATION);

		matrixStack.pushPose();
		matrixStack.mulPose(Vector3f.YP.rotationDegrees(-90.0F));

		builder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
		if (!this.fallingStars.isEmpty()) {
			for (Iterator<FallingStar> iterator = this.fallingStars.listIterator(); iterator.hasNext(); ) {
				FallingStar star = iterator.next();
				boolean result = star.draw(matrixStack.last().pose(), builder, partialTicks);

				if (result) iterator.remove();
			}
		}
		builder.end();
		BufferUploader.end(builder);

		matrixStack.popPose();

		RenderSystem.disableTexture();
	}

	private static Vector3f[] calculateStarPos(float x, float y, float z, float size, double radian) {
		float posX = x * 100.0F;
		float posY = y * 100.0F;
		float posZ = z * 100.0F;
		double d8 = Math.atan2(x, z);
		double d9 = Math.sin(d8);
		double d10 = Math.cos(d8);
		double d11 = Math.atan2(Math.sqrt(x * x + z * z), y);
		double d12 = Math.sin(d11);
		double d13 = Math.cos(d11);
		double d15 = Math.sin(radian);
		double d16 = Math.cos(radian);

		Vector3f[] vertexes = new Vector3f[4];
		for (int i = 0; i < 4; ++i) {
			double d18 = (double) ((i & 2) - 1) * size;
			double d19 = (double) ((i + 1 & 2) - 1) * size;
			double d21 = d18 * d16 - d19 * d15;
			double d22 = d19 * d16 + d18 * d15;
			double d24 = 0.0D * d12 - d21 * d13;
			float offsetX = (float) (d24 * d9 - d22 * d10);
			float offsetY = (float) (d21 * d12 + 0.0D * d13);
			float offsetZ = (float) (d22 * d9 + d24 * d10);

			vertexes[i] = new Vector3f(posX + offsetX, posY + offsetY, posZ + offsetZ);
		}

		return vertexes;
	}

	private static class FallingStar {
		private final float drawX;
		private final float drawY;
		private final float drawZ;
		private final float size;

		private float ticks;

		private FallingStar(float drawX, float drawY, float drawZ) {
			this.drawX = drawX;
			this.drawY = drawY;
			this.drawZ = drawZ;
			this.size = 4.3F;
		}

		private boolean draw(Matrix4f matrix, BufferBuilder builder, float partialTicks) {
			this.ticks += partialTicks;

			int phase = (int) (this.ticks * 0.5F) % 12;
			int phaseU = phase % 4;
			int phaseV = phase / 4 % 3;
			float startU = (float) (phaseU) / 4.0F;
			float startV = (float) (phaseV) / 3.0F;
			float endU = (float) (phaseU + 1) / 4.0F;
			float endV = (float) (phaseV + 1) / 3.0F;

			Vector3f[] vertexes = DramaticSkyRenderer.calculateStarPos(this.drawX, this.drawY, this.drawZ, this.size, Math.toRadians(90));
			for (int i = 0; i < 4; i++) {
				Vec2 uv = switch (i) {
					case 0 -> new Vec2(endU, endV);
					case 1 -> new Vec2(startU, endV);
					case 2 -> new Vec2(startU, startV);
					case 3 -> new Vec2(endU, startV);

					default -> throw new IllegalStateException("Unexpected value: " + i);
				};

				Vector3f vertex = vertexes[i];
				builder.vertex(matrix, vertex.x(), vertex.y(), vertex.z()).uv(uv.x, uv.y).endVertex();
			}

			return Math.floor((int) (this.ticks * 0.5F) / 12.0D) > 0;
		}
	}

	private class MeteorShower {
		private final long startTime;
		private final long finishTime;

		private MeteorShower(long startTime, long finishTime) {
			this.startTime = startTime;
			this.finishTime = finishTime;
		}

		private void tick(long dayTime) {
			if (dayTime >= startTime && dayTime <= finishTime) {
				for (int i = 0; i < 17; i++) {
					DramaticSkyRenderer.this.createStar();
				}
			}
		}
	}
}
